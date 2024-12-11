package com.example.combridge.beranda

import android.os.Bundle
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.example.combridge.R

class ImageDisplayActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image_display)

        // Ambil URL gambar dari intent
        val imageUrl = intent.getStringExtra("image_url")
        val imageView = findViewById<ImageView>(R.id.imageView)

        // Menampilkan gambar dengan Glide jika URL gambar ada
        if (imageUrl != null) {
            Glide.with(this)
                .load(imageUrl)
                .into(imageView)
        } else {
            Toast.makeText(this, "URL gambar tidak ditemukan", Toast.LENGTH_SHORT).show()
        }

        // Menambahkan aksi klik untuk tombol kembali
        val backButtonLayout = findViewById<LinearLayout>(R.id.backButtonLayout)
        backButtonLayout.setOnClickListener {
            onBackPressed()
        }
    }
}
