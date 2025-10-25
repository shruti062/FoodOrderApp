package com.example.foodorderapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class OrderSuccessActivity : AppCompatActivity() {

    private lateinit var txtOrderMessage: TextView
    private lateinit var btnTrackOrder: Button
    private lateinit var btnBackHome: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_success)

        txtOrderMessage = findViewById(R.id.txtOrderMessage)
        btnTrackOrder = findViewById(R.id.btnTrackOrder)
        btnBackHome = findViewById(R.id.btnBackHome)

        val address = intent.getStringExtra("DELIVERY_ADDRESS") ?: "No address"
        val orderId = intent.getStringExtra("ORDER_ID") ?: "N/A"

        txtOrderMessage.text = "Order placed!\n\nID: $orderId\nDeliver To: $address"

        // ✅ Track order
        btnTrackOrder.setOnClickListener {
            val intent = Intent(this, OrderTrackingActivity::class.java)
            intent.putExtra("ORDER_ID", orderId)
            startActivity(intent)
        }

        // ✅ Back to Home (redirect to HomeActivity)
        btnBackHome.setOnClickListener {
            val intent = Intent(this, HomeActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
            finish()
        }
    }
}
