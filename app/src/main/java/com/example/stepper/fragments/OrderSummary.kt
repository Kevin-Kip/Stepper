package com.example.stepper.fragments

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.text.SpannableStringBuilder
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.text.bold
import androidx.core.text.underline
import com.braintreepayments.api.BraintreePaymentActivity
import com.braintreepayments.api.PaymentRequest
import com.braintreepayments.api.models.PaymentMethodNonce
import com.example.stepper.R
import com.example.stepper.models.Order
import com.example.stepper.utils.Commons
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.RequestParams
import com.loopj.android.http.TextHttpResponseHandler
import cz.msebera.android.httpclient.Header
import kotlinx.android.synthetic.main.fragment_summary.view.*
import org.jetbrains.anko.alert
import org.jetbrains.anko.toast

class OrderSummary : BottomSheetDialogFragment() {

    private var order: Order? = null
    private var clientToken: String? = null
    private var mClient: AsyncHttpClient? = null

    private val REQUEST_CODE = 1000

    override fun getTheme(): Int = R.style.BottomSheetDialogTheme

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog =
        BottomSheetDialog(requireContext(), theme)

    override fun onCreateView(inflater: LayoutInflater, parent: ViewGroup?, s: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_summary, parent, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getClientToken()
        val args = arguments
        order = args?.get(Commons.ORDER) as Order?

        if (order != null) {
            val assembly = if (order?.needAssembly!!) {
                "Yes"
            } else {
                "No"
            }
            val summary = SpannableStringBuilder()
            summary.bold { append("Furniture: ") }
                .append("${order?.furniture?.size} Items")
                .append("\n")
                .bold { append("From: ") }
                .append(order?.pickupLocation)
                .append("\n")
                .bold { append("To: ") }
                .append(order?.destinationLocation)
                .append("\n")
                .bold { append("Requires Assembly: ") }
                .append(assembly)
                .append("\n")
                .bold { append("Total: ") }
                .underline { bold { append("$" + order?.price) } }
            view.summary.text = summary
        }
        view.pay_button.setOnClickListener {
            makePayment()
        }
    }

    private fun getClientToken() {
        mClient = AsyncHttpClient()
        mClient?.get(
            Commons.BASE_URL + "/" + Commons.CLIENT_TOKEN,
            object : TextHttpResponseHandler() {
                override fun onFailure(
                    statusCode: Int,
                    headers: Array<Header>,
                    responseString: String,
                    throwable: Throwable
                ) {
                    context?.toast("Unable to get client token from server")
                    dismiss()
                }

                override fun onSuccess(
                    statusCode: Int,
                    headers: Array<Header>,
                    responseString: String
                ) {
                    clientToken = responseString
                    // Enable payment button:
                    view?.pay_button?.isEnabled = true
                }
            })
    }

    private fun makePayment() {
        val paymentRequest: PaymentRequest = PaymentRequest()
            .clientToken(clientToken)
            .amount((order?.price).toString())
            .primaryDescription("Furniture delivery")
            .secondaryDescription("Furniture delivery")
            .submitButtonText("Pay")

        startActivityForResult(paymentRequest.getIntent(context), REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when (requestCode) {
            REQUEST_CODE -> if (resultCode == BraintreePaymentActivity.RESULT_OK) {
                val paymentMethodNonce: PaymentMethodNonce =
                    data?.getParcelableExtra(BraintreePaymentActivity.EXTRA_PAYMENT_METHOD_NONCE)!!
                val requestParams = RequestParams()
                requestParams.put("payment_method_nonce", paymentMethodNonce.nonce)
                requestParams.put("amount", order?.price)
                mClient!!.post(
                    Commons.BASE_URL + "/" + Commons.PAYMENT,
                    requestParams,
                    object : TextHttpResponseHandler() {
                        override fun onFailure(
                            statusCode: Int,
                            headers: Array<Header>,
                            responseString: String,
                            throwable: Throwable
                        ) { //Toast.makeText(MainActivity.this, "Failure: " + responseString, Toast.LENGTH_LONG).show();
                            context?.alert {
                                title = "ERROR"
                                message = "Failure $responseString"
                                positiveButton("CANCEL") { di -> di.dismiss();dismiss() }
                            }?.show()
                        }

                        override fun onSuccess(
                            statusCode: Int,
                            headers: Array<Header>,
                            responseString: String
                        ) { //Toast.makeText(MainActivity.this, "Success: " + responseString, Toast.LENGTH_LONG).show();
                            context?.alert {
                                title = "SUCCESS"
                                message = "Success. Process completed successfully."
                                positiveButton("OK") { di -> di.dismiss();dismiss() }
                            }?.show()
                        }
                    })
            }
            else -> super.onActivityResult(requestCode, resultCode, data)
        }
    }
}