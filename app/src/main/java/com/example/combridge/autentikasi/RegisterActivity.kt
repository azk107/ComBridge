package com.example.combridge.autentikasi

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.combridge.R
import com.example.combridge.api.RetrofitClient
import com.example.combridge.auth.UserPreference
import com.example.combridge.auth.UserRepository
import com.example.combridge.auth.dataStore
import com.example.combridge.databinding.ActivityRegisterBinding
import com.example.combridge.welcome.WelcomeActivity
import kotlinx.coroutines.launch

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupView()
        setupAction()
        setupBackButton()
    }

    private fun setupView() {
        @Suppress("DEPRECATION")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }
        supportActionBar?.hide()
    }

    private fun setupAction() {
        binding.signupButton.setOnClickListener {
            val email = binding.edRegisterEmail.text.toString()
            val password = binding.edRegisterPassword.text.toString()
            val username = binding.edRegisterUsername.text.toString()

            if (password.length < 8) {
                binding.passwordEditTextLayout.error =
                return@setOnClickListener
            } else {
                binding.passwordEditTextLayout.error = null
            }

            // Memanggil register API
            lifecycleScope.launch {
                try {
                    val response = UserRepository.getInstance(
                        UserPreference.getInstance(applicationContext.dataStore),
                        RetrofitClient.apiService
                    ).register(username, email, password)

                    AlertDialog.Builder(this@RegisterActivity).apply {
                        setTitle("Success")
                        setMessage(response.message ?: "Registration successful!")
                        setPositiveButton("OK") { _, _ ->

                            navigateToLogin()
                        }
                        create().show()
                    }
                } catch (e: Exception) {
                    showToast("Registration failed: ${e.message}")
                }
            }
        }
    }

    private fun navigateToLogin() {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun navigateToBack() {
        val intent = Intent(this, WelcomeActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun setupBackButton() {
        val backButton = findViewById<LinearLayout>(R.id.backButtonLayout)

        backButton.setOnClickListener {

            navigateToBack()
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}