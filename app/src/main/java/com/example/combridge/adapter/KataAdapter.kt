package com.example.combridge.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.combridge.R

class KataAdapter(
    private val kataList: List<String>
) : RecyclerView.Adapter<KataAdapter.KataViewHolder>() {

    private var listener: ((String) -> Unit)? = null

    fun setOnItemClickListener(listener: (String) -> Unit) {
        this.listener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): KataViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_kata, parent, false)
        return KataViewHolder(view)
    }

    override fun onBindViewHolder(holder: KataViewHolder, position: Int) {
        val kata = kataList[position]
        holder.bind(kata, listener)
    }

    override fun getItemCount(): Int = kataList.size

    class KataViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvKata: TextView = itemView.findViewById(R.id.tv_detail_kata)

        fun bind(kata: String, listener: ((String) -> Unit)?) {
            tvKata.text = kata
            itemView.setOnClickListener {
                listener?.invoke(kata)
            }
        }
    }
}
