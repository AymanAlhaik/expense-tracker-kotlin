package com.ayman.goodbyemoney.data.local


import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface ExpenseDao {
    @Query("SELECT * FROM expenses ORDER BY createdAt DESC")
    fun getAll(): Flow<List<ExpenseEntity>>

    @Insert
    suspend fun insert(expense: ExpenseEntity)
}