package com.example.combridge.kamera

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.combridge.R
import com.example.combridge.databinding.ActivityGaleriBinding
import org.tensorflow.lite.task.vision.classifier.Classifications

class GaleriActivity : AppCompatActivity(), GaleriClassifierHelper.ClassifierListener {
    private lateinit var binding: ActivityGaleriBinding
    private lateinit var galeriClassifierHelper: GaleriClassifierHelper
    private var currentImageUri: Uri? = null
    private val viewModel: GaleriViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityGaleriBinding.inflate(layoutInflater)
        setContentView(binding.root)

        galeriClassifierHelper = GaleriClassifierHelper(context = this, classifierListener = this)

        binding.galleryButton.setOnClickListener { startGallery() }

        viewModel.currentImageUri?.let { uri ->
            currentImageUri = uri
            showImage()
        }

        binding.analyzeButton.setOnClickListener {
            viewModel.currentImageUri?.let { uri ->
                currentImageUri = uri
                analyzeImage(uri)
            } ?: run {
                showToast(getString(R.string.empty_image_warning))
            }
        }
        binding.btnKeluar.setOnClickListener {
            onBackPressed()
        }
    }

    private fun startGallery() {
        launcherGallery.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
    }

    private val launcherGallery = registerForActivityResult(
        ActivityResultContracts.PickVisualMedia()
    ) { uri: Uri? ->
        if (uri != null) {
            currentImageUri = uri
            viewModel.currentImageUri = uri
            showImage()
        } else {
        }
    }

    private fun showImage() {
        currentImageUri?.let {
            binding.previewImageView.setImageURI(it)
        }
    }

    private fun analyzeImage(uri: Uri) {
        galeriClassifierHelper.classifyStaticImage(uri)
    }

    override fun onResults(results: List<Classifications>?, inferenceTime: Long) {
        results?.firstOrNull()?.categories?.firstOrNull()?.let { category ->
            val predictionResult = category.label
            val confidenceScore = category.score

            val intent = Intent(this, ResultGaleriActivity::class.java).apply {
                putExtra(ResultGaleriActivity.EXTRA_IMAGE_URI, currentImageUri.toString())
                putExtra(ResultGaleriActivity.EXTRA_RESULT, predictionResult)
                putExtra(ResultGaleriActivity.EXTRA_CONFIDENCE, confidenceScore)
            }
            startActivity(intent)
        } ?: run {
            showToast("Prediction failed.")
        }
    }

    override fun onError(error: String) {
        showToast("Error: $error")
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

}