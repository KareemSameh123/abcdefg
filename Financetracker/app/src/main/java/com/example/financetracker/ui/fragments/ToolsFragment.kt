package com.example.financetracker.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.fragment.app.Fragment
import com.example.financetracker.R
import com.example.financetracker.ui.BillSplitActivity
import com.example.financetracker.ui.BillsActivity

class ToolsFragment : Fragment(R.layout.fragment_tools) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<Button>(R.id.btn_bills).setOnClickListener {
            startActivity(Intent(requireContext(), BillsActivity::class.java))
        }
        view.findViewById<Button>(R.id.btn_split).setOnClickListener {
            startActivity(Intent(requireContext(), BillSplitActivity::class.java))
        }
    }
}
