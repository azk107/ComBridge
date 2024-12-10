package com.example.combridge.welcome

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.combridge.R
import com.example.combridge.autentikasi.LoginActivity
import com.example.combridge.autentikasi.RegisterActivity

class WelcomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)

        val signInButton = findViewById<Button>(R.id.btn_sign_in)
        val signUpButton = findViewById<Button>(R.id.btn_sign_up)
        val backButton = findViewById<LinearLayout>(R.id.backButtonLayout)

        // Listener untuk tombol Sign In
        signInButton.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

        // Listener untuk tombol Sign Up
        signUpButton.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

        // Listener untuk tombol Back
        backButton.setOnClickListener {
            // Memanggil onBackPressed() untuk kembali ke halaman sebelumnya
            onBackPressedDispatcher.onBackPressed()
        }
    }
}