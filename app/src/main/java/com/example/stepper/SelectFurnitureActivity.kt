package com.example.stepper

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.example.stepper.models.Deliveries
import com.example.stepper.models.Distance
import com.example.stepper.models.Order
import com.example.stepper.utils.Commons
import kotlinx.android.synthetic.main.activity_select_furniture.*
import org.jetbrains.anko.startActivity

class SelectFurnitureActivity : AppCompatActivity() {

    private var order: Order? = null
    private var needsAssembly = false
    private val furnitures = listOf(
        Deliveries(1, "Sofa or Loveseat or Recliner"),
        Deliveries(2, "Boxed items (up to 4 “3x3 boxes”) "),
        Deliveries(3, "Area Rugs (up to 3 rugs)"),
        Deliveries(4, "Hardwood / Laminate / Vinyl Flooring (up to 350lb)"),
        Deliveries(5, "Hardwood / Laminate / Vinyl Flooring (up to 700lb)"),
        Deliveries(6, "Ottoman or Single Person Chair "),
        Deliveries(7, "Sectional (up to 4 pieces)"),
        Deliveries(8, "Dining Room Set (table and up to 6 chairs)"),
        Deliveries(9, "Patio Furniture Set (table and up to 6 chairs) "),
        Deliveries(10, "Outdoor Grill or Smoker "),
        Deliveries(11, "Mattress/Boxspring Set (Queen or King)"),
        Deliveries(12, "Mattress/Boxspring Set (Twin or Full)"),
        Deliveries(13, "Bedroom Set (Frame or Headboard or Footboard)"),
        Deliveries(14, "Bedroom Set (Nightstand up to 2)"),
        Deliveries(15, "Dresser / Entertainment Stand"),
        Deliveries(16, "Appliances (Washer or Dryer)"),
        Deliveries(17, "Appliances (Refrigerator)"),
        Deliveries(18, "Appliances (Stove or Dishwasher)"),
        Deliveries(19, "Miscellaneous Large Item 51lb or more"),
        Deliveries(20, "Miscellaneous Small Item 50lb or less"),
        Deliveries(21, "Roll of Carpet (up to 350lb)"),
        Deliveries(22, "Roll of Carpet (up to 700lb)")
    )

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

    private val distances = listOf(
        Distance(1, "1-5"),
        Distance(2, "6-10"),
        Distance(3, "11-15"),
        Distance(4, "21-30")
    )

    private val distanceNames = listOf(
        "1-5",
        "6-10",
        "11-15",
        "21-30"
    )

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

        furniture.setItems(furnitureNames)
        distance.setItems(distanceNames)

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        button_next.setOnClickListener {
            submitData()
        }
    }

    override fun onResume() {
        super.onResume()
        if (order != null) {
//            if (order!!.furniture != null) {
//                val index = furnitures.indexOf(Deliveries(order!!.furniture))
//                furniture.selectedIndex = index
//            }
//            if (order!!.distance != null) {
//                val index = distances.indexOf(Distance(order!!.distance))
//                distance.selectedIndex = index
//            }
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
            order!!.distance = distances[distance.selectedIndex].id
            order!!.furniture = furnitures[furniture.selectedIndex].id
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
