package com.example.foodorderapp

import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MyOrdersActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var emptyTextView: TextView
    private lateinit var adapter: MyOrdersAdapter
    private val ordersList = mutableListOf<Order>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_orders)

        recyclerView = findViewById(R.id.recyclerViewOrders)
        emptyTextView = findViewById(R.id.textEmptyOrders)

        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = MyOrdersAdapter(ordersList)
        recyclerView.adapter = adapter

        // Load orders from Firestore
        loadOrdersFromFirestore()
    }

    private fun loadOrdersFromFirestore() {
        OrderManager.getOrders(
            onSuccess = { orders ->
                ordersList.clear()

                for (orderData in orders) {
                    val itemsList = (orderData["items"] as? List<*>)?.mapNotNull {
                        it as? Map<String, Any>
                    } ?: emptyList()

                    val order = Order(
                        orderId = orderData["id"]?.toString() ?: "",
                        items = itemsList,
                        totalPrice = (orderData["totalPrice"] as? Double) ?: 0.0,
                        address = orderData["address"]?.toString() ?: "",
                        timestamp = orderData["timestamp"]?.toString() ?: "",
                        status = "Confirmed"
                    )

                    ordersList.add(order)
                }

                if (ordersList.isEmpty()) {
                    emptyTextView.visibility = View.VISIBLE
                    recyclerView.visibility = View.GONE
                } else {
                    emptyTextView.visibility = View.GONE
                    recyclerView.visibility = View.VISIBLE
                    adapter.notifyDataSetChanged()
                }
            },
            onFailure = { e ->
                Toast.makeText(
                    this,
                    "Failed to load orders: ${e.message}",
                    Toast.LENGTH_LONG
                ).show()
            }
        )
    }
}
