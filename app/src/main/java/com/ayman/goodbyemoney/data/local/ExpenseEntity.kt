package com.ayman.goodbyemoney.data.local


import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "expenses")
data class ExpenseEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val title: String,
    val amount: String,
    val time: String,
    val categoryColor: Long,
    val group: String,
    val createdAt: Long = System.currentTimeMillis()
)