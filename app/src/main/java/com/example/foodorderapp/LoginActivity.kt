package com.example.foodorderapp

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // ✅ Auto-login if already logged in
        val sharedPref = getSharedPreferences("UserSession", Context.MODE_PRIVATE)
        val savedEmail = sharedPref.getString("user_email", null)
        if (savedEmail != null) {
            startActivity(Intent(this, HomeActivity::class.java))
            finish()
            return
        }

        setContentView(R.layout.activity_login)
        auth = FirebaseAuth.getInstance()

        val edtEmail = findViewById<EditText>(R.id.edtEmail)
        val edtPassword = findViewById<EditText>(R.id.edtPassword)
        val btnLogin = findViewById<Button>(R.id.btnLogin)
        val txtGoSignup = findViewById<TextView>(R.id.txtGoSignup)
        val imgTogglePassword = findViewById<ImageView>(R.id.imgTogglePassword) // 👁️ Eye icon

        // 👁️ Show / Hide Password Functionality
        var isPasswordVisible = false
        imgTogglePassword.setOnClickListener {
            isPasswordVisible = !isPasswordVisible
            if (isPasswordVisible) {
                edtPassword.inputType = InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
                imgTogglePassword.setImageResource(R.drawable.ic_visibility) // 👁️
            } else {
                edtPassword.inputType =
                    InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
                imgTogglePassword.setImageResource(R.drawable.ic_visibility_off) // 🚫👁️
            }
            edtPassword.setSelection(edtPassword.text.length)
        }

        // 🔐 Login Button
        btnLogin.setOnClickListener {
            val email = edtEmail.text.toString().trim()
            val password = edtPassword.text.toString().trim()

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Enter email and password", Toast.LENGTH_SHORT).show()
            } else {
                auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            // ✅ Save login session
                            val editor = sharedPref.edit()
                            editor.putString("user_email", email)
                            editor.apply()

                            Toast.makeText(this, "Login successful!", Toast.LENGTH_SHORT).show()
                            startActivity(Intent(this, HomeActivity::class.java))
                            finish()
                        } else {
                            Toast.makeText(this, "Invalid credentials", Toast.LENGTH_SHORT).show()
                        }
                    }
            }
        }

        // 🔄 Navigate to Signup Page
        txtGoSignup.setOnClickListener {
            startActivity(Intent(this, SignupActivity::class.java))
        }
    }
}
