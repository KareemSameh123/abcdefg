package com.example.financetracker.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.financetracker.R
import com.example.financetracker.data.entity.BillSplit
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class BillSplitAdapter(
    private var items: List<BillSplit> = emptyList()
) : RecyclerView.Adapter<BillSplitAdapter.ViewHolder>() {

    private val dateFormat = SimpleDateFormat("MMM d, yyyy", Locale.getDefault())

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvDescription: TextView = view.findViewById(R.id.tv_split_description)
        val tvDetails: TextView = view.findViewById(R.id.tv_split_details)
    }

    fun submitList(newItems: List<BillSplit>) {
        items = newItems
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_bill_split, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.tvDescription.text = item.description
        holder.tvDetails.text = "$%.2f total • ${dateFormat.format(Date(item.date))}".format(item.totalAmount)
    }

    override fun getItemCount(): Int = items.size
}
