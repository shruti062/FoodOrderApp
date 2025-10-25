package com.example.foodorderapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class AdminLoginActivity : AppCompatActivity() {

    private lateinit var inputAdminEmail: EditText
    private lateinit var inputAdminPassword: EditText
    private lateinit var btnAdminLogin: Button

    // 🔐 You can later move these to Firebase or SharedPreferences
    private val adminEmail = "admin@foodapp.com"
    private val adminPassword = "admin123"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_login)

        // Initialize views
        inputAdminEmail = findViewById(R.id.inputAdminEmail)
        inputAdminPassword = findViewById(R.id.inputAdminPassword)
        btnAdminLogin = findViewById(R.id.btnAdminLogin)

        btnAdminLogin.setOnClickListener {
            val email = inputAdminEmail.text.toString().trim()
            val password = inputAdminPassword.text.toString().trim()

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please enter all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (email == adminEmail && password == adminPassword) {
                Toast.makeText(this, "Login successful!", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, AddFoodActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                Toast.makeText(this, "Invalid credentials", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
