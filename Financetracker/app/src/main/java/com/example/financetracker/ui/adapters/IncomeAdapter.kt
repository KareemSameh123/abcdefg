package com.example.financetracker.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.financetracker.R
import com.example.financetracker.data.entity.Income
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class IncomeAdapter(
    private var items: List<Income> = emptyList(),
    private val onDelete: (Income) -> Unit
) : RecyclerView.Adapter<IncomeAdapter.ViewHolder>() {

    private val dateFormat = SimpleDateFormat("MMM d, yyyy", Locale.getDefault())

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvTitle: TextView = view.findViewById(R.id.tv_item_title)
        val tvSubtitle: TextView = view.findViewById(R.id.tv_item_subtitle)
        val tvAmount: TextView = view.findViewById(R.id.tv_item_amount)
    }

    fun submitList(newItems: List<Income>) {
        items = newItems
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_income, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.tvTitle.text = item.source
        holder.tvSubtitle.text = "${item.description} • ${dateFormat.format(Date(item.date))}"
        holder.tvAmount.text = "+$%.2f".format(item.amount)
        holder.itemView.setOnLongClickListener {
            onDelete(item)
            true
        }
    }

    override fun getItemCount(): Int = items.size
}
