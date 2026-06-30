package com.example.financetracker.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.financetracker.R
import com.example.financetracker.data.entity.Expense
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class ExpenseAdapter(
    private var items: List<Expense> = emptyList(),
    private val onDelete: (Expense) -> Unit
) : RecyclerView.Adapter<ExpenseAdapter.ViewHolder>() {

    private val dateFormat = SimpleDateFormat("MMM d, yyyy", Locale.getDefault())

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvTitle: TextView = view.findViewById(R.id.tv_item_title)
        val tvSubtitle: TextView = view.findViewById(R.id.tv_item_subtitle)
        val tvAmount: TextView = view.findViewById(R.id.tv_item_amount)
    }

    fun submitList(newItems: List<Expense>) {
        items = newItems
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_expense, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.tvTitle.text = item.category
        holder.tvSubtitle.text = "${item.description} • ${dateFormat.format(Date(item.date))}"
        holder.tvAmount.text = "-$%.2f".format(item.amount)
        holder.itemView.setOnLongClickListener {
            onDelete(item)
            true
        }
    }

    override fun getItemCount(): Int = items.size
}
