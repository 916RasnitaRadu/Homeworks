package com.example.expensewizard.ui.components.list

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.gestures.ScrollableState
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.expensewizard.data.TransactionViewModel
import com.example.expensewizard.ui.components.listItem.TransactionItem

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun TransactionList(viewModel : TransactionViewModel, navController: NavController) {
    val transactions = viewModel.transactions.value


    LazyColumn (modifier = Modifier.height(400.dp)){
        items(transactions) {
                transaction -> TransactionItem(transaction = transaction, navController) { viewModel.deleteTransaction(transaction.id)}
                Spacer(modifier = Modifier.height(16.dp))

        }
    }
}
