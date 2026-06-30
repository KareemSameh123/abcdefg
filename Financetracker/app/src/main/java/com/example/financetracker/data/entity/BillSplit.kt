package com.example.financetracker.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "bill_splits")
data class BillSplit(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val totalAmount: Double,
    val description: String,
    val date: Long = System.currentTimeMillis()
)
