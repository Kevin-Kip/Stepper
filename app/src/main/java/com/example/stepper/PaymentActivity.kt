package com.example.stepper

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.stripe.android.ApiResultCallback
import com.stripe.android.Stripe
import com.stripe.android.model.Card
import com.stripe.android.model.Token
import com.stripe.android.view.CardMultilineWidget
import kotlinx.android.synthetic.main.activity_payment.*
import org.jetbrains.anko.toast


class PaymentActivity : AppCompatActivity() {

    private val publishKey: String = "pk_test_xxxxxxxxxxxxxxxxxxx"
    private val idempotencyKey: String = ""//TODO get these

    private var cardInput: CardMultilineWidget? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment)

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        cardInput = findViewById(R.id.card_input)
        pay_now.setOnClickListener { makePayment() }
    }

    private fun makePayment() {
        val card: Card? = cardInput?.card
        if (card == null) {
            Toast.makeText(applicationContext, "Invalid card", Toast.LENGTH_SHORT).show()
        } else {
            if (!card.validateCard()) { // Do not continue token creation.
                Toast.makeText(applicationContext, "Invalid card", Toast.LENGTH_SHORT).show()
            } else {
                createToken(card)
            }
        }
    }

    private fun createToken(card: Card) {
        val stripe = Stripe(applicationContext, publishKey)
        stripe.createCardToken(card, idempotencyKey, object : ApiResultCallback<Token> {
            override fun onSuccess(result: Token) {
                toast("Token created successfully")
                chargeCard(result.id)
            }

            override fun onError(e: Exception) {
                Snackbar.make(payment_parent, "Unable to create token", Snackbar.LENGTH_INDEFINITE)
                    .setAction("OK") {}
                    .show()
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
