package com.example.financetracker.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.financetracker.R
import com.example.financetracker.ui.AddExpenseActivity
import com.example.financetracker.viewmodel.ExpenseViewModel
import com.example.financetracker.viewmodel.IncomeViewModel
import java.util.Calendar
import android.widget.Button
import android.widget.TextView

class HomeFragment : Fragment(R.layout.fragment_home) {

    private val expenseViewModel: ExpenseViewModel by viewModels()
    private val incomeViewModel: IncomeViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val tvTotalExpenses = view.findViewById<TextView>(R.id.tv_total_expenses)
        val tvTotalIncome = view.findViewById<TextView>(R.id.tv_total_income)
        val btnAddExpense = view.findViewById<Button>(R.id.btn_add_expense)

        val now = Calendar.getInstance()
        now.set(Calendar.DAY_OF_MONTH, 1)
        now.set(Calendar.HOUR_OF_DAY, 0)
        now.set(Calendar.MINUTE, 0)
        now.set(Calendar.SECOND, 0)
        now.set(Calendar.MILLISECOND, 0)
        val startOfMonth = now.timeInMillis
        now.add(Calendar.MONTH, 1)
        val endOfMonth = now.timeInMillis - 1

        expenseViewModel.allExpenses.observe(viewLifecycleOwner) { expenses ->
            val total = expenses.filter { it.date in startOfMonth..endOfMonth }.sumOf { it.amount }
            tvTotalExpenses.text = "Total Expenses: $%.2f".format(total)
        }

        incomeViewModel.allIncomes.observe(viewLifecycleOwner) { incomes ->
            val total = incomes.filter { it.date in startOfMonth..endOfMonth }.sumOf { it.amount }
            tvTotalIncome.text = "Total Income: $%.2f".format(total)
        }

        btnAddExpense.setOnClickListener {
            startActivity(Intent(requireContext(), AddExpenseActivity::class.java))
        }
    }
}
