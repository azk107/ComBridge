package com.example.combridge.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.combridge.R

class AngkaAdapter(private val angkaList: List<String>) : RecyclerView.Adapter<AngkaAdapter.AngkaViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AngkaViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_angka, parent, false)
        return AngkaViewHolder(view)
    }

    override fun onBindViewHolder(holder: AngkaViewHolder, position: Int) {
        val angka = angkaList[position]
        holder.bind(angka)
    }

    override fun getItemCount(): Int = angkaList.size

    class AngkaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvAngka: TextView = itemView.findViewById(R.id.tv_angka)
        private val tvKeterangan: TextView = itemView.findViewById(R.id.tv_keterangan)

        fun bind(angka: String) {
            tvAngka.text = angka
            tvKeterangan.text = "Angka $angka"
        }
    }
}