package com.example.stepper

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import com.example.stepper.models.Order
import com.example.stepper.utils.Commons
import kotlinx.android.synthetic.main.activity_select_guys.*
import org.jetbrains.anko.startActivity

class SelectGuysActivity : AppCompatActivity() {

    private var order: Order? = null
    private var twoGoodGuys = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select_guys)

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        order = intent.getParcelableExtra(Commons.ORDER) as Order

        button_next.setOnClickListener {
            submitData()
        }

        card_one_good_guy.setOnClickListener { oneGoodGuy() }
        card_two_good_guys.setOnClickListener { twoGoodGuys() }
    }

    override fun onResume() {
        super.onResume()
        if (order != null) {
            if (order!!.twoGoodGuys != null) {
                if (twoGoodGuys) {
                    twoGoodGuys()
                }
            } else {
                oneGoodGuy()
            }
        }
    }

    private fun oneGoodGuy() {
        card_one_good_guy.setOnClickListener {
            card_one_good_guy.setBackgroundColor(
                ContextCompat.getColor(
                    applicationContext,
                    R.color.colorPrimary
                )
            )

            twoGoodGuys = false

            one_good_guy_label.setTextColor(Color.WHITE)
            one_good_guy_text.setTextColor(Color.WHITE)
            one_good_guy_price.setTextColor(Color.WHITE)

            card_two_good_guys.setBackgroundColor(Color.WHITE)
            two_good_guys_label.setTextColor(Color.BLACK)
            two_good_guys_text.setTextColor(Color.BLACK)
            two_good_guys_price.setTextColor(Color.BLACK)
        }
    }

    private fun twoGoodGuys() {
        card_two_good_guys.setBackgroundColor(
            ContextCompat.getColor(
                applicationContext,
                R.color.colorPrimary
            )
        )

        twoGoodGuys = true

        two_good_guys_label.setTextColor(Color.WHITE)
        two_good_guys_text.setTextColor(Color.WHITE)
        two_good_guys_price.setTextColor(Color.WHITE)

        card_one_good_guy.setBackgroundColor(Color.WHITE)
        one_good_guy_label.setTextColor(Color.BLACK)
        one_good_guy_text.setTextColor(Color.BLACK)
        one_good_guy_price.setTextColor(Color.BLACK)
    }

    private fun submitData() {
        if (order != null) {
            order!!.twoGoodGuys = twoGoodGuys
            val i = Intent(this@SelectGuysActivity, PersonalDetailsActivity::class.java)
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
