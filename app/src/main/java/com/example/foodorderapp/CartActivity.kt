package com.example.foodorderapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class CartActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var totalPriceText: TextView
    private lateinit var btnCheckout: Button
    private lateinit var adapter: CartAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)

        // ensure cart is loaded
        CartManager.init(this)

        recyclerView = findViewById(R.id.recyclerViewCart)
        totalPriceText = findViewById(R.id.textTotalPrice)
        btnCheckout = findViewById(R.id.btnCheckout)

        recyclerView.layoutManager = LinearLayoutManager(this)

        val items = CartManager.getCartItems().toMutableList()
        adapter = CartAdapter(this, items) {
            // callback when cart changed (CartAdapter removes items)
            updateTotal()
            // persist current items
            // remove all and re-add so manager stays synced
            CartManager.clearCart(this)
            adapter.getItems().forEach { CartManager.addToCart(this, it) }
        }

        recyclerView.adapter = adapter
        updateTotal()

        btnCheckout.setOnClickListener {
            startActivity(Intent(this, CheckoutActivity::class.java))
        }
    }

    private fun updateTotal() {
        totalPriceText.text = "Total: ₹${"%.2f".format(CartManager.getTotalPrice())}"
    }

    override fun onResume() {
        super.onResume()
        // refresh adapter with saved data
        adapter.setItems(CartManager.getCartItems())
        updateTotal()
    }
}
