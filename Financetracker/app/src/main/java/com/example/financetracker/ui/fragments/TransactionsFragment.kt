package com.example.financetracker.ui.fragments

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.financetracker.R
import com.example.financetracker.ui.adapters.ExpenseAdapter
import com.example.financetracker.ui.adapters.IncomeAdapter
import com.example.financetracker.viewmodel.ExpenseViewModel
import com.example.financetracker.viewmodel.IncomeViewModel

class TransactionsFragment : Fragment(R.layout.fragment_transactions) {

    private val expenseViewModel: ExpenseViewModel by viewModels()
    private val incomeViewModel: IncomeViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView = view.findViewById<RecyclerView>(R.id.recycler_transactions)
        val btnExpenses = view.findViewById<Button>(R.id.btn_tab_expenses)
        val btnIncome = view.findViewById<Button>(R.id.btn_tab_income)

        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        val expenseAdapter = ExpenseAdapter { expense -> expenseViewModel.delete(expense) }
        val incomeAdapter = IncomeAdapter { income -> incomeViewModel.delete(income) }

        fun showExpenses() {
            recyclerView.adapter = expenseAdapter
        }

        fun showIncome() {
            recyclerView.adapter = incomeAdapter
        }

        expenseViewModel.allExpenses.observe(viewLifecycleOwner) { expenseAdapter.submitList(it) }
        incomeViewModel.allIncomes.observe(viewLifecycleOwner) { incomeAdapter.submitList(it) }

        btnExpenses.setOnClickListener { showExpenses() }
        btnIncome.setOnClickListener { showIncome() }

        showExpenses()
    }
}
