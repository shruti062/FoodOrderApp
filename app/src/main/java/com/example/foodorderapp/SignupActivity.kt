package com.example.foodorderapp

import android.os.Bundle
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class SignupActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        // Views
        val container = findViewById<LinearLayout>(R.id.signupContainer)
        val edtName = findViewById<EditText>(R.id.edtName)
        val edtEmail = findViewById<EditText>(R.id.edtEmail)
        val edtPassword = findViewById<EditText>(R.id.edtPassword)
        val btnSignup = findViewById<Button>(R.id.btnSignup)
        val txtGoLogin = findViewById<TextView>(R.id.txtGoLogin)

        // Animate container
        container.translationY = 200f
        container.alpha = 0f
        container.animate()
            .translationY(0f)
            .alpha(1f)
            .setInterpolator(AccelerateDecelerateInterpolator())
            .setDuration(1000)
            .start()

        // Button action
        btnSignup.setOnClickListener {
            val name = edtName.text.toString().trim()
            val email = edtEmail.text.toString().trim()
            val password = edtPassword.text.toString().trim()

            if (name.isEmpty() || email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Signup successful!", Toast.LENGTH_SHORT).show()
                // Later: Save user in Firebase/MySQL
                finish()
            }
        }

        // Go to Login
        txtGoLogin.setOnClickListener {
            finish() // closes signup and goes back to LoginActivity
        }
    }
}
