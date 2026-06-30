package com.example.financetracker.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.financetracker.R
import com.example.financetracker.data.entity.BillSplitPerson
import com.example.financetracker.ui.adapters.BillSplitAdapter
import com.example.financetracker.viewmodel.BillSplitViewModel

class BillSplitActivity : AppCompatActivity() {

    private val viewModel: BillSplitViewModel by viewModels()
    private val personRows = mutableListOf<Pair<EditText, EditText>>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bill_split)

        val etTotalAmount = findViewById<EditText>(R.id.et_total_amount)
        val etDescription = findViewById<EditText>(R.id.et_description)
        val llPersons = findViewById<LinearLayout>(R.id.ll_persons)
        val btnAddPerson = findViewById<Button>(R.id.btn_add_person)
        val btnEqualSplit = findViewById<Button>(R.id.btn_equal_split)
        val btnSaveSplit = findViewById<Button>(R.id.btn_save_split)
        val recyclerHistory = findViewById<RecyclerView>(R.id.recycler_split_history)

        recyclerHistory.layoutManager = LinearLayoutManager(this)
        val historyAdapter = BillSplitAdapter()
        recyclerHistory.adapter = historyAdapter
        viewModel.allSplits.observe(this) { historyAdapter.submitList(it) }

        fun addPersonField() {
            val row = LayoutInflater.from(this).inflate(R.layout.item_person_share, llPersons, false)
            val nameEdit = row.findViewById<EditText>(R.id.et_person_name)
            val shareEdit = row.findViewById<EditText>(R.id.et_share)
            personRows.add(nameEdit to shareEdit)
            llPersons.addView(row)
        }

        addPersonField()
        addPersonField()

        btnAddPerson.setOnClickListener { addPersonField() }

        btnEqualSplit.setOnClickListener {
            val total = etTotalAmount.text.toString().toDoubleOrNull() ?: 0.0
            val count = personRows.size
            if (total > 0 && count > 0) {
                val share = total / count
                personRows.forEach { (_, shareEdit) -> shareEdit.setText("%.2f".format(share)) }
            }
        }

        btnSaveSplit.setOnClickListener {
            val total = etTotalAmount.text.toString().toDoubleOrNull() ?: 0.0
            val desc = etDescription.text.toString()
            val persons = personRows.mapIndexedNotNull { index, (nameEdit, shareEdit) ->
                val name = nameEdit.text.toString().ifBlank { "Person ${index + 1}" }
                val share = shareEdit.text.toString().toDoubleOrNull() ?: 0.0
                if (share > 0) BillSplitPerson(billSplitId = 0, personName = name, shareAmount = share) else null
            }
            if (total > 0 && persons.isNotEmpty()) {
                viewModel.saveBillSplit(total, desc.ifBlank { "Bill Split" }, persons)
                finish()
            }
        }
    }
}
