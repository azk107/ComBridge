package com.example.combridge.beranda

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.combridge.R
import com.example.combridge.adapter.AngkaAdapter

class AngkaFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate layout FragmentHuruf
        val view = inflater.inflate(R.layout.fragment_angka, container, false)

        // Setup RecyclerView
        val rvAngka = view.findViewById<RecyclerView>(R.id.rv_angka)
        rvAngka.layoutManager = GridLayoutManager(context, 2)
        val adapter = AngkaAdapter(getAngkaList())
        rvAngka.adapter = adapter

        // Set item click listener
        adapter.setOnItemClickListener { angka ->
            onAngkaClick(angka)
        }

        return view
    }

    private fun getAngkaList(): List<String> {
        return listOf("0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10")
    }

    private fun onAngkaClick(angka: String) {
        // Map untuk menyimpan URL berdasarkan huruf
        val angkaToImageUrl = mapOf(
            "0" to "https://storage.googleapis.com/sign-model-bucket/images/angka/0.jpg",
            "1" to "https://storage.googleapis.com/sign-model-bucket/images/angka/1.jpg",
            "2" to "https://storage.googleapis.com/sign-model-bucket/images/angka/2.jpg",
            "3" to "https://storage.googleapis.com/sign-model-bucket/images/angka/3.jpg",
            "4" to "https://storage.googleapis.com/sign-model-bucket/images/angka/4.jpg",
            "5" to "https://storage.googleapis.com/sign-model-bucket/images/angka/5.jpg",
            "6" to "https://storage.googleapis.com/sign-model-bucket/images/angka/6.jpg",
            "7" to "https://storage.googleapis.com/sign-model-bucket/images/angka/7.jpg",
            "8" to "https://storage.googleapis.com/sign-model-bucket/images/angka/8.jpg",
            "9" to "https://storage.googleapis.com/sign-model-bucket/images/angka/9.jpg",
            "10" to "https://storage.googleapis.com/sign-model-bucket/images/angka/10.jpg"
        )

        // Cek apakah huruf ada di dalam map dan dapatkan URL-nya
        val imageUrl = angkaToImageUrl[angka]

        if (imageUrl != null) {
            // Jika URL ditemukan, buat intent untuk menampilkan gambar
            val intent = Intent(context, ImageDisplayActivity::class.java).apply {
                putExtra("image_url", imageUrl)
            }
            startActivity(intent)
        } else {
            // Jika huruf tidak ditemukan, bisa ditambahkan penanganan lain jika diperlukan
        }
    }

}