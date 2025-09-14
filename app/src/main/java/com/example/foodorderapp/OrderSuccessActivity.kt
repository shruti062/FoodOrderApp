package com.example.foodorderapp

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class OrderSuccessActivity : AppCompatActivity() {

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_success)

        val txtMessage: TextView = findViewById(R.id.txtOrderMessage)
        val btnHome: Button = findViewById(R.id.btnBackHome)

        // Show confirmation message
        txtMessage.text = "🎉 Your order has been placed successfully!"

        // Clear cart after order
        CartManager.clearCart()

        // Navigate back to HomeActivity
        btnHome.setOnClickListener {
            val intent = Intent(this, HomeActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
            finish()
        }
    }
}
