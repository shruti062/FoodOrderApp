package com.example.foodorderapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class CheckoutActivity : AppCompatActivity() {

    private lateinit var editAddress: EditText
    private lateinit var textTotal: TextView
    private lateinit var btnPlaceOrder: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_checkout)

        editAddress = findViewById(R.id.editAddress)
        textTotal = findViewById(R.id.textTotal)
        btnPlaceOrder = findViewById(R.id.btnPlaceOrder)

        val total = CartManager.getTotalPrice()
        textTotal.text = "Total: ₹${"%.2f".format(total)}"

        btnPlaceOrder.setOnClickListener {
            val address = editAddress.text.toString().trim()
            if (address.isEmpty()) {
                Toast.makeText(this, "Please enter delivery address", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val intent = Intent(this, PaymentActivity::class.java)
            intent.putExtra("address", address)
            intent.putExtra("total", total)
            startActivity(intent)
        }
    }
}
