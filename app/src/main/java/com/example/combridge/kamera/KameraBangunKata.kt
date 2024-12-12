//package com.example.combridge.kamera
//
//import android.Manifest
//import android.content.pm.PackageManager
//import android.os.Build
//import android.os.Bundle
//import android.util.Log
//import android.view.WindowInsets
//import android.view.WindowManager
//import android.widget.Toast
//import androidx.appcompat.app.AppCompatActivity
//import androidx.camera.core.CameraSelector
//import androidx.camera.core.ImageAnalysis
//import androidx.camera.core.Preview
//import androidx.camera.core.resolutionselector.AspectRatioStrategy
//import androidx.camera.core.resolutionselector.ResolutionSelector
//import androidx.camera.lifecycle.ProcessCameraProvider
//import androidx.core.app.ActivityCompat
//import androidx.core.content.ContextCompat
//import com.example.combridge.databinding.ActivityBangunKataBinding
//import org.tensorflow.lite.task.vision.classifier.Classifications
//import java.text.NumberFormat
//import java.util.concurrent.Executors
//
//class KameraBangunKataActivity : AppCompatActivity() {
//    private lateinit var binding: ActivityBangunKataBinding
//    private var cameraSelector: CameraSelector = CameraSelector.DEFAULT_BACK_CAMERA
//    private lateinit var imageClassifierHelper: ImageClassifierHelper
//    private var cameraProvider: ProcessCameraProvider? = null // CameraProvider sebagai nullable
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        binding = ActivityBangunKataBinding.inflate(layoutInflater)
//        setContentView(binding.root)
//
//        // Tombol close
//        binding.btnClose.setOnClickListener {
//            onBackPressed() // Menutup activity
//        }
//
//        // Mulai kamera
//        startCamera()
//    }
//
//    override fun onResume() {
//        super.onResume()
//        hideSystemUI()
//    }
//
//    override fun onPause() {
//        super.onPause()
//        // Unbind kamera ketika Activity ini tidak aktif hanya jika cameraProvider sudah diinisialisasi
//        cameraProvider?.unbindAll()
//    }
//
//    private fun startCamera() {
//        // Pastikan izin kamera sudah diberikan
//        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
//            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA), 0)
//        } else {
//            imageClassifierHelper = ImageClassifierHelper(
//                context = this,
//                classifierListener = object : ImageClassifierHelper.ClassifierListener {
//                    override fun onError(error: String) {
//                        runOnUiThread {
//                            Toast.makeText(this@KameraBangunKataActivity, error, Toast.LENGTH_SHORT).show()
//                        }
//                    }
//
//                    override fun onResults(results: List<Classifications>?, inferenceTime: Long) {
//                        runOnUiThread {
//                            results?.let {
//                                if (it.isNotEmpty() && it[0].categories.isNotEmpty()) {
//                                    val sortedCategories = it[0].categories.sortedByDescending { it?.score }
//                                    val displayResult = sortedCategories.joinToString("\n") {
//                                        "${it.label} " + NumberFormat.getPercentInstance().format(it.score).trim()
//                                    }
//                                    binding.tvResult.text = displayResult
//                                    binding.tvInferenceTime.text = "$inferenceTime ms"
//                                } else {
//                                    binding.tvResult.text = ""
//                                    binding.tvInferenceTime.text = ""
//                                }
//                            }
//                        }
//                    }
//                }
//            )
//
//            val cameraProviderFuture = ProcessCameraProvider.getInstance(this)
//            cameraProviderFuture.addListener({
//                cameraProvider = cameraProviderFuture.get() // Inisialisasi cameraProvider
//
//                val resolutionSelector = ResolutionSelector.Builder()
//                    .setAspectRatioStrategy(AspectRatioStrategy.RATIO_16_9_FALLBACK_AUTO_STRATEGY)
//                    .build()
//
//                val imageAnalyzer = ImageAnalysis.Builder()
//                    .setResolutionSelector(resolutionSelector)
//                    .setTargetRotation(binding.viewFinder2.display.rotation)
//                    .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
//                    .setOutputImageFormat(ImageAnalysis.OUTPUT_IMAGE_FORMAT_RGBA_8888)
//                    .build()
//
//                imageAnalyzer.setAnalyzer(Executors.newSingleThreadExecutor()) { image ->
//                    imageClassifierHelper.classifyImage(image)
//                }
//
//                val preview = Preview.Builder().build()
//                    .also { it.setSurfaceProvider(binding.viewFinder2.surfaceProvider) }
//
//                try {
//                    cameraProvider?.unbindAll() // Pastikan hanya satu kamera yang aktif
//                    cameraProvider?.bindToLifecycle(
//                        this,
//                        cameraSelector,
//                        preview,
//                        imageAnalyzer
//                    )
//                } catch (exc: Exception) {
//                    Log.e(TAG, "startCamera: ${exc.message}")
//                    Toast.makeText(
//                        this@KameraBangunKataActivity,
//                        "Gagal memunculkan kamera.",
//                        Toast.LENGTH_SHORT
//                    ).show()
//                }
//            }, ContextCompat.getMainExecutor(this))
//        }
//    }
//
//    private fun hideSystemUI() {
//        @Suppress("DEPRECATION")
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
//            window.insetsController?.hide(WindowInsets.Type.statusBars())
//        } else {
//            window.setFlags(
//                WindowManager.LayoutParams.FLAG_FULLSCREEN,
//                WindowManager.LayoutParams.FLAG_FULLSCREEN
//            )
//        }
//        supportActionBar?.hide()
//    }
//
//    companion object {
//        private const val TAG = "KameraBangunKataActivity"
//    }
//}