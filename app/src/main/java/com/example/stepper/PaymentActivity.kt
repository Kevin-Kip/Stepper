package com.example.stepper

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.braintreepayments.api.dropin.DropInActivity
import com.braintreepayments.api.dropin.DropInRequest
import com.braintreepayments.api.dropin.DropInResult
import com.example.stepper.models.Order
import com.example.stepper.utils.Commons
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.RequestParams
import com.loopj.android.http.TextHttpResponseHandler
import kotlinx.android.synthetic.main.activity_payment.*
import org.jetbrains.anko.toast


class PaymentActivity : AppCompatActivity() {

    private val TAG = MainActivity::class.java.simpleName
    private val PATH_TO_SERVER = "PATH_TO_SERVER"
    private var clientToken: String? = null
    private val BRAINTREE_REQUEST_CODE = 4949
    private var order: Order? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        order = intent.getSerializableExtra(Commons.ORDER) as Order
        if (order != null) {
            pay_now.text = "PAY $${order?.price}"

            createToken()
            pay_now.setOnClickListener {
                onBraintreeSubmit()
            }
        } else{
            toast("Order cannot be null. Fill in all selections")
            finish()
        }
    }

    private fun createToken() {
        val androidClient = AsyncHttpClient()
        androidClient.get(PATH_TO_SERVER, object : TextHttpResponseHandler() {

            override fun onSuccess(
                statusCode: Int,
                headers: Array<out cz.msebera.android.httpclient.Header>?,
                responseString: String?
            ) {
                clientToken = responseString
            }

            override fun onFailure(
                statusCode: Int,
                headers: Array<out cz.msebera.android.httpclient.Header>?,
                responseString: String?,
                throwable: Throwable?
            ) {

            }
        })
    }

    private fun onBraintreeSubmit() {
        val dropInRequest = DropInRequest().clientToken(clientToken)
        startActivityForResult(dropInRequest.getIntent(this), BRAINTREE_REQUEST_CODE)
    }

    private fun onActivityResult(requestCode: Int?, resultCode: Int?, data: Intent?) {
        super.onActivityResult(requestCode!!, resultCode!!, data)
        if (requestCode == BRAINTREE_REQUEST_CODE) {
            if (Activity.RESULT_OK == resultCode) {
                val result: DropInResult =
                    data!!.getParcelableExtra(DropInResult.EXTRA_DROP_IN_RESULT)!!
                val paymentNonce = result.paymentMethodNonce!!.nonce
                //send to your server
                Log.d(TAG, "Testing the app here")
                sendPaymentNonceToServer(paymentNonce)
            } else if (resultCode == Activity.RESULT_CANCELED) {
                Log.d(TAG, "User cancelled payment")
            } else {
                val error = data!!.getSerializableExtra(DropInActivity.EXTRA_ERROR) as Exception
                Log.d(TAG, " error exception")
            }
        }
    }

    private fun sendPaymentNonceToServer(paymentNonce: String) {
        val params = RequestParams("NONCE", paymentNonce)
        val androidClient = AsyncHttpClient()
        androidClient.post(PATH_TO_SERVER, params, object : TextHttpResponseHandler() {
            override fun onSuccess(
                statusCode: Int,
                headers: Array<out cz.msebera.android.httpclient.Header>?,
                responseString: String?
            ) {
                toast("Payment sent successfully")
            }

            override fun onFailure(
                statusCode: Int,
                headers: Array<out cz.msebera.android.httpclient.Header>?,
                responseString: String?,
                throwable: Throwable?
            ) {

            }
        })
    }

    private fun chargeCard(token: String?) {
        // Pass that token, amount to your server using API to process payment.
//TODO send token to server
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
