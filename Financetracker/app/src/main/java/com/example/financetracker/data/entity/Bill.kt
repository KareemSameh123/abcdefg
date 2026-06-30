package com.example.financetracker.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "bills")
data class Bill(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val name: String,
    val amount: Double,
    val dueDate: Long,
    val frequency: String = "",
    val isRecurring: Boolean = false,
    val isPaid: Boolean = false
)
