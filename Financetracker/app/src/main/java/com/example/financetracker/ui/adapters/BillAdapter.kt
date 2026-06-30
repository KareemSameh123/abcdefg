package com.example.financetracker.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.financetracker.R
import com.example.financetracker.data.entity.Bill
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class BillAdapter(
    private var items: List<Bill> = emptyList(),
    private val onMarkPaid: (Bill) -> Unit,
    private val onDelete: (Bill) -> Unit
) : RecyclerView.Adapter<BillAdapter.ViewHolder>() {

    private val dateFormat = SimpleDateFormat("MMM d, yyyy", Locale.getDefault())

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvName: TextView = view.findViewById(R.id.tv_bill_name)
        val tvDetails: TextView = view.findViewById(R.id.tv_bill_details)
        val btnPaid: Button = view.findViewById(R.id.btn_mark_paid)
    }

    fun submitList(newItems: List<Bill>) {
        items = newItems
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_bill, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.tvName.text = item.name
        holder.tvDetails.text = "$%.2f due ${dateFormat.format(Date(item.dueDate))}".format(item.amount)
        holder.btnPaid.setOnClickListener { onMarkPaid(item) }
        holder.itemView.setOnLongClickListener {
            onDelete(item)
            true
        }
    }

    override fun getItemCount(): Int = items.size
}
