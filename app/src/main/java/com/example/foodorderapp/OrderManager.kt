package com.example.foodorderapp

import FoodItem

object OrdersManager {
    private val orders = mutableListOf<List<FoodItem>>()   // stores lists of orders

    fun addOrder(order: List<FoodItem>) {
        orders.add(order)
    }

    fun getOrders(): List<List<FoodItem>> {
        return orders
    }
}
