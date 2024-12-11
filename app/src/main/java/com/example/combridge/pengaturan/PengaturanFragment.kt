package com.example.combridge.pengaturan

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.lifecycleScope
import com.example.combridge.auth.UserPreference
import com.example.combridge.auth.dataStore
import com.example.combridge.databinding.FragmentPengaturanBinding
import com.example.combridge.welcome.WelcomeActivity
import kotlinx.coroutines.launch

class PengaturanFragment : Fragment() {

    private lateinit var logoutButton: LinearLayout
    private lateinit var aboutButton: LinearLayout // Tambahkan referensi untuk tombol "About"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentPengaturanBinding.inflate(inflater, container, false)

        // Menemukan tombol Logout dan About dari layout
        logoutButton = binding.logoutButton
        aboutButton = binding.aboutButton // Inisialisasi tombol "About" menggunakan binding

        // Menambahkan listener untuk tombol Logout
        logoutButton.setOnClickListener {
            // Melakukan proses logout
            logout()
        }

        // Menambahkan listener untuk tombol About
        aboutButton.setOnClickListener {
            navigateToAboutPage()
        }

        return binding.root
    }

    private fun logout() {
        AlertDialog.Builder(requireContext()).apply {
            setTitle("Konfirmasi Logout")
            setMessage("Apakah Anda yakin ingin logout?")
            setPositiveButton("Ya") { _, _ ->
                performLogout()
            }
            setNegativeButton("Batal") { dialog, _ ->
                dialog.dismiss()
            }
            create()
            show()
        }
    }

    private fun performLogout() {
        val userPreference = UserPreference.getInstance(requireContext().dataStore)
        lifecycleScope.launch {
            userPreference.clearUserData() // Menghapus data pengguna
        }

        // Menavigasi ke halaman login setelah logout
        val intent = Intent(requireActivity(), WelcomeActivity::class.java)
        startActivity(intent)
        requireActivity().finish() // Menutup halaman Pengaturan agar tidak bisa kembali
    }

    private fun navigateToAboutPage() {
        // Intent untuk berpindah ke halaman About
        val intent = Intent(requireActivity(), AboutActivity::class.java)
        startActivity(intent)
    }
}
