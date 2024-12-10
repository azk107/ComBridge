package com.example.combridge.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.combridge.R

class HurufAdapter(
    private val hurufList: List<String>
) : RecyclerView.Adapter<HurufAdapter.HurufViewHolder>() {

    private var listener: ((String) -> Unit)? = null

    fun setOnItemClickListener(listener: (String) -> Unit) {
        this.listener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HurufViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_huruf, parent, false)
        return HurufViewHolder(view, listener)  // Pass listener to ViewHolder
    }

    override fun onBindViewHolder(holder: HurufViewHolder, position: Int) {
        val huruf = hurufList[position]
        holder.bind(huruf)
    }

    override fun getItemCount(): Int = hurufList.size

    class HurufViewHolder(itemView: View, private val listener: ((String) -> Unit)?) : RecyclerView.ViewHolder(itemView) {
        private val tvHuruf: TextView = itemView.findViewById(R.id.tv_huruf)

        fun bind(huruf: String) {
            tvHuruf.text = huruf
            itemView.setOnClickListener {
                listener?.invoke(huruf)
            }
        }
    }
}

