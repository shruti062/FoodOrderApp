package com.example.foodorderapp

import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.ktx.toObject

class AddressActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var addBtn: Button
    private lateinit var adapter: SimpleAddressAdapter
    private val addressList = mutableListOf<Address>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_address)

        recyclerView = findViewById(R.id.recyclerViewAddresses)
        addBtn = findViewById(R.id.btnAddAddress)

        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = SimpleAddressAdapter(addressList)
        recyclerView.adapter = adapter

        loadAddressesFromFirestore()

        addBtn.setOnClickListener {
            // For quick demo: create a sample address.
            // Replace this with a proper form screen for production.
            val uid = FirebaseManager.auth.currentUser?.uid ?: "guest"
            val newAddress = hashMapOf(
                "name" to "Home",
                "phone" to "9876543210",
                "houseNo" to "123 Example St",
                "city" to "Kolkata",
                "state" to "WB",
                "pinCode" to "700001"
            )
            FirebaseManager.firestore.collection("users")
                .document(uid)
                .collection("addresses")
                .add(newAddress)
                .addOnSuccessListener {
                    Toast.makeText(this, "Address saved", Toast.LENGTH_SHORT).show()
                    loadAddressesFromFirestore()
                }
                .addOnFailureListener { e ->
                    Toast.makeText(this, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
                }
        }
    }

    private fun loadAddressesFromFirestore() {
        val uid = FirebaseManager.auth.currentUser?.uid ?: "guest"
        FirebaseManager.firestore.collection("users")
            .document(uid)
            .collection("addresses")
            .get()
            .addOnSuccessListener { snap ->
                addressList.clear()
                for (doc in snap.documents) {
                    val a = doc.toObject(Address::class.java)
                    // set doc id if you want
                    if (a != null) addressList.add(a.copy(id = doc.id))
                }
                adapter.notifyDataSetChanged()
            }
            .addOnFailureListener { e ->
                Toast.makeText(this, "Failed: ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }
}
