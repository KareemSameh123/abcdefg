package com.example.financetracker.ui

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.financetracker.R
import com.example.financetracker.data.entity.Income
import com.example.financetracker.viewmodel.IncomeViewModel

class AddIncomeActivity : AppCompatActivity() {

    private val viewModel: IncomeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_income)

        val etAmount = findViewById<EditText>(R.id.et_amount)
        val etSource = findViewById<EditText>(R.id.et_source)
        val etDescription = findViewById<EditText>(R.id.et_description)
        val btnSave = findViewById<Button>(R.id.btn_save)

        btnSave.setOnClickListener {
            val amount = etAmount.text.toString().toDoubleOrNull() ?: 0.0
            val source = etSource.text.toString()
            val desc = etDescription.text.toString()
            if (amount > 0) {
                viewModel.insert(Income(amount = amount, source = source, description = desc))
                finish()
            } else {
                etAmount.error = "Enter a valid amount"
            }
        }
    }
}
