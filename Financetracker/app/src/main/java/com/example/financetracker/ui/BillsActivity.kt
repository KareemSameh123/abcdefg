package com.example.financetracker.ui

import android.app.AlertDialog
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.LinearLayout
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.financetracker.R
import com.example.financetracker.data.entity.Bill
import com.example.financetracker.ui.adapters.BillAdapter
import com.example.financetracker.viewmodel.BillViewModel
import java.util.Calendar

class BillsActivity : AppCompatActivity() {

    private val viewModel: BillViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bills)

        val recyclerView = findViewById<RecyclerView>(R.id.recycler_bills)
        val btnAddBill = findViewById<Button>(R.id.btn_add_bill)
        recyclerView.layoutManager = LinearLayoutManager(this)

        val adapter = BillAdapter(
            onMarkPaid = { bill -> viewModel.markPaid(bill) },
            onDelete = { bill -> viewModel.delete(bill) }
        )
        recyclerView.adapter = adapter

        viewModel.allBills.observe(this) { adapter.submitList(it) }

        btnAddBill.setOnClickListener { showAddBillDialog() }
    }

    private fun showAddBillDialog() {
        val container = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(48, 24, 48, 24)
        }
        val nameInput = EditText(this).apply { hint = "Bill name" }
        val amountInput = EditText(this).apply {
            hint = "Amount"
            inputType = android.text.InputType.TYPE_CLASS_NUMBER or android.text.InputType.TYPE_NUMBER_FLAG_DECIMAL
        }
        val recurringCheck = CheckBox(this).apply { text = "Recurring monthly" }
        container.addView(nameInput)
        container.addView(amountInput)
        container.addView(recurringCheck)

        AlertDialog.Builder(this)
            .setTitle("Add Bill")
            .setView(container)
            .setPositiveButton("Save") { _, _ ->
                val name = nameInput.text.toString()
                val amount = amountInput.text.toString().toDoubleOrNull() ?: 0.0
                if (name.isNotBlank() && amount > 0) {
                    val dueDate = Calendar.getInstance().apply { add(Calendar.DAY_OF_MONTH, 30) }.timeInMillis
                    viewModel.insert(
                        Bill(
                            name = name,
                            amount = amount,
                            dueDate = dueDate,
                            frequency = if (recurringCheck.isChecked) "monthly" else "",
                            isRecurring = recurringCheck.isChecked
                        )
                    )
                }
            }
            .setNegativeButton("Cancel", null)
            .show()
    }
}
