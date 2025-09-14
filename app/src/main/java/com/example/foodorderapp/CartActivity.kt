package com.example.foodorderapp

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class CartActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: CartAdapter
    private lateinit var totalPriceText: TextView
    private lateinit var placeOrderBtn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)

        recyclerView = findViewById(R.id.cartRecyclerView)
        totalPriceText = findViewById(R.id.totalPriceText)
        placeOrderBtn = findViewById(R.id.btnPlaceOrder)

        // ✅ Use getCartItems() instead of cartItems
        val cartItems = CartManager.getCartItems()

        adapter = CartAdapter(this, cartItems)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        // Calculate total
        var total = 0
        for (item in cartItems) {
            total += item.price
        }
        totalPriceText.text = "Total: ₹$total"

        // Place Order button
        placeOrderBtn.setOnClickListener {
            // Save to "Orders" (for now just clear the cart)
            OrdersManager.addOrder(cartItems.toList())  // optional manager for orders
            CartManager.clearCart()
            finish()
        }
    }
}
