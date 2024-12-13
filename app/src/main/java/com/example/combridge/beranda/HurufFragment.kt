package com.example.combridge.beranda

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.combridge.R
import com.example.combridge.adapter.HurufAdapter

class FragmentHuruf : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_huruf, container, false)
        val rvHuruf = view.findViewById<RecyclerView>(R.id.rv_huruf)
        rvHuruf.layoutManager = GridLayoutManager(context, 2)
        val adapter = HurufAdapter(getHurufList())
        rvHuruf.adapter = adapter

        adapter.setOnItemClickListener { huruf ->
            onHurufClick(huruf)
        }

        val btnClose = view.findViewById<ImageView>(R.id.btnClose)
        btnClose.setOnClickListener {
            activity?.onBackPressed()
        }

        return view
    }

    private fun getHurufList(): List<String> {
        return listOf("A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z")
    }

    private fun onHurufClick(huruf: String) {
        val hurufToImageUrl = mapOf(
            "A" to "https://storage.googleapis.com/sign-model-bucket/images/valid/A/body%20white%20(3).jpg",
            "B" to "https://storage.googleapis.com/sign-model-bucket/images/valid/B/body%20white%20(3).jpg",
            "C" to "https://storage.googleapis.com/sign-model-bucket/images/valid/C/body%20white%20(3).jpg",
            "D" to "https://storage.googleapis.com/sign-model-bucket/images/valid/D/body%20white%20(3).jpg",
            "E" to "https://storage.googleapis.com/sign-model-bucket/images/valid/E/body%20white%20(3).jpg",
            "F" to "https://storage.googleapis.com/sign-model-bucket/images/valid/F/body%20white%20(3).jpg",
            "G" to "https://storage.googleapis.com/sign-model-bucket/images/valid/G/body%20white%20(3).jpg",
            "H" to "https://storage.googleapis.com/sign-model-bucket/images/valid/H/body%20white%20(3).jpg",
            "I" to "https://storage.googleapis.com/sign-model-bucket/images/valid/I/body%20white%20(3).jpg",
            "J" to "https://storage.googleapis.com/sign-model-bucket/images/valid/J/body%20white%20(3).jpg",
            "K" to "https://storage.googleapis.com/sign-model-bucket/images/valid/K/body%20white%20(3).jpg",
            "L" to "https://storage.googleapis.com/sign-model-bucket/images/valid/L/body%20white%20(3).jpg",
            "M" to "https://storage.googleapis.com/sign-model-bucket/images/valid/M/body%20white%20(3).jpg",
            "N" to "https://storage.googleapis.com/sign-model-bucket/images/valid/N/body%20white%20(3).jpg",
            "O" to "https://storage.googleapis.com/sign-model-bucket/images/valid/O/body%20white%20(3).jpg",
            "P" to "https://storage.googleapis.com/sign-model-bucket/images/valid/P/body%20white%20(3).jpg",
            "Q" to "https://storage.googleapis.com/sign-model-bucket/images/valid/Q/body%20white%20(3).jpg",
            "R" to "https://storage.googleapis.com/sign-model-bucket/images/valid/R/body%20white%20(3).jpg",
            "S" to "https://storage.googleapis.com/sign-model-bucket/images/valid/S/body%20white%20(3).jpg",
            "T" to "https://storage.googleapis.com/sign-model-bucket/images/valid/T/body%20white%20(3).jpg",
            "U" to "https://storage.googleapis.com/sign-model-bucket/images/valid/U/body%20white%20(3).jpg",
            "V" to "https://storage.googleapis.com/sign-model-bucket/images/valid/V/body%20white%20(3).jpg",
            "W" to "https://storage.googleapis.com/sign-model-bucket/images/valid/W/body%20white%20(3).jpg",
            "X" to "https://storage.googleapis.com/sign-model-bucket/images/valid/X/body%20white%20(3).jpg",
            "Y" to "https://storage.googleapis.com/sign-model-bucket/images/valid/Y/body%20white%20(3).jpg",
            "Z" to "https://storage.googleapis.com/sign-model-bucket/images/valid/Z/body%20white%20(3).jpg"
        )

        val imageUrl = hurufToImageUrl[huruf]

        if (imageUrl != null) {
            val intent = Intent(context, ImageDisplayActivity::class.java).apply {
                putExtra("image_url", imageUrl)
            }
            startActivity(intent)
        } else {
        }
    }

}