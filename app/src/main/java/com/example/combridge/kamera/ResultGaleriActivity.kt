package com.example.combridge.kamera

import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.combridge.databinding.ActivityResultGaleriBinding

class ResultGaleriActivity : AppCompatActivity() {
    private lateinit var binding: ActivityResultGaleriBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityResultGaleriBinding.inflate(layoutInflater)

        setContentView(binding.root)

        val imageUri = Uri.parse(intent.getStringExtra(EXTRA_IMAGE_URI))
        val resultText = intent.getStringExtra(EXTRA_RESULT)
        val confidenceScore = intent.getFloatExtra(EXTRA_CONFIDENCE, 0.0f)

        imageUri?.let {
            binding.resultImage.setImageURI(it)
            binding.confidenceText.text = "Akurasi: ${"%.2f".format(confidenceScore * 100)}%"
        }

        if (resultText != null) {
            binding.resultText.text = "Prediksi: $resultText"
        } else {
            binding.resultText.text = "Prediksi tidak tersedia"
        }
        binding.btnKeluar.setOnClickListener {
            onBackPressed()
        }
    }
    companion object {
        const val EXTRA_IMAGE_URI = "extra_image_uri"
        const val EXTRA_RESULT = "extra_result"
        const val EXTRA_CONFIDENCE = "extra_confidence"
    }

}