package com.example.combridge.beranda

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.combridge.R
import com.example.combridge.adapter.DetailKataAdapter

class DetailKataActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_kata)

        val rvDetailKata = findViewById<RecyclerView>(R.id.rv_detail_kata)
        rvDetailKata.layoutManager = GridLayoutManager(this, 2)
        rvDetailKata.adapter = DetailKataAdapter(getdetailKataList())

        findViewById<View>(R.id.btn_back).setOnClickListener {
            onBackPressed()
        }
    }

    private fun getdetailKataList(): List<String> {
        return listOf("Se", "la", "mat", "Pa", "gi")
    }
}