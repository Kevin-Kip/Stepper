package com.example.stepper

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.stepper.models.Order
import com.example.stepper.utils.Commons
import kotlinx.android.synthetic.main.activity_select_assembly.*

class SelectAssemblyActivity : AppCompatActivity() {

    private var order: Order? = null
    private var needsAssembly = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select_assembly)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        order = intent.getSerializableExtra(Commons.ORDER) as Order

        needs_assembly.setOnCheckedChangeListener { group, checkedId ->
            needsAssembly = when (checkedId) {
                R.id.needs_assembly_yes -> true
                else -> false
            }
        }

        button_next.setOnClickListener {
            submitData()
        }
    }

    override fun onResume() {
        super.onResume()
        if (order != null) {
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
            order!!.needAssembly = needsAssembly
            val i = Intent(this@SelectAssemblyActivity, PersonalDetailsActivity::class.java)
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
