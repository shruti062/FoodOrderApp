package com.example.foodorderapp

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore

class OrderTrackingActivity : AppCompatActivity() {

    private lateinit var txtStatus: TextView
    private lateinit var progressBar: ProgressBar
    private lateinit var loadingSpinner: ProgressBar
    private lateinit var imgStatus: ImageView
    private val db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_tracking)

        txtStatus = findViewById(R.id.txtOrderStatus)
        progressBar = findViewById(R.id.progressBarStatus)
        loadingSpinner = findViewById(R.id.loadingSpinner)
        imgStatus = findViewById(R.id.imgStatus)

        val orderId = intent.getStringExtra("ORDER_ID") ?: "ORDER123"

        // 🧩 Simulate Firebase real-time updates (remove this part once you have real orders)
        simulateOrderStatus(orderId)

        // 🔥 Real Firebase listener (kept for when you connect Firebase)
        listenToOrderStatus(orderId)
    }

    private fun listenToOrderStatus(orderId: String) {
        db.collection("orders").document(orderId)
            .addSnapshotListener { snapshot, _ ->
                if (snapshot != null && snapshot.exists()) {
                    val status = snapshot.getString("status") ?: "Pending"
                    updateUI(status)
                }
            }
    }

    private fun updateUI(status: String) {
        txtStatus.text = "Order Status: $status"

        when (status) {
            "Pending" -> {
                imgStatus.setImageResource(R.drawable.ic_waiting)
                progressBar.progress = 25
            }
            "Preparing" -> {
                imgStatus.setImageResource(R.drawable.ic_cooking)
                progressBar.progress = 50
            }
            "Out for Delivery" -> {
                imgStatus.setImageResource(R.drawable.ic_delivery)
                progressBar.progress = 75
            }
            "Delivered" -> {
                // 🎬 Show confirmation animation before marking success
                loadingSpinner.visibility = View.VISIBLE
                txtStatus.text = "Order is being confirmed..."

                Handler(Looper.getMainLooper()).postDelayed({
                    loadingSpinner.visibility = View.GONE
                    imgStatus.setImageResource(R.drawable.ic_delivered)
                    txtStatus.text = "Order Status: Delivered ✅"
                    progressBar.progress = 100
                }, 2500)
            }
            else -> {
                imgStatus.setImageResource(R.drawable.ic_confirmed)
                progressBar.progress = 25
            }
        }
    }

    // 🧩 Optional: Fake order status flow (for demo)
    private fun simulateOrderStatus(orderId: String) {
        val statusList = listOf("Pending", "Preparing", "Out for Delivery", "Delivered")
        var index = 0

        val handler = Handler(Looper.getMainLooper())
        val runnable = object : Runnable {
            override fun run() {
                if (index < statusList.size) {
                    val currentStatus = statusList[index]
                    db.collection("orders").document(orderId)
                        .set(mapOf("status" to currentStatus))
                    index++
                    handler.postDelayed(this, 4000) // every 4 seconds
                }
            }
        }
        handler.post(runnable)
    }
}
