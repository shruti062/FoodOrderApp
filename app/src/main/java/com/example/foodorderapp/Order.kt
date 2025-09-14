package com.example.foodorderapp

import FoodItem

data class Order(
    val id: Int,
    val items: List<FoodItem>,
    val totalPrice: Int
)
