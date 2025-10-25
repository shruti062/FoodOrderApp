package com.example.foodorderapp

import android.os.Bundle
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore

class OrderTrackingActivity : AppCompatActivity() {

    private lateinit var txtStatus: TextView
    private lateinit var progressBar: ProgressBar
    private val db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_tracking)

        txtStatus = findViewById(R.id.txtOrderStatus)
        progressBar = findViewById(R.id.progressBarStatus)

        val orderId = intent.getStringExtra("ORDER_ID") ?: return

        listenToOrderStatus(orderId)
    }

    private fun listenToOrderStatus(orderId: String) {
        db.collection("orders").document(orderId)
            .addSnapshotListener { snapshot, _ ->
                if (snapshot != null && snapshot.exists()) {
                    val status = snapshot.getString("status") ?: "Pending"
                    txtStatus.text = "Order Status: $status"

                    when (status) {
                        "Pending" -> progressBar.progress = 25
                        "Preparing" -> progressBar.progress = 50
                        "Out for Delivery" -> progressBar.progress = 75
                        "Delivered" -> progressBar.progress = 100
                    }
                }
            }
    }
}
