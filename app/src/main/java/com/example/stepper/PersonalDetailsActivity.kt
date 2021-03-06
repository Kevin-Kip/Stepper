package com.example.stepper

import android.Manifest
import android.annotation.TargetApi
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.widget.addTextChangedListener
import com.example.stepper.fragments.OrderSummary
import com.example.stepper.models.Deliveries
import com.example.stepper.models.Distance
import com.example.stepper.models.Order
import com.example.stepper.models.Stairs
import com.example.stepper.utils.Commons
import com.example.stepper.utils.FilePath
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import com.revosleap.simpleadapter.SimpleAdapter
import com.revosleap.simpleadapter.SimpleCallbacks
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_personal_details.*
import kotlinx.android.synthetic.main.item_notification.view.*
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.jetbrains.anko.alert
import org.jetbrains.anko.getStackTraceString
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File

class PersonalDetailsActivity : AppCompatActivity() {

    private var hasOwnerShip = "No"
    private var order: Order? = null
    private val windows =
        listOf("7-8 a.m", "8-9 a.m", "1-2 pm", "3-4 pm")

    private val furnitures = listOf(
        "Sofa or Loveseat or Recliner",
        "Boxed items (up to 4 “3x3 boxes”) ",
        "Area Rugs (up to 3 rugs)",
        "Hardwood / Laminate / Vinyl Flooring (up to 350lb)",
        "Hardwood / Laminate / Vinyl Flooring (up to 700lb)",
        "Ottoman or Single Person Chair ",
        "Sectional (up to 4 pieces)",
        "Dining Room Set (table and up to 6 chairs)",
        "Patio Furniture Set (table and up to 6 chairs) ",
        "Outdoor Grill or Smoker ",
        "Mattress/Boxspring Set (Queen or King)",
        "Mattress/Boxspring Set (Twin or Full)",
        "Bedroom Set (Frame or Headboard or Footboard)",
        "Bedroom Set (Nightstand up to 2)",
        "Dresser / Entertainment Stand",
        "Appliances (Washer or Dryer)",
        "Appliances (Refrigerator)",
        "Appliances (Stove or Dishwasher)",
        "Miscellaneous Large Item 51lb or more",
        "Miscellaneous Small Item 50lb or less",
        "Roll of Carpet (up to 350lb)",
        "Roll of Carpet (up to 700lb)"
    )

    private val selectedFiles = mutableListOf<File>()
    private val selectedFileUris = mutableListOf<Uri>()
    private var filePaths: MutableList<String> = arrayListOf()

    private var simpleAdapter: SimpleAdapter? = null
    private val simpleCallbacks = object : SimpleCallbacks {
        override fun bindView(view: View, item: Any, position: Int) {
            item as File
            view.file_name.text = item.name
            val image = when {
                item.name.endsWith(".png", true) -> R.drawable.icon_png
                item.name.endsWith(".jpg", true) ||
                        item.name.endsWith(
                            ".jpeg",
                            true
                        ) -> R.drawable.icon_jpg
                item.name.endsWith(".doc", true) || item.name.endsWith(
                    ".docx",
                    true
                ) -> R.drawable.icon_doc
                item.name.endsWith(".xls", true) || item.name.endsWith(
                    ".xlsx",
                    true
                ) -> R.drawable.icon_xls
                item.name.endsWith(".pdf", true) -> R.drawable.icon_pdf
                item.name.endsWith(".txt", true) || item.name.endsWith(
                    ".csv",
                    true
                ) -> R.drawable.icon_txt
                else -> R.drawable.ic_launcher_background
            }
            Picasso.get().load(image).into(view.file_icon)
            view.remove_file.setOnClickListener {
                simpleAdapter?.removeItem(item)
            }
        }

        override fun onViewClicked(view: View, item: Any, position: Int) {}

        override fun onViewLongClicked(it: View?, item: Any, position: Int) {}
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_personal_details)

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        order = intent.getSerializableExtra(Commons.ORDER) as Order
        simpleAdapter = SimpleAdapter(R.layout.item_notification, simpleCallbacks)

