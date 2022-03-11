package com.example.quotegenerator.fragment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.quotegenerator.R
import com.example.quotegenerator.model.Quote

class Adapter : ListAdapter<Quote, Adapter.ViewHolder>(DiffCallback()) {

    var data = ArrayList<Quote>()

    override fun getItemCount(): Int { return data.size }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Adapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: Adapter.ViewHolder, position: Int) {
        val item = data[position]
        holder.quote.text = item.q
        holder.author.text = item.a
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val quote: TextView = view.findViewById(R.id.quote)
        val author: TextView = view.findViewById(R.id.author)
    }

    class DiffCallback : DiffUtil.ItemCallback<Quote>() {
        override fun areItemsTheSame(oldItem: Quote, newItem: Quote): Boolean {
            return oldItem.q == newItem.q
        }

        override fun areContentsTheSame(oldItem: Quote, newItem: Quote): Boolean {
            return oldItem == newItem
        }
    }
}