package com.example.combridge.kamera

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.fragment.app.Fragment
import com.example.combridge.R

class KameraFragment : Fragment(R.layout.fragment_kamera) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val btnKamera = view.findViewById<Button>(R.id.btnKamera)
        btnKamera.setOnClickListener {
            val intent = Intent(requireActivity(), CameraActivity::class.java)
            startActivity(intent)
        }

        val btnWhitepaper = view.findViewById<Button>(R.id.BangunKata)
        btnWhitepaper.setOnClickListener {
            val intent = Intent(requireActivity(), BangunKataActivity::class.java)
            startActivity(intent)
        }
    }
}
