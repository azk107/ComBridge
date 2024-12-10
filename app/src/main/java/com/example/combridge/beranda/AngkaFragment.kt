package com.example.combridge.beranda

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
        rvAngka.adapter = AngkaAdapter(getAngkaList())

        return view
    }

    private fun getAngkaList(): List<String> {
        return listOf("0", "1", "2", "3", "4", "5", "6", "7", "8", "9")
    }
}