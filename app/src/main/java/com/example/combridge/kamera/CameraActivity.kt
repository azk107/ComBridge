package com.example.combridge.kamera

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.Preview
import androidx.camera.core.resolutionselector.AspectRatioStrategy
import androidx.camera.core.resolutionselector.ResolutionSelector
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.combridge.databinding.ActivityCameraBinding
import org.tensorflow.lite.task.vision.classifier.Classifications
import java.text.NumberFormat
import java.util.concurrent.Executors

class CameraActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCameraBinding
    private var cameraSelector: CameraSelector = CameraSelector.DEFAULT_BACK_CAMERA
    private lateinit var imageClassifierHelper: ImageClassifierHelper
    private var cameraProvider: ProcessCameraProvider? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCameraBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.Keluar.setOnClickListener {
            onBackPressed()
        }

        startCamera()
    }

    override fun onResume() {
        super.onResume()
        hideSystemUI()
    }

    override fun onPause() {
        super.onPause()
        cameraProvider?.unbindAll()
    }

    private fun startCamera() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA), 0)
        } else {
            imageClassifierHelper = ImageClassifierHelper(
                context = this,
                classifierListener = object : ImageClassifierHelper.ClassifierListener {
                    override fun onError(error: String) {
                        runOnUiThread {
                            Toast.makeText(this@CameraActivity, error, Toast.LENGTH_SHORT).show()
                        }
                    }

                    override fun onResults(results: List<Classifications>?, inferenceTime: Long) {
                        runOnUiThread {
                            results?.let {
                                if (it.isNotEmpty() && it[0].categories.isNotEmpty()) {
                                    val sortedCategories = it[0].categories.sortedByDescending { it?.score }
                                    val displayResult = sortedCategories.joinToString("\n") {
                                        "${it.label} " + NumberFormat.getPercentInstance().format(it.score).trim()
                                    }
                                    binding.Prediksii.text = displayResult
                                    binding.tvPrediksi.text = "$inferenceTime ms"
                                } else {
                                    binding.Prediksii.text = ""
                                    binding.tvPrediksi.text = ""
                                }
                            }
                        }
                    }
                }
            )

            val cameraProviderFuture = ProcessCameraProvider.getInstance(this)
            cameraProviderFuture.addListener({
                cameraProvider = cameraProviderFuture.get() // Inisialisasi cameraProvider

                val resolutionSelector = ResolutionSelector.Builder()
                    .setAspectRatioStrategy(AspectRatioStrategy.RATIO_16_9_FALLBACK_AUTO_STRATEGY)
                    .build()

                val imageAnalyzer = ImageAnalysis.Builder()
                    .setResolutionSelector(resolutionSelector)
                    .setTargetRotation(binding.Camera1.display.rotation)
                    .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                    .setOutputImageFormat(ImageAnalysis.OUTPUT_IMAGE_FORMAT_RGBA_8888)
                    .build()

                imageAnalyzer.setAnalyzer(Executors.newSingleThreadExecutor()) { image ->
                    imageClassifierHelper.classifyImage(image)
                }

                val preview = Preview.Builder().build()
                    .also { it.setSurfaceProvider(binding.Camera1.surfaceProvider) }

                try {
                    cameraProvider?.unbindAll() // Pastikan hanya satu kamera yang aktif
                    cameraProvider?.bindToLifecycle(
                        this,
                        cameraSelector,
                        preview,
                        imageAnalyzer
                    )
                } catch (exc: Exception) {
                    Log.e(TAG, "startCamera: ${exc.message}")
                    Toast.makeText(
                        this@CameraActivity,
                        "Gagal memunculkan kamera.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }, ContextCompat.getMainExecutor(this))
        }
    }

    private fun hideSystemUI() {
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

    companion object {
        const val TAG = "CameraActivity"
    }
}
