package com.example.financetracker.data.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "bill_split_persons",
    foreignKeys = [ForeignKey(
        entity = BillSplit::class,
        parentColumns = ["id"],
        childColumns = ["billSplitId"],
        onDelete = ForeignKey.CASCADE
    )]
)
data class BillSplitPerson(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val billSplitId: Long,
    val personName: String,
    val shareAmount: Double
)
