package com.example.stepper

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.stepper.models.Deliveries
import com.example.stepper.models.Distance
import com.example.stepper.models.Order
import com.example.stepper.utils.Commons
import com.revosleap.simpleadapter.SimpleAdapter
import com.revosleap.simpleadapter.SimpleCallbacks
import kotlinx.android.synthetic.main.activity_select_furniture.*
import kotlinx.android.synthetic.main.item_selected_furniture.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast

class SelectFurnitureActivity : AppCompatActivity() {

    private var order: Order? = null
    private var furnitureList = mutableListOf<Deliveries>()
    private val furnitureNames = listOf(
        "Sofa or Loveseat or Recliner",
        "Boxed items (up to 4 “3x3 boxes”) ",
        "Area Rugs (up to 3 rugs)",
        "Hardwood / Laminate / Vinyl Flooring (up to 350lb)",
        "Hardwood / Laminate / Vinyl Flooring (up to 700lb)",
        "Ottoman or Single Person Chair ",
        "Sectional (up to 4 pieces)",
        "Dining Room Set (table and up to 6 chairs)",
        "Patio Furniture Set (table and up to 6 chairs) ",
        "Outdoor Grill or Smoker",
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

    private var simpleAdapter: SimpleAdapter? = null
    private val simpleCallbacks = object : SimpleCallbacks {
        override fun bindView(view: View, item: Any, position: Int) {
            item as Deliveries
            val itemName = view.findViewById<TextView>(R.id.item_name)
            val itemPrice = view.findViewById<TextView>(R.id.item_price)
            val itemCount = view.findViewById<TextView>(R.id.item_count)
            val itemMinus = view.findViewById<ImageButton>(R.id.item_minus)
            val itemPlus = view.findViewById<ImageButton>(R.id.item_plus)
            val itemRemove = view.findViewById<ImageButton>(R.id.item_remove)

            itemName.text = item.description
            itemPrice.text = "$${item.price!!.times(item.count!!)}"
            itemCount.text = (item.count).toString()

            itemPlus.setOnClickListener {
                if (item.limit != null) {
                    if (item.count < item.limit!!) {
                        item.count = item.count!! + 1
                    } else if (item.count!! == item.limit) {
                        toast("Cannot exceed ${item.count} for this item")
                    }
                }else{
                    item.count = item.count!! + 1
                }
                itemCount.text = (item.count).toString()
                itemPrice.text = "$${item.price!!.times(item.count!!)}"
            }

            itemMinus.setOnClickListener {
                if (item.count!! > 1) {
                    item.count = item.count!! - 1
                } else{
                    toast("Cannot add less than one item")
                }
                itemCount.text = (item.count).toString()
                itemPrice.text = "$${item.price!!.times(item.count!!)}"
            }

            itemRemove.setOnClickListener {
                val thisItem = furnitureList.find {
                    it.description == item.description
                }
                furnitureList.remove(thisItem)
                simpleAdapter?.removeItem(thisItem as Any)
            }
        }

        override fun onViewClicked(view: View, item: Any, position: Int) {

        }

        override fun onViewLongClicked(it: View?, item: Any, position: Int) {

        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select_furniture)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        order = intent.getSerializableExtra(Commons.ORDER) as Order

        furniture.setItems(furnitureNames)
        simpleAdapter = SimpleAdapter(R.layout.item_selected_furniture, simpleCallbacks)
        selected_furniture.apply {
            adapter = simpleAdapter
            layoutManager = LinearLayoutManager(applicationContext)
            hasFixedSize()
            itemAnimator = DefaultItemAnimator()
        }
        button_next.setOnClickListener {
            submitData()
        }

        selectFurniture()
    }

    private fun selectFurniture() {
        furniture.setOnItemSelectedListener { _, _, _, name ->
            val item: Deliveries = Commons.furnitures.find { f ->
                f.description == name
            }!!
            if (!furnitureList.contains(item)) {
                simpleAdapter?.addItem(item)
                furnitureList.add(item)
            } else {
                toast("Item already exists in your selection")
            }
        }
    }

    override fun onResume() {
        super.onResume()
        if (order != null) {
            if (order!!.furniture != null) {
                if (order!!.furniture!!.isNotEmpty()) {
                    furnitureList = order!!.furniture!!
                    simpleAdapter!!.changeItems(order!!.furniture as MutableList<Any>)
                }
            }
        }
    }

    private fun submitData() {
        if (order != null) {
            order!!.furniture = furnitureList
            val i = Intent(this@SelectFurnitureActivity, SelectGuysActivity::class.java)
            i.putExtra(Commons.ORDER, order)
            startActivity(i)
            overridePendingTransition(R.anim.right_to_left_enter, R.anim.left_to_right_enter)
        }
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
