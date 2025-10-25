package com.example.foodorderapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MenuActivity : AppCompatActivity() {

    private lateinit var recyclerViewMenu: RecyclerView
    private lateinit var btnGoToCart: Button
    private lateinit var foodAdapter: FoodAdapter
    private val foodList = mutableListOf<FoodItem>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu) // ensure layout id

        recyclerViewMenu = findViewById(R.id.recyclerViewMenu)
        btnGoToCart = findViewById(R.id.btnGoToCart)

        recyclerViewMenu.layoutManager = LinearLayoutManager(this)

        // adapter: FoodAdapter(foodList, onItemClick)
        foodAdapter = FoodAdapter(this, foodList) { selected ->
            val intent = Intent(this, FoodDetailActivity::class.java)
            intent.putExtra("foodName", selected.name)
            intent.putExtra("foodDesc", selected.description)
            intent.putExtra("foodPrice", selected.price)
            intent.putExtra("foodImage", selected.imageUrl)
            startActivity(intent)
        }
        recyclerViewMenu.adapter = foodAdapter

        loadFoodFromFirestore()

        btnGoToCart.setOnClickListener {
            startActivity(Intent(this, CartActivity::class.java))
        }
    }

    private fun loadFoodFromFirestore() {
        FirebaseManager.firestore.collection("menuItems")
            .get()
            .addOnSuccessListener { snapshot ->
                foodList.clear()
                for (doc in snapshot.documents) {
                    val item = doc.toObject(FoodItem::class.java)
                    if (item != null) foodList.add(item)
                }
                foodAdapter.notifyDataSetChanged()
            }
            .addOnFailureListener { e ->
                Log.e("MenuActivity", "Error loading menuItems", e)
            }
    }
}
