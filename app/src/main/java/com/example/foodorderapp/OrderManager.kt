package com.example.foodorderapp

import com.google.firebase.Timestamp
import com.google.firebase.firestore.FirebaseFirestore

object OrderManager {

    private val firestore by lazy { FirebaseFirestore.getInstance() }

    // 🛒 Place a new order and return the document ID
    fun placeOrder(
        items: List<Map<String, Any>>,
        totalPrice: Double,
        address: String,
        onSuccess: (String) -> Unit,    // return orderId
        onFailure: (Exception) -> Unit
    ) {
        val order = hashMapOf(
            "items" to items,
            "totalPrice" to totalPrice,
            "address" to address,
            "timestamp" to Timestamp.now(),
            "status" to "Pending"
        )

        firestore.collection("orders")
            .add(order)
            .addOnSuccessListener { docRef ->
                onSuccess(docRef.id) // ✅ return Firestore-generated ID
            }
            .addOnFailureListener { e ->
                onFailure(e)
            }
    }

    // 📦 Get all orders
    fun getOrders(
        onSuccess: (List<Map<String, Any>>) -> Unit,
        onFailure: (Exception) -> Unit
    ) {
        firestore.collection("orders")
            .orderBy("timestamp", com.google.firebase.firestore.Query.Direction.DESCENDING)
            .get()
            .addOnSuccessListener { result ->
                val orders = result.documents.mapNotNull { doc ->
                    doc.data?.plus("id" to doc.id)
                }
                onSuccess(orders)
            }
            .addOnFailureListener { e -> onFailure(e) }
    }
}
