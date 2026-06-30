package com.example.financetracker.ui.fragments

import android.app.AlertDialog
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.financetracker.R
import com.example.financetracker.data.entity.SavingsGoal
import com.example.financetracker.ui.adapters.SavingsGoalAdapter
import com.example.financetracker.viewmodel.SavingsGoalViewModel

class SavingsFragment : Fragment(R.layout.fragment_savings) {

    private val viewModel: SavingsGoalViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView = view.findViewById<RecyclerView>(R.id.recycler_savings)
        val btnAddGoal = view.findViewById<Button>(R.id.btn_add_goal)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        val adapter = SavingsGoalAdapter { goal -> showAddToGoalDialog(goal) }
        recyclerView.adapter = adapter

        viewModel.allGoals.observe(viewLifecycleOwner) { adapter.submitList(it) }

        btnAddGoal.setOnClickListener { showNewGoalDialog() }
    }

    private fun showNewGoalDialog() {
        val container = LinearLayout(requireContext()).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(48, 24, 48, 24)
        }
        val nameInput = EditText(requireContext()).apply { hint = "Goal name" }
        val targetInput = EditText(requireContext()).apply {
            hint = "Target amount"
            inputType = android.text.InputType.TYPE_CLASS_NUMBER or android.text.InputType.TYPE_NUMBER_FLAG_DECIMAL
        }
        container.addView(nameInput)
        container.addView(targetInput)

        AlertDialog.Builder(requireContext())
            .setTitle("New Savings Goal")
            .setView(container)
            .setPositiveButton("Create") { _, _ ->
                val name = nameInput.text.toString()
                val target = targetInput.text.toString().toDoubleOrNull() ?: 0.0
                if (name.isNotBlank() && target > 0) {
                    viewModel.insert(SavingsGoal(name = name, targetAmount = target))
                }
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    private fun showAddToGoalDialog(goal: SavingsGoal) {
        val amountInput = EditText(requireContext()).apply {
            hint = "Amount to add"
            inputType = android.text.InputType.TYPE_CLASS_NUMBER or android.text.InputType.TYPE_NUMBER_FLAG_DECIMAL
            setPadding(48, 24, 48, 24)
        }

        AlertDialog.Builder(requireContext())
            .setTitle("Add to ${goal.name}")
            .setView(amountInput)
            .setPositiveButton("Add") { _, _ ->
                val amount = amountInput.text.toString().toDoubleOrNull() ?: 0.0
                if (amount > 0) {
                    viewModel.addToGoal(goal, amount)
                }
            }
            .setNegativeButton("Cancel", null)
            .show()
    }
}
