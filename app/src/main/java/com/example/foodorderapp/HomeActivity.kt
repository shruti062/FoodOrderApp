package com.example.foodorderapp

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class HomeActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: FoodAdapter
    private val foods = arrayListOf<FoodItem>()
    private val filteredFoods = arrayListOf<FoodItem>()
    private val db = FirebaseFirestore.getInstance()
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        auth = FirebaseAuth.getInstance() // ✅ Initialize FirebaseAuth
        CartManager.init(this)

        val toolbar: Toolbar = findViewById(R.id.toolbarHome)
        setSupportActionBar(toolbar)

        recyclerView = findViewById(R.id.recyclerViewHome)
        recyclerView.layoutManager = LinearLayoutManager(this)

        adapter = FoodAdapter(this, filteredFoods) { selected ->
            val i = Intent(this, FoodDetailActivity::class.java)
            i.putExtra("id", selected.id)
            i.putExtra("name", selected.name)
            i.putExtra("desc", selected.description)
            i.putExtra("price", selected.price)
            i.putExtra("image", selected.imageUrl)
            startActivity(i)
        }

        recyclerView.adapter = adapter

        // 🔥 Load food data from Firestore
        loadFoodItems()
    }

    private fun loadFoodItems() {
        db.collection("foods")
            .get()
            .addOnSuccessListener { result ->
                foods.clear()
                for (doc in result) {
                    val item = doc.toObject(FoodItem::class.java)
                    foods.add(item)
                }
                filteredFoods.clear()
                filteredFoods.addAll(foods)
                adapter.notifyDataSetChanged()
            }
            .addOnFailureListener { e ->
                Toast.makeText(this, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.homemenu, menu)

        val searchItem = menu?.findItem(R.id.action_search)
        val searchView = searchItem?.actionView as? SearchView
        searchView?.queryHint = "Search food..."

        searchView?.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                filterList(query)
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                filterList(newText)
                return true
            }
        })

        return true
    }

    private fun filterList(query: String?) {
        filteredFoods.clear()
        if (query.isNullOrBlank()) {
            filteredFoods.addAll(foods)
        } else {
            val search = query.trim().lowercase()
            filteredFoods.addAll(
                foods.filter {
                    it.name.lowercase().contains(search) ||
                            it.description.lowercase().contains(search)
                }
            )
        }
        adapter.notifyDataSetChanged()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_cart -> {
                startActivity(Intent(this, CartActivity::class.java))
                true
            }
            R.id.action_orders -> {
                startActivity(Intent(this, MyOrdersActivity::class.java))
                true
            }
            R.id.action_admin -> {
                startActivity(Intent(this, AdminLoginActivity::class.java))
                true
            }
            R.id.action_logout -> {
                logoutUser()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    // ✅ Logout function (Firebase + SharedPreferences)
    private fun logoutUser() {
        // Sign out from Firebase
        auth.signOut()

        // Clear shared preferences if any
        val sharedPref = getSharedPreferences("UserSession", MODE_PRIVATE)
        sharedPref.edit().clear().apply()

        // Navigate to LoginActivity
        val intent = Intent(this, LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)

        Toast.makeText(this, "Logged out successfully!", Toast.LENGTH_SHORT).show()
    }
}
