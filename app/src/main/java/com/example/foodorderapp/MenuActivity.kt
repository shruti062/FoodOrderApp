package com.example.foodorderapp

import FoodItem
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MenuActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerViewMenu)
        recyclerView.layoutManager = LinearLayoutManager(this)

        val foodList = listOf(
            FoodItem("Pizza", "Cheese Burst", 299, R.drawable.pizza),
            FoodItem("Burger", "Veg Loaded", 149, R.drawable.burger),
            FoodItem("Pasta", "White Sauce", 199, R.drawable.pasta),
            FoodItem("Coffee", "Cappuccino", 99, R.drawable.coffee)
        )
        val goToCartBtn: Button = findViewById(R.id.btnGoToCart)
        goToCartBtn.setOnClickListener {
            val intent = Intent(this, CartActivity::class.java)
            startActivity(intent)
        }


        val adapter = FoodAdapter(this, foodList)
        recyclerView.adapter = adapter

    }
}
