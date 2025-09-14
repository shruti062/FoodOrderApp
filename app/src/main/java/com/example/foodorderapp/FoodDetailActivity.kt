package com.example.foodorderapp

import FoodItem
import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class FoodDetailActivity : AppCompatActivity() {

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_food_detail)

        // Get data from Intent
        val foodName = intent.getStringExtra("foodName") ?: "Food"
        val foodDesc = intent.getStringExtra("foodDesc") ?: "No description"
        val foodPrice = intent.getIntExtra("foodPrice", 0)
        val foodImage = intent.getIntExtra("foodImage", R.drawable.ic_launcher_background)

        // Bind views
        val imgFood: ImageView = findViewById(R.id.imgFoodDetail)
        val txtName: TextView = findViewById(R.id.txtFoodNameDetail)
        val txtDesc: TextView = findViewById(R.id.txtFoodDescDetail)
        val txtPrice: TextView = findViewById(R.id.txtFoodPriceDetail)
        val btnAddToCart: Button = findViewById(R.id.btnAddToCartDetail)

        // Set values
        imgFood.setImageResource(foodImage)
        txtName.text = foodName
        txtDesc.text = foodDesc
        txtPrice.text = "₹$foodPrice"

        // ✅ Fix: use addToCart instead of addItem
        btnAddToCart.setOnClickListener {
            val item = FoodItem(foodName, foodDesc, foodPrice, foodImage)
            CartManager.addToCart(item)   // 👈 correct function
            Toast.makeText(this, "$foodName added to cart", Toast.LENGTH_SHORT).show()
        }
    }
}
