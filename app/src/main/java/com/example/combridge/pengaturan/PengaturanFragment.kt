package com.example.combridge.pengaturan

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.lifecycle.lifecycleScope
import com.example.combridge.R
import com.example.combridge.autentikasi.LoginActivity
import com.example.combridge.auth.UserPreference
import com.example.combridge.auth.dataStore
import com.example.combridge.databinding.FragmentPengaturanBinding
import com.example.combridge.welcome.WelcomeActivity
import kotlinx.coroutines.launch

class PengaturanFragment : Fragment() {

    private lateinit var logoutButton: LinearLayout
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentPengaturanBinding.inflate(inflater, container, false)

        // Menemukan tombol logout dari layout
        logoutButton = binding.logoutButton

        // Menambahkan listener untuk tombol Logout
        logoutButton.setOnClickListener {
            // Melakukan proses logout
            logout()
        }

        return binding.root
    }

    private fun logout() {
        // Menghapus data pengguna yang disimpan, misalnya SharedPreferences atau UserPreference
        val userPreference = UserPreference.getInstance(requireContext().dataStore)
        lifecycleScope.launch {
            // Menyimpan nilai login sebagai false atau menghapus data lain yang terkait
            userPreference.clearUserData() // Sesuaikan dengan metode yang Anda gunakan untuk clear data
        }

        // Menavigasi ke halaman login setelah logout
        val intent = Intent(requireActivity(), WelcomeActivity::class.java)
        startActivity(intent)
        requireActivity().finish() // Menutup halaman Pengaturan agar tidak bisa kembali
    }

}
