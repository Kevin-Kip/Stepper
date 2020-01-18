package com.example.stepper

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.braintreepayments.api.BraintreePaymentActivity
import com.braintreepayments.api.PaymentRequest
import com.braintreepayments.api.models.PaymentMethodNonce
import com.example.stepper.models.Order
import com.example.stepper.utils.Commons
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.RequestParams
import com.loopj.android.http.TextHttpResponseHandler
import cz.msebera.android.httpclient.Header
import kotlinx.android.synthetic.main.activity_payment.*
import org.jetbrains.anko.alert
import org.jetbrains.anko.toast


open class PaymentActivity : AppCompatActivity() {

    private val TAG = MainActivity::class.java.simpleName
    private val PATH_TO_SERVER = "PATH_TO_SERVER"
    private var clientToken: String? = null
    private val BRAINTREE_REQUEST_CODE = 4949
    private var order: Order? = null

    private var mClient: AsyncHttpClient? = null

    private val REQUEST_CODE = 1000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        order = intent.getSerializableExtra(Commons.ORDER) as Order
        pay_now.isEnabled = false
        if (order != null) {
            pay_now.text = "PAY USD ${order?.price}"

            pay_now.setOnClickListener {
                makePayment()
            }
        } else {
            toast("Order cannot be null. Fill in all selections")
            finish()
        }

        getClientToken()
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
                    toast("Unable to get client token from server")
                    finish()
                }

                override fun onSuccess(
                    statusCode: Int,
                    headers: Array<Header>,
                    responseString: String
                ) {
                    clientToken = responseString
                    // Enable payment button:
                    pay_now.isEnabled = true
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

        startActivityForResult(paymentRequest.getIntent(this), REQUEST_CODE)
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
                            alert {
                                title = "ERROR"
                                message = "Failure $responseString"
                                positiveButton("CANCEL") { di -> di.dismiss() }
                            }.show()
                        }

                        override fun onSuccess(
                            statusCode: Int,
                            headers: Array<Header>,
                            responseString: String
                        ) { //Toast.makeText(MainActivity.this, "Success: " + responseString, Toast.LENGTH_LONG).show();
                            alert {
                                title = "SUCCESS"
                                message = "Success. Process completed successfully."
                                positiveButton("OK") { di -> di.dismiss() }
                            }.show()
                            intent.removeExtra(Commons.ORDER)
                        }
                    })
            }
            else -> super.onActivityResult(requestCode, resultCode, data)
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
