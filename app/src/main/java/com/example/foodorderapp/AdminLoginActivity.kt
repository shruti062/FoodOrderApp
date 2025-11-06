package com.example.foodorderapp

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class AdminLoginActivity : AppCompatActivity() {

    private lateinit var inputAdminEmail: EditText
    private lateinit var inputAdminPassword: EditText
    private lateinit var btnAdminLogin: Button
    private lateinit var btnTogglePassword: ImageView

    // 👨‍💻 Hardcoded Admin Credentials (you can move these to Firebase later)
    private val adminEmail = "admin@foodapp.com"
    private val adminPassword = "admin123"

    // 👁️ Track password visibility
    private var isPasswordVisible = false

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_login)

        // Initialize views
        inputAdminEmail = findViewById(R.id.inputAdminEmail)
        inputAdminPassword = findViewById(R.id.inputAdminPassword)
        btnAdminLogin = findViewById(R.id.btnAdminLogin)
        btnTogglePassword = findViewById(R.id.btnTogglePassword)

        // 👁️ Toggle show/hide password
        btnTogglePassword.setOnClickListener {
            if (isPasswordVisible) {
                // Hide password
                inputAdminPassword.inputType =
                    InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
                btnTogglePassword.setImageResource(R.drawable.ic_visibility_off)
            } else {
                // Show password
                inputAdminPassword.inputType = InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
                btnTogglePassword.setImageResource(R.drawable.ic_visibility)
            }
            // Keep cursor at the end of text
            inputAdminPassword.setSelection(inputAdminPassword.text.length)
            isPasswordVisible = !isPasswordVisible
        }

        // 🔐 Admin Login Logic
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
