package com.example.foodorderapp

import android.content.Context

object CartManager {
    private const val PREF_NAME = "CartPrefs"
    private const val CART_KEY = "cart_items"
    private var cartItems = mutableListOf<FoodItem>()

    /** Call once from a main activity to load saved cart */
    fun init(context: Context) {
        loadCart(context)
    }

    fun addToCart(context: Context, item: FoodItem) {
        cartItems.add(item)
        saveCart(context)
    }

    fun removeFromCart(context: Context, item: FoodItem) {
        cartItems.removeAll { it.id == item.id }
        saveCart(context)
    }

    fun getCartItems(): List<FoodItem> = cartItems.toList()

    fun clearCart(context: Context) {
        cartItems.clear()
        saveCart(context)
    }

    fun getTotalPrice(): Double = cartItems.sumOf { it.price }

    // Persistence (simple string format, no external lib required)
    private fun saveCart(context: Context) {
        val prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val serialized = cartItems.joinToString(";") {
            listOf(it.id, it.name, it.description, it.price.toString(), it.imageUrl).joinToString("|")
        }
        prefs.edit().putString(CART_KEY, serialized).apply()
    }

    private fun loadCart(context: Context) {
        val prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val serialized = prefs.getString(CART_KEY, null)
        cartItems.clear()
        serialized?.takeIf { it.isNotEmpty() }?.split(";")?.forEach { itemString ->
            val parts = itemString.split("|")
            if (parts.size >= 5) {
                val item = FoodItem(
                    id = parts[0],
                    name = parts[1],
                    description = parts[2],
                    price = parts[3].toDoubleOrNull() ?: 0.0,
                    imageUrl = parts[4]
                )
                cartItems.add(item)
            }
        }
    }
}
