package com.example.combridge.beranda

import android.os.Bundle
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.combridge.R

class ImageDisplayActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image_display)

        val imageUrl = intent.getStringExtra("image_url")
        val imageView = findViewById<ImageView>(R.id.imageView)

        if (imageUrl != null) {
            Glide.with(this)
                .load(imageUrl)
                .into(imageView)
        } else {
            Toast.makeText(this, "URL gambar tidak ditemukan", Toast.LENGTH_SHORT).show()
        }
        val backButtonLayout = findViewById<LinearLayout>(R.id.backButtonLayout)
        backButtonLayout.setOnClickListener {
            onBackPressed()
        }
    }
}
