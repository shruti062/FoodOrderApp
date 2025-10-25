package com.example.foodorderapp

data class Address(
    val id: String = "",       // Firestore doc id (optional)
    val name: String = "",
    val phone: String = "",
    val houseNo: String = "",
    val city: String = "",
    val state: String = "",
    val pinCode: String = ""
)
