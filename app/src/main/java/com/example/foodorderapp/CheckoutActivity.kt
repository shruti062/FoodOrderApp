package com.example.foodorderapp

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar

class CheckoutActivity : AppCompatActivity() {

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_checkout)

        val txtTotal: TextView = findViewById(R.id.txtTotalPriceCheckout)
        val btnConfirm: Button = findViewById(R.id.btnConfirmOrder)

        // Get total from CartActivity
        val total = intent.getIntExtra("TOTAL_PRICE", 0)
        txtTotal.text = "Total: ₹$total"

        btnConfirm.setOnClickListener {
            val cartItems = CartManager.getCartItems()

            if (cartItems.isNotEmpty()) {
                // Save items to OrderManager
                OrdersManager.addOrder(cartItems.toList())


                // Clear cart after order placed
                CartManager.clearCart()

                Toast.makeText(this, "Order placed successfully!", Toast.LENGTH_SHORT).show()

                // Navigate to My Orders
                val intent = Intent(this, MyOrdersActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                Toast.makeText(this, "Cart is empty!", Toast.LENGTH_SHORT).show()
            }

        }
        val btnBack: Button = findViewById(R.id.btnBackToHome)
        btnBack.setOnClickListener {
            val intent = Intent(this, HomeActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
            finish()
        }
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        toolbar.setNavigationOnClickListener {
            val intent = Intent(this, HomeActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
            finish()
        }

    }
}

