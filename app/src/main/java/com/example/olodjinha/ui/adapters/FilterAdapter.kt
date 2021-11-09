package com.example.olodjinha.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView
import com.example.olodjinha.R

class FilterAdapter(
    private @LayoutRes val view: Int,
    private val list: List<String>,
    private val onClickAction: (String, Int) -> Unit
) : RecyclerView.Adapter<FilterAdapter.SimpleViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SimpleViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(view, parent, false)
        return SimpleViewHolder(view)

    }

    override fun onBindViewHolder(holder: SimpleViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class SimpleViewHolder(val itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tv = itemView.findViewById<TextView>(R.id.tv)

        fun bind(text: String) {
            tv.text = text

            itemView.setOnClickListener {
                onClickAction.invoke(text, adapterPosition)
            }
        }
    }

}



