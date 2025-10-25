package com.example.foodorderapp

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore

class AddFoodActivity : AppCompatActivity() {

    private lateinit var inputFoodName: EditText
    private lateinit var inputFoodPrice: EditText
    private lateinit var inputFoodDescription: EditText
    private lateinit var inputFoodImageUrl: EditText
    private lateinit var btnAddFood: Button
    private lateinit var previewImage: ImageView

    private val db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_food)

        inputFoodName = findViewById(R.id.inputFoodName)
        inputFoodPrice = findViewById(R.id.inputFoodPrice)
        inputFoodDescription = findViewById(R.id.inputFoodDescription)
        inputFoodImageUrl = findViewById(R.id.inputFoodImageUrl)
        btnAddFood = findViewById(R.id.btnAddFood)
        previewImage = findViewById(R.id.previewImage)

        // Load image preview when admin enters URL
        inputFoodImageUrl.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                val imageUrl = inputFoodImageUrl.text.toString().trim()
                if (imageUrl.isNotEmpty()) {
                    ImageLoader.loadImage(previewImage, imageUrl)
                }
            }
        }

        btnAddFood.setOnClickListener {
            val name = inputFoodName.text.toString().trim()
            val priceText = inputFoodPrice.text.toString().trim()
            val description = inputFoodDescription.text.toString().trim()
            val imageUrl = inputFoodImageUrl.text.toString().trim()

            if (name.isEmpty() || priceText.isEmpty() || description.isEmpty() || imageUrl.isEmpty()) {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val price = priceText.toDoubleOrNull()
            if (price == null || price <= 0) {
                Toast.makeText(this, "Enter a valid price", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val foodItem = hashMapOf(
                "name" to name,
                "price" to price,
                "description" to description,
                "imageUrl" to imageUrl
            )

            db.collection("foods")
                .add(foodItem)
                .addOnSuccessListener {
                    Toast.makeText(this, "✅ Food item added successfully!", Toast.LENGTH_LONG).show()
                    clearFields()
                }
                .addOnFailureListener { e ->
                    Toast.makeText(this, "❌ Failed: ${e.message}", Toast.LENGTH_LONG).show()
                }
        }
    }

    private fun clearFields() {
        inputFoodName.text.clear()
        inputFoodPrice.text.clear()
        inputFoodDescription.text.clear()
        inputFoodImageUrl.text.clear()
        previewImage.setImageResource(R.drawable.ic_image)
    }
}
