package com.example.stepper

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.example.stepper.models.Order
import com.example.stepper.utils.Commons
import kotlinx.android.synthetic.main.activity_select_furniture.*
import org.jetbrains.anko.startActivity

class SelectFurnitureActivity : AppCompatActivity() {

    private var order: Order? = null
    private var needsAssembly = false
    private val furnitures = listOf<String>()
    private val distances = listOf<String>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select_furniture)

        order = intent.getParcelableExtra(Commons.ORDER) as Order

        needs_assembly.setOnCheckedChangeListener { group, checkedId ->
            needsAssembly = when (checkedId) {
                R.id.needs_assembly_yes -> true
                else -> false
            }
        }

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        button_next.setOnClickListener {
            submitData()
        }
    }

    override fun onResume() {
        super.onResume()
        if (order != null) {
            if (order!!.furniture != null) {
                val index = furnitures.indexOf(order!!.furniture)
                furniture.selectedIndex = index
            }
            if (order!!.distance != null) {
                val index = distances.indexOf(order!!.distance)
                distance.selectedIndex = index
            }
            if (order!!.furnitureNumber != null) {
                furniture_number_input.setText(
                    order!!.furnitureNumber,
                    TextView.BufferType.EDITABLE
                )
            }

            if (needsAssembly) {
                needs_assembly_no.isChecked = false
                needs_assembly_yes.isChecked = true
            } else {
                needs_assembly_yes.isChecked = false
                needs_assembly_no.isChecked = true
            }
        }
    }

    private fun submitData() {
        if (order != null) {
            order!!.distance = distances[distance.selectedIndex]
            order!!.furniture = furnitures[furniture.selectedIndex]
            order!!.furnitureNumber = furniture_number_input.text.toString()
            order!!.needAssembly = needsAssembly
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
