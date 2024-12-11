package com.example.combridge.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.combridge.R

class AngkaAdapter(
    private val angkaList: List<String>
) : RecyclerView.Adapter<AngkaAdapter.AngkaViewHolder>() {

    private var listener: ((String) -> Unit)? = null

    fun setOnItemClickListener(listener: (String) -> Unit) {
        this.listener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AngkaViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_angka, parent, false)
        return AngkaViewHolder(view)
    }

    override fun onBindViewHolder(holder: AngkaViewHolder, position: Int) {
        val angka = angkaList[position]
        holder.bind(angka, listener)
    }

    override fun getItemCount(): Int = angkaList.size

    class AngkaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvAngka: TextView = itemView.findViewById(R.id.tv_angka)

        fun bind(angka: String, listener: ((String) -> Unit)?) {
            tvAngka.text = angka
            itemView.setOnClickListener {
                listener?.invoke(angka)
            }
        }
    }
}