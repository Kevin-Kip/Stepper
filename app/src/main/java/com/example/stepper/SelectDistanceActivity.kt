package com.example.stepper

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.stepper.models.Distance
import com.example.stepper.models.Order
import com.example.stepper.utils.Commons
import kotlinx.android.synthetic.main.activity_select_distance.*

class SelectDistanceActivity : AppCompatActivity() {

    private var order: Order? = null

    private val distanceNames = listOf(
        "1-5 miles",
        "6-10 miles",
        "11-15 miles",
        "21-30 miles"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select_distance)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        order = intent.getSerializableExtra(Commons.ORDER) as Order

        distance.setItems(distanceNames)

        button_next.setOnClickListener {
            submitData()
        }
    }

    override fun onResume() {
        super.onResume()
        if (order != null) {
        }
    }

    private fun submitData() {
        if (order != null) {
            order!!.distance = Commons.distances[distance.selectedIndex]
            val i = Intent(this@SelectDistanceActivity, SelectAssemblyActivity::class.java)
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
