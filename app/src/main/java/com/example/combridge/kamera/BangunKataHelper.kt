package com.example.combridge.kamera

import android.content.Context
import android.graphics.Bitmap
import android.os.SystemClock
import android.util.Log
import android.view.Surface
import android.widget.TextView
import androidx.camera.core.ImageProxy
import com.example.combridge.R
import org.tensorflow.lite.DataType
import org.tensorflow.lite.support.common.ops.CastOp
import org.tensorflow.lite.task.core.BaseOptions
import org.tensorflow.lite.task.vision.classifier.Classifications
import org.tensorflow.lite.task.vision.classifier.ImageClassifier
import org.tensorflow.lite.support.image.ImageProcessor
import org.tensorflow.lite.support.image.TensorImage
import org.tensorflow.lite.support.image.ops.ResizeOp
import org.tensorflow.lite.task.core.vision.ImageProcessingOptions
import java.text.NumberFormat

class BangunKataHelper(
    var threshold: Float = 0.1f,
    var maxResults: Int = 3,
    val modelName: String = "mobilenetv2-bisindo-mode-v2.tflite",
    val context: Context,
    val classifierListener: ClassifierListener?
) {
    private var imageClassifier: ImageClassifier? = null
    private var lastResult: String = ""  // Untuk menyimpan hasil sebelumnya
    private val wordBuilder: StringBuilder = StringBuilder()
    private lateinit var tvResult: TextView  // Tambahkan properti ini untuk tvResult
    private lateinit var tvInferenceTime: TextView  // Tambahkan properti ini untuk tvInferenceTime

    init {
        setupImageClassifier()
    }

    fun setTextViews(tvResult: TextView, tvInferenceTime: TextView) {
        this.tvResult = tvResult
        this.tvInferenceTime = tvInferenceTime
    }

    private fun setupImageClassifier() {
        val optionsBuilder = ImageClassifier.ImageClassifierOptions.builder()
            .setScoreThreshold(threshold)
            .setMaxResults(maxResults)

        val baseOptionsBuilder = BaseOptions.builder()
            .setNumThreads(4)  // Set the number of threads for inference
        optionsBuilder.setBaseOptions(baseOptionsBuilder.build())

        try {
            imageClassifier = ImageClassifier.createFromFileAndOptions(
                context,
                modelName,
                optionsBuilder.build()
            )
        } catch (e: IllegalStateException) {
            classifierListener?.onError(context.getString(R.string.image_classifier_failed))
            Log.e(TAG, e.message.toString())
        }
    }

    fun classifyImage(image: ImageProxy) {
        if (imageClassifier == null) {
            setupImageClassifier()
        }

        val imageProcessor = ImageProcessor.Builder()
            .add(ResizeOp(224, 224, ResizeOp.ResizeMethod.NEAREST_NEIGHBOR))
            .add(CastOp(DataType.UINT8))
            .build()

        val tensorImage = imageProcessor.process(TensorImage.fromBitmap(toBitmap(image)))
        val imageProcessingOptions = ImageProcessingOptions.builder()
            .setOrientation(getOrientationFromRotation(image.imageInfo.rotationDegrees))
            .build()

        var inferenceTime = SystemClock.uptimeMillis()
        val results = imageClassifier?.classify(tensorImage, imageProcessingOptions)
        inferenceTime = SystemClock.uptimeMillis() - inferenceTime
        classifierListener?.onResults(results, inferenceTime)
    }

    private fun getOrientationFromRotation(rotation: Int): ImageProcessingOptions.Orientation {
        return when (rotation) {
            Surface.ROTATION_270 -> ImageProcessingOptions.Orientation.BOTTOM_RIGHT
            Surface.ROTATION_180 -> ImageProcessingOptions.Orientation.RIGHT_BOTTOM
            Surface.ROTATION_90 -> ImageProcessingOptions.Orientation.TOP_LEFT
            else -> ImageProcessingOptions.Orientation.RIGHT_TOP
        }
    }

    private fun toBitmap(image: ImageProxy): Bitmap {
        val bitmapBuffer = Bitmap.createBitmap(
            image.width,
            image.height,
            Bitmap.Config.ARGB_8888
        )
        image.use { bitmapBuffer.copyPixelsFromBuffer(image.planes[0].buffer) }
        image.close()
        return bitmapBuffer
    }

    // Fungsi untuk memperbarui tampilan hasil klasifikasi
    fun updateResults(
        results: List<Classifications>?,
        inferenceTime: Long
    ) {
        // Menggunakan UI Thread untuk memastikan update tampilan dilakukan di thread utama
        results?.let {
            if (it.isNotEmpty() && it[0].categories.isNotEmpty()) {
                val topResult = it[0].categories.maxByOrNull { it.score }?.label ?: ""

                // Simpan hasil klasifikasi sementara tanpa menambahkannya ke wordBuilder
                if (topResult.isNotEmpty() && topResult != lastResult) {
                    lastResult = topResult // Perbarui hasil terakhir yang terdeteksi
                    // Update tampilan sementara (tanpa menambahkan ke wordBuilder)
                    tvResult.text = topResult
                }

                val displayResult = it[0].categories.joinToString("\n") {
                    "${it.label} " + NumberFormat.getPercentInstance().format(it.score).trim()
                }
                tvInferenceTime.text = "$inferenceTime ms"  // Tampilkan waktu inferensi
            } else {
                tvResult.text = ""
                tvInferenceTime.text = ""
            }
        }
    }


    interface ClassifierListener {
        fun onError(error: String)
        fun onResults(
            results: List<Classifications>?,
            inferenceTime: Long
        )
    }

    companion object {
        private const val TAG = "BangunActivityHelper"
    }
}
