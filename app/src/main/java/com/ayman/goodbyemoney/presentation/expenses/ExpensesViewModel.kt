package com.ayman.goodbyemoney.presentation.expenses

import com.ayman.goodbyemoney.data.local.ExpenseEntity

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.ayman.goodbyemoney.data.local.AppDatabase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class UiExpense(
    val id: Long,
    val title: String,
    val amount: String,
    val time: String,
    val categoryColor: Long,
    val group: String
)

data class ExpensesUiState(
    val expenses: List<UiExpense> = emptyList(),
    val totalWeek: String = "$ 1250.98"
)

class ExpensesViewModel(application: Application) : AndroidViewModel(application) {
    private val dao = AppDatabase.get(application).expenseDao()
    private val _uiState = MutableStateFlow(ExpensesUiState())
    val uiState: StateFlow<ExpensesUiState> = _uiState

    init {
        // Load from database and keep listening
        viewModelScope.launch {
            dao.getAll().collect { entities ->
                val uiList = entities.map {
                    UiExpense(it.id, it.title, it.amount, it.time, it.categoryColor, it.group)
                }
                _uiState.update { it.copy(expenses = uiList) }
            }
        }
        // Insert fake data once if database is empty
        viewModelScope.launch {
            if (_uiState.value.expenses.isEmpty()) {
                dao.insert(ExpenseEntity(title = "Pizza", amount = "MKD 660", time = "16:24", categoryColor = 0xFFFF3B30, group = "Today"))
                dao.insert(ExpenseEntity(title = "Mouse pad", amount = "USD35", time = "13:37", categoryColor = 0xFF0A84FF, group = "Today"))
                dao.insert(ExpenseEntity(title = "Groceries", amount = "MKD1200", time = "16:24", categoryColor = 0xFF0A84FF, group = "Today"))
                dao.insert(ExpenseEntity(title = "Electricity", amount = "MKD2450", time = "20:41", categoryColor = 0xFFFF3B30, group = "Today"))
                dao.insert(ExpenseEntity(title = "Milk + Eggs", amount = "MKD 660", time = "16:24", categoryColor = 0xFF0A84FF, group = "Yesterday"))
            }
        }
    }

    fun addRandomExpense() {
        viewModelScope.launch {
            dao.insert(
                ExpenseEntity(
                    title = "Coffee",
                    amount = "USD 4",
                    time = "now",
                    categoryColor = 0xFFFF3B30,
                    group = "Today"
                )
            )
        }
    }
}