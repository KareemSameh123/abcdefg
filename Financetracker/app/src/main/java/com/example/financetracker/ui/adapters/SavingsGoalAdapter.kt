package com.example.financetracker.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.financetracker.R
import com.example.financetracker.data.entity.SavingsGoal

class SavingsGoalAdapter(
    private var items: List<SavingsGoal> = emptyList(),
    private val onAddToGoal: (SavingsGoal) -> Unit
) : RecyclerView.Adapter<SavingsGoalAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvName: TextView = view.findViewById(R.id.tv_goal_name)
        val tvProgress: TextView = view.findViewById(R.id.tv_goal_progress)
        val progressBar: ProgressBar = view.findViewById(R.id.progress_goal)
        val btnAdd: Button = view.findViewById(R.id.btn_add_to_goal)
    }

    fun submitList(newItems: List<SavingsGoal>) {
        items = newItems
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_savings_goal, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.tvName.text = item.name
        holder.tvProgress.text = "$%.2f / $%.2f".format(item.currentAmount, item.targetAmount)
        val percent = if (item.targetAmount > 0) {
            ((item.currentAmount / item.targetAmount) * 100).toInt().coerceIn(0, 100)
        } else 0
        holder.progressBar.progress = percent
        holder.btnAdd.setOnClickListener { onAddToGoal(item) }
    }

    override fun getItemCount(): Int = items.size
}
