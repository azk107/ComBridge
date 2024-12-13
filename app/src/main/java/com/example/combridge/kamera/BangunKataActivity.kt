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
import com.example.combridge.databinding.ActivityBangunKataBinding
import org.tensorflow.lite.task.vision.classifier.Classifications
import java.text.NumberFormat
import java.util.Locale
import android.speech.tts.TextToSpeech

class BangunKataActivity : AppCompatActivity() {
    private lateinit var binding: ActivityBangunKataBinding
    private var cameraSelector: CameraSelector = CameraSelector.DEFAULT_BACK_CAMERA
    private lateinit var bangunKataHelper : BangunKataHelper
    private val wordBuilder = StringBuilder()
    private var detectedLetter: String? = null
    private lateinit var textToSpeech: TextToSpeech

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBangunKataBinding.inflate(layoutInflater)
        setContentView(binding.root)

        textToSpeech = TextToSpeech(this) { status ->
            if (status == TextToSpeech.SUCCESS) {
                textToSpeech.language = Locale.getDefault()
            } else {
                Toast.makeText(this, "Text-to-Speech tidak tersedia.", Toast.LENGTH_SHORT).show()
            }
        }

        binding.btnKeluar.setOnClickListener {
            onBackPressed()
        }

        binding.EjaKata.setOnClickListener {
            val textToRead = wordBuilder.toString()
            if (textToRead.isNotEmpty()) {
                textToSpeech.speak(textToRead, TextToSpeech.QUEUE_FLUSH, null, null)
            } else {
                Toast.makeText(this, "Tidak ada kata untuk dibaca.", Toast.LENGTH_SHORT).show()
            }
        }

        binding.SimpanKata.setOnClickListener {
            detectedLetter?.let { letter ->
                wordBuilder.append(letter)
                Toast.makeText(this, "Kata disimpan: ${wordBuilder.toString()}", Toast.LENGTH_SHORT).show()
                binding.Prediksi.text = wordBuilder.toString()
                binding.Kata.text = "Hasil: ${wordBuilder.toString()}"
                detectedLetter = null
            } ?: run {
                Toast.makeText(this, "Tidak ada huruf untuk disimpan.", Toast.LENGTH_SHORT).show()
            }
        }

        binding.HapusKata.setOnClickListener {
            wordBuilder.clear()
            binding.Prediksi.text = ""
            binding.Kata.text = "Hasil: "
            Toast.makeText(this, "Semua kata telah dihapus.", Toast.LENGTH_SHORT).show()
        }

        startCamera()
    }

    override fun onDestroy() {
        super.onDestroy()
        if (::textToSpeech.isInitialized) {
            textToSpeech.stop()
            textToSpeech.shutdown()
        }
    }

    public override fun onResume() {
        super.onResume()
        hideSystemUI()
    }
    private fun startCamera() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA), 0)
        } else {
            bangunKataHelper = BangunKataHelper(
                context = this,
                classifierListener = object : BangunKataHelper.ClassifierListener {
                    override fun onError(error: String) {
                        runOnUiThread {
                            Toast.makeText(this@BangunKataActivity, error, Toast.LENGTH_SHORT).show()
                        }
                    }

                    override fun onResults(results: List<Classifications>?, inferenceTime: Long) {
                        runOnUiThread {
                            results?.let {
                                if (it.isNotEmpty() && it[0].categories.isNotEmpty()) {
                                    val topResult = it[0].categories.maxByOrNull { it.score }?.label ?: ""
                                    if (topResult.isNotEmpty() && topResult != detectedLetter) {
                                        detectedLetter = topResult
                                    }
                                    val sortedCategories = it[0].categories.sortedByDescending { it?.score }
                                    val displayResult = sortedCategories.joinToString("\n") {
                                        "${it.label} " + NumberFormat.getPercentInstance().format(it.score).trim()
                                    }
                                    binding.Prediksi.text = displayResult
                                } else {
                                    binding.Prediksi.text = ""
                                }
                            }
                        }
                    }
                }
            )

            bangunKataHelper.setTextViews(binding.Prediksi, binding.SimpanKata)

            val cameraProviderFuture = ProcessCameraProvider.getInstance(this)
            cameraProviderFuture.addListener({
                val cameraProvider: ProcessCameraProvider = cameraProviderFuture.get()

                val resolutionSelector = ResolutionSelector.Builder()
                    .setAspectRatioStrategy(AspectRatioStrategy.RATIO_16_9_FALLBACK_AUTO_STRATEGY)
                    .build()

                val imageAnalyzer = ImageAnalysis.Builder()
                    .setResolutionSelector(resolutionSelector)
                    .setTargetRotation(binding.Camera2.display.rotation)
                    .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                    .setOutputImageFormat(ImageAnalysis.OUTPUT_IMAGE_FORMAT_RGBA_8888)
                    .build()
                val preview = Preview.Builder().build()
                    .also { it.setSurfaceProvider(binding.Camera2.surfaceProvider) }

                imageAnalyzer.setAnalyzer(ContextCompat.getMainExecutor(this), ImageAnalysis.Analyzer { imageProxy ->
                    bangunKataHelper.classifyImage(imageProxy)
                })

                try {
                    cameraProvider?.unbindAll()
                    cameraProvider?.bindToLifecycle(
                        this,
                        cameraSelector,
                        preview,
                        imageAnalyzer
                    )
                } catch (exc: Exception) {
                    Log.e(CameraActivity.TAG, "startCamera: ${exc.message}")
                    Toast.makeText(
                        this@BangunKataActivity,
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
    private const val TAG = "CameraActivity"
}
}
