package com.example.foodorderapp

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class UPIPaymentActivity : AppCompatActivity() {

    private lateinit var editUpiId: EditText
    private lateinit var btnPayNow: Button
    private lateinit var textAmount: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_upipayment)

        editUpiId = findViewById(R.id.editUpiId)
        btnPayNow = findViewById(R.id.btnPayNow)
        textAmount = findViewById(R.id.textAmount)

        val total = intent.getDoubleExtra("total", 0.0)
        val address = intent.getStringExtra("address") ?: ""

        textAmount.text = "Pay ₹${"%.2f".format(total)} via UPI"

        btnPayNow.setOnClickListener {
            val upiId = editUpiId.text.toString().trim()
            if (upiId.isEmpty()) {
                Toast.makeText(this, "Enter valid UPI ID", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            Toast.makeText(this, "Payment processing...", Toast.LENGTH_SHORT).show()

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
                    intent.putExtra("PAYMENT_METHOD", "UPI ($upiId)")
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
