package com.example.combridge.autentikasi

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.LinearLayout
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.example.combridge.MainActivity
import com.example.combridge.R
import com.example.combridge.api.RetrofitClient
import com.example.combridge.auth.UserPreference
import com.example.combridge.auth.UserRepository
import com.example.combridge.auth.dataStore
import com.example.combridge.beranda.BerandaFragment
import com.example.combridge.databinding.ActivityLoginBinding
import com.example.combridge.welcome.WelcomeActivity
import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Menggunakan View Binding
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupAction()
        setupBackButton()
    }

    private fun setupAction() {
        binding.loginButton.setOnClickListener {
            val email = binding.edLoginEmail.text.toString()
            val password = binding.edLoginPassword.text.toString()

            if (email.isBlank() || password.isBlank()) {
                Toast.makeText(this, "Email atau Password tidak boleh kosong", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Memanggil login API
            lifecycleScope.launch {
                try {
                    val response = UserRepository.getInstance(
                        UserPreference.getInstance(applicationContext.dataStore),
                        RetrofitClient.apiService
                    ).login(email, password)

                    if (!response.error) {
                        // Simpan token jika ada
                        response.token?.let { token ->
                            val userPref = UserPreference.getInstance(applicationContext.dataStore)
                            userPref.saveToken(token)
                        }

                        Toast.makeText(this@LoginActivity, "Login berhasil!", Toast.LENGTH_SHORT).show()
                        val intent = Intent(this@LoginActivity, MainActivity::class.java)
                        startActivity(intent)
                        finish()
                    } else {
                        Toast.makeText(this@LoginActivity, "Login gagal: ${response.message}", Toast.LENGTH_SHORT).show()
                    }
                } catch (e: Exception) {
                    Log.e("LoginActivity", "Login failed: ${e.message}")
                    Toast.makeText(this@LoginActivity, "Login failed: ${e.message}", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun navigateToBack() {
        val intent = Intent(this, WelcomeActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun setupBackButton() {
        val backButton = findViewById<LinearLayout>(R.id.backButtonLayout)

        backButton.setOnClickListener {
            // Panggil fungsi untuk kembali ke halaman Welcome
            navigateToBack()
        }
    }
}