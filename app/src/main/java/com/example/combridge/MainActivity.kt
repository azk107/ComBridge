package com.example.combridge

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import com.example.combridge.beranda.BerandaFragment
import com.example.combridge.beranda.KataFragment
import com.example.combridge.kamera.CameraActivity
import com.example.combridge.kamera.KameraFragment
import com.example.combridge.pengaturan.PengaturanFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Load default fragment (Beranda)
        loadFragment(BerandaFragment())

        // Bottom Navigation Setup
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_beranda -> {
                    loadFragment(BerandaFragment())
                    true
                }
                R.id.nav_kamera -> {
                    loadFragment(KameraFragment()) // Load CameraFragment
                    true
                }
                R.id.nav_pengaturan -> {
                    loadFragment(PengaturanFragment()) // Load PengaturanFragment
                    true
                }
                else -> false
            }
        }
    }

    private fun loadFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
    }
}
