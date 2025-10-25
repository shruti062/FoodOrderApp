package com.example.foodorderapp

data class Order(
    val orderId: String = "",
    val userId: String = "",
    val items: List<Map<String, Any>> = emptyList(), // list of maps { "name": "...", "price": 100 }
    val totalPrice: Double = 0.0,
    val address: String = "",
    val status: String = "Pending",
    val timestamp: String = System.currentTimeMillis().toString()
)
