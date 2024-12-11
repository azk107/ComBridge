package com.example.combridge.kamera

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.fragment.app.Fragment
import com.example.combridge.R

/**
 * A simple [Fragment] subclass.
 * Use the [KameraFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class KameraFragment : Fragment(R.layout.fragment_kamera) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        // Tombol untuk membuka CameraActivity
        val btnKamera = view.findViewById<Button>(R.id.btnKamera)
        btnKamera.setOnClickListener {
            // Navigasi ke CameraActivity
            val intent = Intent(requireActivity(), CameraActivity::class.java)
            startActivity(intent)
        }
    }
}