        select_file.setOnClickListener { selectFile() }

        button_next.setOnClickListener {
            submitData()
        }
        pickup_window.setItems(windows)
        initListeners()
    }

    private fun initListeners() {
        phone_input.addTextChangedListener { input ->
            if (input.toString().isNotEmpty()) {
                phone_parent.isErrorEnabled = false
            }
        }
        email_input.addTextChangedListener { input ->
            if (input.toString().isNotEmpty()) {
                email_parent.isErrorEnabled = false
            }
        }
        first_name_input.addTextChangedListener { input ->
            if (input.toString().isNotEmpty()) {
                first_name_parent.isErrorEnabled = false
            }
        }
        last_name_input.addTextChangedListener { input ->
            if (input.toString().isNotEmpty()) {
                last_name_parent.isErrorEnabled = false
            }
        }
    }

    private fun selectFile() {
        if (Build.VERSION.SDK_INT < 19) {
            val intent = Intent()
            intent.type = "*/*"
            intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
            intent.action = Intent.ACTION_GET_CONTENT
            startActivityForResult(
                Intent.createChooser(intent, "Select File")
                , Commons.PICK_IMAGE_MULTIPLE
            )
        } else {
            val intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
            intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
            intent.addCategory(Intent.CATEGORY_OPENABLE)
            intent.type = "*/*"
            startActivityForResult(intent, Commons.PICK_IMAGE_MULTIPLE)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == Commons.READ_EXTERNAL_CODE && grantResults[0] != PackageManager.PERMISSION_GRANTED) {
            toast("Permission denied")
        } else {
            toast("Permission granted. Select files")
        }
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == Commons.PICK_IMAGE_MULTIPLE &&
            resultCode == Activity.RESULT_OK && null != data
        ) {
            if (ContextCompat.checkSelfPermission(
                    this@PersonalDetailsActivity,
                    Manifest.permission.READ_EXTERNAL_STORAGE
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                hasOwnerShip = "Yes"
                selectedFileUris.add(data.data!!)
                val fullPath: String = FilePath.getPath(this@PersonalDetailsActivity, data.data)
                val f = File(fullPath)
                selectedFiles.add(f)
                filePaths.add(f.absolutePath)
                simpleAdapter?.addItem(f)
            } else {
                ActivityCompat.requestPermissions(
                    this@PersonalDetailsActivity,
                    arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                    Commons.READ_EXTERNAL_CODE
                )
            }
        }
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    private fun uploadFiles() {
        val filesToSend = ArrayList<MultipartBody.Part>()
        selectedFileUris.forEach {
            furnitures
            filesToSend.add(prepareFilePart("attachments[]", it))
        }
//        apiService?.uploadFiles(filesToSend, uuid, "${token?.type} ${token?.token}")!!
//            .enqueue(object : Callback<PostResponses> {
//                override fun onResponse(
//                    call: Call<PostResponses>,
//                    response: Response<PostResponses>
//                ) {
//                    fileDialog?.dismiss()
//                    if (response.isSuccessful) {
//                        Log.e(
//                            "Response CODE/MESSAGE",
//                            "${response.code()} / ${response.message()}"
//                        )
//
//                        val res = response.body()
//                        Log.e("FILE", Gson().toJson(res))
//                        res?.let {
//                            Snackbar.make(
//                                claim_notification_parent,
//                                res.message!!,
//                                Snackbar.LENGTH_INDEFINITE
//                            )
//                                .setAction("DISMISS") {}
//                                .show()
//                            if (res.status!!) {
//                                simpleAdapter?.clearItems()
//                                claim_description.text.clear()
//                                select_cover.selectedIndex = 0
//                            }
//                        }
//                    } else {
//                        Log.e("NOT successfull", "not sucessfull")
//                    }
//                }
//
//                override fun onFailure(call: Call<PostResponses>, t: Throwable) {
//                    fileDialog?.dismiss()
//                    Log.e("FILES FAIL", t.getStackTraceString())
//                    Snackbar.make(
//                        claim_notification_parent,
//                        "Unable to upload files",
//                        Snackbar.LENGTH_INDEFINITE
//                    )
//                        .setAction("DISMISS") {}
//                        .show()
//                }
//            })
    }

    private fun prepareFilePart(partName: String, fileUri: Uri): MultipartBody.Part {
        val fullPath: String = FilePath.getPath(this@PersonalDetailsActivity, fileUri)
        val file = File(fullPath)
        // create RequestBody instance from file
        val requestFile = RequestBody.create(
            MediaType.parse(contentResolver.getType(fileUri)!!),
            file
        )
        // MultipartBody.Part is used to send also the actual file name
        return MultipartBody.Part.createFormData(partName, file.name, requestFile)
    }

    override fun onResume() {
        super.onResume()
        if (order != null) {
            if (order!!.firstName != null) {
                first_name_input.setText(order!!.firstName, TextView.BufferType.EDITABLE)
            }
            if (order!!.lastName != null) {
                last_name_input.setText(order!!.lastName, TextView.BufferType.EDITABLE)
            }
            if (order!!.email != null) {
                email_input.setText(order!!.email, TextView.BufferType.EDITABLE)
            }
            if (order!!.phone != null) {
                phone_input.setText(order!!.phone, TextView.BufferType.EDITABLE)
            }
            if (order!!.pickupWindow != null) {
                val index = windows.indexOf(order!!.pickupWindow)
                pickup_window.selectedIndex = index
            }
        }
    }

    private fun submitData() {
        if (order != null) {

            val phone: String? = phone_input.text.toString()
            val email: String? = email_input.text.toString()
            val firstName: String? = first_name_input.text.toString()
            val lastName: String? = last_name_input.text.toString()

            if (phone.isNullOrEmpty() || email.isNullOrEmpty() ||
                firstName.isNullOrEmpty() || lastName.isNullOrEmpty()
            ) {
                if (phone.isNullOrEmpty()) {
                    phone_parent.isErrorEnabled = true
                    phone_parent.error = "Please provide phone number"
                }
                if (email.isNullOrEmpty()) {
                    email_parent.isErrorEnabled = true
                    email_parent.error = "Please provide email address"
                }
                if (firstName.isNullOrEmpty()) {
                    first_name_parent.isErrorEnabled = true
                    first_name_parent.error = "Please provide first name"
                }
                if (lastName.isNullOrEmpty()) {
                    last_name_parent.isErrorEnabled = true
                    last_name_parent.error = "Please provide last name"
                }
                return
            }

            order!!.phone = phone
            order!!.email = email
            order!!.firstName = firstName
            order!!.lastName = lastName
            order!!.pickupWindow = windows[pickup_window.selectedIndex]
            order!!.hasProofOfOwnerShip = hasOwnerShip

            val price: Float = getPrice(order)
            order?.price = price

            val orderSummary = OrderSummary()
            val bundle = Bundle()
            bundle.putSerializable(Commons.ORDER, order)
            orderSummary.arguments = bundle
            orderSummary.show(supportFragmentManager, orderSummary.tag)
        }
    }

    private fun getPrice(order: Order?): Float {
        var currentPrice = 0F
        val basePrice = 45F
        //Get furniture price
        order!!.furniture!!.forEach { item ->
            currentPrice += (item.price!!).times(item.count)
        }
        //Get distance price
        currentPrice += (order.distance)!!.price!!
        //Pickup stairs
        currentPrice += (order.pickupStairs)!!.price!!
        //Destination stairs
        currentPrice += (order.destinationStairs)!!.price!!
        //Assembly required
        currentPrice += when (order.twoGoodGuys) {
            true -> 75
            else -> 0
        }
        //good guys price
        val goodGuysPrice: Float = currentPrice.times(0.1F)
        currentPrice += goodGuysPrice

        if (currentPrice < basePrice) {
            return basePrice
        }
        return currentPrice
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left)
        return true
    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left)
    }
}
