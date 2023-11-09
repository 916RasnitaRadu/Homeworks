package com.example.expensewizard.data

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.expensewizard.model.Transaction
import com.example.expensewizard.model.Type
import java.util.Date

class TransactionViewModel : ViewModel() {
    var transactions = mutableStateOf(listOf<Transaction>())

    init {
        populateList()
    }

    fun addTransaction(transaction: Transaction) {
        val updatedList = transactions.value + transaction
        transactions.value = updatedList
    }

    fun deleteTransaction(transactionId: Int) {
        val updatedList = transactions.value.filterNot { it.id == transactionId }
        transactions.value = updatedList
    }

    fun updateTransaction(updatedTransaction: Transaction) {
        val updatedList = transactions.value.map { transaction ->
            if (transaction.id == updatedTransaction.id) {
                updatedTransaction
            }
            else {
                transaction
            }
         }

        transactions.value = updatedList
    }

    fun getTransactionById(id : Int) : Transaction? {
        for (t in transactions.value) {
            if (t.id == id) {
                return t
            }
        }
        return Transaction("",Type.INCOME, "",0.0,Date())
    }

    private fun populateList() {
        val initialTransactions = listOf<Transaction>(
            Transaction("Uber", Type.EXPENSE,"Transport",8.76, Date()),
            Transaction("Salary", Type.INCOME,"Income",500.0, Date(2023,7,4)),
            Transaction("Groceries", Type.EXPENSE,"Food",25.46, Date(2023,8,1)),
            Transaction("Some moneyyy", Type.INCOME,"Income",50.0, Date(2023,9,29)),
            Transaction("Date", Type.EXPENSE,"Entertainment",65.65, Date(2023,10,14)),
            Transaction("Bani de la tati", Type.INCOME,"Income",400.0, Date(2023,10,15)),
            Transaction("Train", Type.EXPENSE,"Transport",2.5, Date(2023,7,20)),

        )
        transactions.value = initialTransactions

    }
}