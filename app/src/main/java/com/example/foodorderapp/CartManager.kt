package com.example.foodorderapp

import FoodItem

object CartManager {
    private val cartItems = mutableListOf<FoodItem>()   // keep items here

    fun addToCart(item: FoodItem) {
        cartItems.add(item)
    }

    fun removeFromCart(item: FoodItem) {
        cartItems.remove(item)
    }

    fun clearCart() {
        cartItems.clear()
    }

    fun getCartItems(): List<FoodItem> {   // ✅ single public accessor
        return cartItems
    }
}
