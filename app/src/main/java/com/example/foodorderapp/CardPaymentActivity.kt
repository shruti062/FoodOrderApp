package com.example.foodorderapp

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class CardPaymentActivity : AppCompatActivity() {

    private lateinit var editCardNumber: EditText
    private lateinit var editExpiry: EditText
    private lateinit var editCVV: EditText
    private lateinit var btnPayNow: Button
    private lateinit var textAmount: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_card_payment)

        editCardNumber = findViewById(R.id.editCardNumber)
        editExpiry = findViewById(R.id.editExpiry)
        editCVV = findViewById(R.id.editCVV)
        btnPayNow = findViewById(R.id.btnPayNow)
        textAmount = findViewById(R.id.textAmount)

        val total = intent.getDoubleExtra("total", 0.0)
        val address = intent.getStringExtra("address") ?: ""

        textAmount.text = "Pay ₹${"%.2f".format(total)} by Card"

        btnPayNow.setOnClickListener {
            val card = editCardNumber.text.toString()
            val expiry = editExpiry.text.toString()
            val cvv = editCVV.text.toString()

            if (card.length < 16 || expiry.isEmpty() || cvv.length != 3) {
                Toast.makeText(this, "Enter valid card details", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            Toast.makeText(this, "Processing payment...", Toast.LENGTH_SHORT).show()

            val items = CartManager.getCartItems().map {
                mapOf(
                    "name" to it.name,
                    "price" to it.price,
                    "quantity" to 1
                )
            }

            OrderManager.placeOrder(
                items = items,
                totalPrice = total,
                address = address,
                onSuccess = {
                    val intent = Intent(this, OrderSuccessActivity::class.java)
                    intent.putExtra("DELIVERY_ADDRESS", address)
                    intent.putExtra("ORDER_ID", System.currentTimeMillis().toString())
                    intent.putExtra("PAYMENT_METHOD", "Card Payment (****${card.takeLast(4)})")
                    startActivity(intent)
                    finish()
                },
                onFailure = {
                    Toast.makeText(this, "Payment failed: ${it.message}", Toast.LENGTH_LONG).show()
                }
            )
        }
    }
}
