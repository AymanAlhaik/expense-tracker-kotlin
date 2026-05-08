package com.ayman.goodbyemoney.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ayman.goodbyemoney.presentation.expenses.ExpensesViewModel
import com.ayman.goodbyemoney.presentation.expenses.UiExpense
import com.ayman.goodbyemoney.ui.theme.*

@Composable
fun ExpensesScreen(viewModel: ExpensesViewModel) {
    val uiState by viewModel.uiState.collectAsState()
    val grouped = uiState.expenses.groupBy { it.group }

    Scaffold(
        containerColor = Background,
        floatingActionButton = {
            FloatingActionButton(
                onClick = { viewModel.addRandomExpense() },
                containerColor = BlueAction
            ) {
                Text("+", color = TextPrimary, fontSize = 24.sp)
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 16.dp)
        ) {
            Spacer(Modifier.height(8.dp))
            Text("Expenses", color = TextPrimary, style = MaterialTheme.typography.displayLarge.copy(fontSize = 32.sp))
            Spacer(Modifier.height(12.dp))
            Text("⌕ Search", color = TextTertiary, fontSize = 16.sp)
            Spacer(Modifier.height(20.dp))

            Card(
                colors = CardDefaults.cardColors(containerColor = Surface),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(Modifier.padding(16.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text("Total for:", color = TextSecondary, fontSize = 14.sp)
                        Spacer(Modifier.width(8.dp))
                        Surface(color = SurfaceVariant, shape = RoundedCornerShape(6.dp)) {
                            Text("this week", color = TextPrimary, modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp), fontSize = 14.sp)
                        }
                    }
                    Spacer(Modifier.height(10.dp))
                    Text(uiState.totalWeek, color = TextPrimary, fontSize = 34.sp, fontWeight = FontWeight.Bold)
                }
            }

            Spacer(Modifier.height(24.dp))

            LazyColumn {
                grouped.forEach { (groupName, items) ->
                    item {
                        Text(groupName, color = TextSecondary, fontSize = 14.sp, modifier = Modifier.padding(vertical = 8.dp))
                    }
                    items(items, key = { it.id }) { expense ->
                        ExpenseRow(expense)
                        HorizontalDivider(color = Divider, thickness = 0.5.dp)
                    }
                }
            }
        }
    }
}

@Composable
private fun ExpenseRow(expense: UiExpense) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(vertical = 12.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column {
            Text(expense.title, color = TextPrimary, fontSize = 17.sp)
            Spacer(Modifier.height(4.dp))
            Box(
                modifier = Modifier
                    .width(32.dp)
                    .height(4.dp)
                    .background(androidx.compose.ui.graphics.Color(expense.categoryColor), RoundedCornerShape(2.dp))
            )
        }
        Column(horizontalAlignment = Alignment.End) {
            Text(expense.amount, color = TextPrimary, fontSize = 16.sp, fontWeight = FontWeight.Medium)
            Text(expense.time, color = TextTertiary, fontSize = 13.sp)
        }
    }
}