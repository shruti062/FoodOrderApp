package com.example.foodorderapp

import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide

class FoodDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_food_detail)

        val img: ImageView = findViewById(R.id.imgFoodDetail)
        val nameTv: TextView = findViewById(R.id.txtFoodNameDetail)
        val descTv: TextView = findViewById(R.id.txtFoodDescDetail)
        val priceTv: TextView = findViewById(R.id.txtFoodPriceDetail)
        val btnAdd: Button = findViewById(R.id.btnAddToCart)

        val id = intent.getStringExtra("id") ?: ""
        val name = intent.getStringExtra("name") ?: ""
        val desc = intent.getStringExtra("desc") ?: ""
        val price = intent.getDoubleExtra("price", 0.0)
        val image = intent.getStringExtra("image") ?: ""

        nameTv.text = name
        descTv.text = desc
        priceTv.text = "₹${"%.2f".format(price)}"
        Glide.with(this).load(image).into(img)

        btnAdd.setOnClickListener {
            val item = FoodItem(id, name, desc, price, image)
            CartManager.addToCart(this, item)
            Toast.makeText(this, "$name added to cart", Toast.LENGTH_SHORT).show()
        }
    }
}
