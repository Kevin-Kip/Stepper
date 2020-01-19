package com.example.stepper.fragments

import android.app.Dialog
import android.os.Bundle
import android.text.SpannableStringBuilder
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.text.bold
import androidx.core.text.underline
import com.example.stepper.R
import com.example.stepper.models.Order
import com.example.stepper.utils.Commons
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.fragment_summary.view.*

class OrderSummary : BottomSheetDialogFragment() {

    override fun getTheme(): Int = R.style.BottomSheetDialogTheme

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog =
        BottomSheetDialog(requireContext(), theme)

    override fun onCreateView(inflater: LayoutInflater, parent: ViewGroup?, s: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_summary, parent, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val args = arguments
        val order = args?.get(Commons.ORDER) as Order?

        if (order != null) {
            val assembly = if (order.needAssembly!!) {
                "Yes"
            } else {
                "No"
            }
            val summary = SpannableStringBuilder()
            summary.bold { append("Furniture: ") }
                .append("${order.furniture?.size} Items")
                .append("\n")
                .bold { append("From: ") }
                .append(order.pickupLocation)
                .append("\n")
                .bold { append("To: ") }
                .append(order.destinationLocation)
                .append("\n")
                .bold { append("Requires Assembly: ") }
                .append(assembly)
                .append("\n")
                .bold { append("Total: ") }
                .underline { bold { append("$"+order.price) } }
            view.summary.text = summary
        }
    }
}