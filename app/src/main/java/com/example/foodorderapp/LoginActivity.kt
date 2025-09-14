package com.example.foodorderapp

import android.content.Intent
import android.os.Bundle
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val sessionManager = SessionManager(this)

        // ✅ If user already logged in → go directly to HomeActivity
        if (sessionManager.isLoggedIn()) {
            startActivity(Intent(this, HomeActivity::class.java))
            finish()
            return
        }

        // Views
        val container = findViewById<LinearLayout>(R.id.loginContainer)
        val edtEmail = findViewById<EditText>(R.id.edtEmail)
        val edtPassword = findViewById<EditText>(R.id.edtPassword)
        val btnLogin = findViewById<Button>(R.id.btnLogin)
        val txtGoSignup = findViewById<TextView>(R.id.txtGoSignup)

        // Animate login form when activity opens
        container.translationY = 200f
        container.alpha = 0f
        container.animate()
            .translationY(0f)
            .alpha(1f)
            .setInterpolator(AccelerateDecelerateInterpolator())
            .setDuration(1000)
            .start()

        // ✅ Login button action
        btnLogin.setOnClickListener {
            val email = edtEmail.text.toString().trim()
            val password = edtPassword.text.toString().trim()

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please enter email and password", Toast.LENGTH_SHORT).show()
            } else {
                // 🔑 Replace with real DB/auth check later
                if (email == "admin@gmail.com" && password == "1234") {
                    // Save session
                    sessionManager.createLoginSession(email)

                    Toast.makeText(this, "Login Successful!", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this, HomeActivity::class.java))
                    finish()
                } else {
                    Toast.makeText(this, "Invalid credentials", Toast.LENGTH_SHORT).show()
                }
            }
        }

        // Go to SignupActivity
        txtGoSignup.setOnClickListener {
            val intent = Intent(this, SignupActivity::class.java)
            startActivity(intent)
        }
    }
}
