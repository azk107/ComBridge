package com.example.combridge.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.combridge.R

class DetailKataAdapter(private val detailKataList: List<String>) : RecyclerView.Adapter<DetailKataAdapter.DetailKataViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetailKataViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_detail_kata, parent, false)
        return DetailKataViewHolder(view)
    }

    override fun onBindViewHolder(holder: DetailKataViewHolder, position: Int) {
        val detailKata = detailKataList[position]
        holder.bind(detailKata)
    }

    override fun getItemCount(): Int = detailKataList.size

    class DetailKataViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvDetailKata: TextView = itemView.findViewById(R.id.tv_detail_kata)
        private val tvKeterangan: TextView = itemView.findViewById(R.id.tv_keterangan)

        fun bind(detail_kata: String) {
            tvDetailKata.text = detail_kata
            tvKeterangan.text = "Huruf $detail_kata"
        }
    }
}