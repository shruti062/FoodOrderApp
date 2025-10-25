package com.example.foodorderapp

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class PaymentActivity : AppCompatActivity() {

    private lateinit var textAmount: TextView
    private lateinit var radioGroup: RadioGroup
    private lateinit var btnPayNow: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment)

        textAmount = findViewById(R.id.textAmount)
        radioGroup = findViewById(R.id.radioGroupPayment)
        btnPayNow = findViewById(R.id.btnPayNow)

        // Get data from previous screen (Checkout or Cart)
        val address = intent.getStringExtra("address") ?: ""
        val total = intent.getDoubleExtra("total", 0.0)

        textAmount.text = "Amount to Pay: ₹${"%.2f".format(total)}"

        btnPayNow.setOnClickListener {
            val selectedId = radioGroup.checkedRadioButtonId
            if (selectedId == -1) {
                Toast.makeText(this, "Please select a payment method", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val selectedPayment = findViewById<RadioButton>(selectedId).text.toString()

            when (selectedPayment) {
                "UPI (Google Pay / PhonePe / Paytm)" -> {
                    // Open UPI Payment Screen
                    val intent = Intent(this, UPIPaymentActivity::class.java)
                    intent.putExtra("address", address)
                    intent.putExtra("total", total)
                    startActivity(intent)
                }

                "Credit / Debit Card" -> {
                    // Open Card Payment Screen
                    val intent = Intent(this, CardPaymentActivity::class.java)
                    intent.putExtra("address", address)
                    intent.putExtra("total", total)
                    startActivity(intent)
                }

                "Cash on Delivery" -> {
                    // Directly place order without extra payment screen
                    simulatePayment("Cash on Delivery", address, total)
                }

                else -> {
                    Toast.makeText(this, "Select valid payment method", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    // ✅ Handles direct "Cash on Delivery" flow
    private fun simulatePayment(paymentMethod: String, address: String, total: Double) {
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
                Toast.makeText(this, "Order placed successfully!", Toast.LENGTH_LONG).show()

                // Clear cart
                CartManager.clearCart(this)

                // Redirect to Order Success screen
                val intent = Intent(this, OrderSuccessActivity::class.java)
                intent.putExtra("DELIVERY_ADDRESS", address)
                intent.putExtra("ORDER_ID", System.currentTimeMillis().toString())
                intent.putExtra("PAYMENT_METHOD", paymentMethod)
                startActivity(intent)
                finish()
            },
            onFailure = {
                Toast.makeText(this, "Order failed: ${it.message}", Toast.LENGTH_LONG).show()
            }
        )
    }
}
