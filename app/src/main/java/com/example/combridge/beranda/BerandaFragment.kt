package com.example.combridge.beranda

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.combridge.R
import com.google.android.material.bottomnavigation.BottomNavigationView

class BerandaFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_beranda, container, false)

        // Akses buttonHuruf dan tambahkan listener
        val buttonHuruf = view.findViewById<View>(R.id.iconHuruf)
        buttonHuruf.setOnClickListener {
            // Navigasi ke FragmentHuruf
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, FragmentHuruf())
                .addToBackStack(null)
                .commit()
        }

        // Akses buttonKata dan tambahkan listener
        val buttonKata = view.findViewById<View>(R.id.iconKata)
        buttonKata.setOnClickListener {
            // Navigasi ke FragmentHuruf
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, KataFragment())
                .addToBackStack(null)
                .commit()

        }

        // Akses buttonAngka dan tambahkan listener
        val buttonAngka = view.findViewById<View>(R.id.iconAngka)
        buttonAngka.setOnClickListener {
            // Navigasi ke FragmentHuruf
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, AngkaFragment())
                .addToBackStack(null)
                .commit()

        }

        return view
    }
}