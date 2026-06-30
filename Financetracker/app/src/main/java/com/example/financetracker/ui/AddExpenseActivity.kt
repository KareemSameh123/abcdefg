package com.example.financetracker.ui

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import androidx.activity.viewModels
import com.example.financetracker.R
import com.example.financetracker.data.entity.Expense
import com.example.financetracker.viewmodel.ExpenseViewModel

class AddExpenseActivity : AppCompatActivity() {

    private val viewModel: ExpenseViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_expense)

        val etAmount = findViewById<EditText>(R.id.et_amount)
        val etDescription = findViewById<EditText>(R.id.et_description)
        val spCategory = findViewById<Spinner>(R.id.sp_category)
        val btnSave = findViewById<Button>(R.id.btn_save)

        val categories = arrayOf("Food", "Transport", "Bills", "Shopping", "Entertainment", "Health", "Other")
        spCategory.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, categories)

        btnSave.setOnClickListener {
            val amount = etAmount.text.toString().toDoubleOrNull() ?: 0.0
            val category = spCategory.selectedItem?.toString() ?: "Other"
            val desc = etDescription.text.toString()
            if (amount > 0) {
                viewModel.insert(Expense(amount = amount, category = category, description = desc))
                finish()
            } else {
                etAmount.error = "Enter a valid amount"
            }
        }
    }
}
