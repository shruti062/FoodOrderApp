package com.example.foodorderapp

import FoodItem
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class HomeActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: FoodAdapter
    private lateinit var foodList: ArrayList<FoodItem>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // ✅ Check session first
        val sessionManager = SessionManager(this)
        if (!sessionManager.isLoggedIn()) {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
            return
        }

        // ✅ If logged in, load layout
        setContentView(R.layout.activity_home)

        // Setup toolbar
        val toolbar: Toolbar = findViewById(R.id.toolbarHome)
        setSupportActionBar(toolbar)

        // Initialize RecyclerView
        recyclerView = findViewById(R.id.recyclerViewHome)
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Create food items
        foodList = ArrayList()
        foodList.add(FoodItem("Pizza", "Cheese Burst", 299, R.drawable.pizza))
        foodList.add(FoodItem("Burger", "Veg Loaded", 149, R.drawable.burger))
        foodList.add(FoodItem("Pasta", "White Sauce", 199, R.drawable.pasta))
        foodList.add(FoodItem("Coffee", "Cappuccino", 99, R.drawable.coffee))

        // Set Adapter
        adapter = FoodAdapter(this, foodList)
        recyclerView.adapter = adapter
    }

    // Inflate Toolbar Menu
    override fun onCreateOptionsMenu(menu: android.view.Menu?): Boolean {
        menuInflater.inflate(R.menu.homemenu, menu)
        return true
    }

    // Handle Menu Item Clicks
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_cart -> {
                startActivity(Intent(this, CartActivity::class.java))
                return true
            }
            R.id.action_orders -> {
                startActivity(Intent(this, MyOrdersActivity::class.java)) // ✅ OrdersActivity
                return true
            }
            R.id.action_logout -> {
                val sessionManager = SessionManager(this)
                sessionManager.logout()
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

}

