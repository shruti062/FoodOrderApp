package com.example.foodorderapp

import android.content.Intent
import android.os.Bundle
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class StartActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)

        val titleText = findViewById<TextView>(R.id.titleText)
        val btnLogin = findViewById<Button>(R.id.btnLogin)

        // Load animation
        val fadeInUp = AnimationUtils.loadAnimation(this, R.anim.fade_in_up)

        // Apply animation
        titleText.startAnimation(fadeInUp)
        btnLogin.startAnimation(fadeInUp)

        // 👇 Important: click listener to go to LoginActivity
        btnLogin.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }
    }
}
