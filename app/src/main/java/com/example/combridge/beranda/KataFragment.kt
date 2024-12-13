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
import com.example.combridge.adapter.KataAdapter

class KataFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_kata, container, false)
        val rvKata = view.findViewById<RecyclerView>(R.id.rv_kata)
        rvKata.layoutManager = GridLayoutManager(context, 2)
        val adapter = KataAdapter(getKataList())
        rvKata.adapter = adapter
        adapter.setOnItemClickListener { kata ->
            onKataClick(kata)
        }

        val btnClose = view.findViewById<ImageView>(R.id.btnClose)
        btnClose.setOnClickListener {
            activity?.onBackPressed()
        }


        return view
    }
    private fun getKataList(): List<String> {
        return listOf(
            "Berdoa", "Berhenti", "Berjalan", "Bermain", "Berpikir",
            "Bicara", "Duduk", "Makan", "Melihat", "Membaca",
            "Membuat", "Memeluk", "Menangis", "Mendorong", "Menggambar",
            "Menuangkan", "Minimum", "Saya", "Tidur", "Memukul"
        )
    }

    private fun onKataClick(kata: String) {
        val kataToImageUrl = mapOf(
            "Berdoa" to "https://storage.googleapis.com/sign-model-bucket/images/valid/Berdoa/berdoa-0567ce8b-3137-11ef-9ab5-9a8ad2a22026.jpg",
            "Berhenti" to "https://storage.googleapis.com/sign-model-bucket/images/valid/berhenti/berhenti-18cd8545-36ba-11ef-881e-8d5308685961.jpg",
            "Berjalan" to "https://storage.googleapis.com/sign-model-bucket/images/valid/Berjalan/berjalan-06001610-313c-11ef-b70a-9a8ad2a22026.jpg",
            "Bermain" to "https://storage.googleapis.com/sign-model-bucket/images/valid/Bermain/Bermain-04f2db0f-36b3-11ef-b90e-c85acfeebaf8.jpg",
            "Berpikir" to "https://storage.googleapis.com/sign-model-bucket/images/valid/Berpikir/berpikir-0b27bf61-3134-11ef-ab94-9a8ad2a22026.jpg",
            "Bicara" to "https://storage.googleapis.com/sign-model-bucket/images/valid/Bicara/bicara-10794f13-36b4-11ef-aeca-8d5308685961.jpg",
            "Duduk" to "https://storage.googleapis.com/sign-model-bucket/images/valid/Duduk/Duduk-04e6c29d-36ba-11ef-864f-c85acfeebaf8.jpg",
            "Makan" to "https://storage.googleapis.com/sign-model-bucket/images/valid/Makan/makan-1e10bea6-3132-11ef-9435-9a8ad2a22026.jpg",
            "Melihat" to "https://storage.googleapis.com/sign-model-bucket/images/valid/Melihat/melihat-2424d0c2-36b1-11ef-a768-8d5308685961.jpg",
            "Membaca" to "https://storage.googleapis.com/sign-model-bucket/images/valid/Membaca/Membaca-14ec6eae-36b6-11ef-b104-c85acfeebaf8.jpg",
            "Membuat" to "https://storage.googleapis.com/sign-model-bucket/images/valid/Membuat/membuat-0d77bbf2-36be-11ef-8546-8d5308685961.jpg",
            "Memeluk" to "https://storage.googleapis.com/sign-model-bucket/images/valid/Memeluk/Memeluk-007ca84b-36bb-11ef-a670-c85acfeebaf8.jpg",
            "Menangis" to "https://storage.googleapis.com/sign-model-bucket/images/valid/Menangis/Menangis-03042696-36b5-11ef-b6e0-c85acfeebaf8.jpg",
            "Mendorong" to "https://storage.googleapis.com/sign-model-bucket/images/valid/Mendorong/mendorong-07cebbc1-36bb-11ef-bb08-8d5308685961.jpg",
            "Menggambar" to "https://storage.googleapis.com/sign-model-bucket/images/valid/Menggambar/menggambar-0e30379c-36b6-11ef-9d60-8d5308685961.jpg",
            "Menuangkan" to "https://storage.googleapis.com/sign-model-bucket/images/valid/Menuangkan/menuangkan-49111b25-36bc-11ef-8921-8d5308685961.jpg",
            "Minimum" to "https://storage.googleapis.com/sign-model-bucket/images/valid/Minum/minum-05cc17ea-36b7-11ef-b309-8d5308685961.jpg",
            "Saya" to "https://storage.googleapis.com/sign-model-bucket/images/valid/Saya/saya-09c11911-3138-11ef-812d-9a8ad2a22026.jpg",
            "Tidur" to "https://storage.googleapis.com/sign-model-bucket/images/valid/Tidur/tidur-3750c46b-312f-11ef-acf6-9a8ad2a22026.jpg",
            "Memukul" to "https://storage.googleapis.com/sign-model-bucket/images/valid/Memukul/memukul-0bba3d9c-36bd-11ef-86ae-c85acfeebaf8.jpg"
        )

        val imageUrl = kataToImageUrl[kata]

        if (imageUrl != null) {
            val intent = Intent(context, ImageDisplayActivity::class.java).apply {
                putExtra("image_url", imageUrl)
            }
            startActivity(intent)
        } else {
        }
    }
}