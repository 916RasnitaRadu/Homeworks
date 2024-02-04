
import 'package:flutter/material.dart';

import '../model/transaction.dart';
import '../model/type.dart';
import '../ui/components/list_item.dart';
import '../ui/pages/add_page.dart';
import '../ui/pages/update_page.dart';

class TransactionViewModel extends StatefulWidget {
  const TransactionViewModel();

  @override
  _TransactionViewModelState createState() => _TransactionViewModelState();

  Function getAdd() {
    return _TransactionViewModelState().addTransaction;
  }
}

class _TransactionViewModelState extends State<TransactionViewModel> {
  List<Transaction> transactions = [];

  @override
  void initState() {
    super.initState();
    populateList();
  }

  void addTransaction(Transaction transaction) {
    setState(() {
      transactions.add(transaction);
    });

  }

  void deleteTransaction(int transactionId) {
    setState(() {
      transactions.removeWhere((transaction) => transactionId == transaction.id);
    });
  }

  void updateTransaction(Transaction updatedTransaction) {
    setState(() {
      final index = transactions.indexWhere((transaction) => transaction.id == updatedTransaction.id);
      if (index != -1) {
        transactions[index] = updatedTransaction;
      }
    });
  }

  Transaction getTransactionById(int id) {
    return transactions.firstWhere((transaction) => transaction.id == id, orElse: () {
      return Transaction(id:0,title: "",type: Type.income, category: "",amount: 0.0, timestamp:DateTime.now());
    });
  }

  void populateList() {
    transactions.addAll(List<Transaction>.of(
      [Transaction.createTransaction( "Uber", Type.expense,"Transport", 8.76,  DateTime.now()),
        Transaction.createTransaction("Salary", Type.income, "Income", 500.0, DateTime(2023, 7, 4)),
        Transaction.createTransaction("Groceries",  Type.expense,  "Food",  25.46,  DateTime(2023, 8, 1)),
        Transaction.createTransaction( "Some moneyyy",Type.income, "Income",  50.0,  DateTime(2023, 9, 29)),
        Transaction.createTransaction("Date",  Type.expense,  "Entertainment",  65.65,  DateTime(2023, 10, 14)),
        Transaction.createTransaction("Bani de la tati",Type.income,"Income", 400.0,DateTime(2023, 10, 15)),
        Transaction.createTransaction( "Train", Type.expense, "Transport", 2.5, DateTime(2023, 7, 20))
      ],

    ));
  }

  @override
  Widget build(BuildContext context) {
    return Expanded(
      child: ListView.builder(
        padding: const EdgeInsets.all(16.0),
        itemCount: transactions.length,
        itemBuilder: (context, index) {
          final transaction = transactions[index];

          if (index == 0) {
            return Column(
              children: [
                ElevatedButton(
                  onPressed: () {
                    Navigator.push(context,
                        MaterialPageRoute(builder: (_) => AddPage(addFunction: addTransaction)));
                  },
                  style: ElevatedButton.styleFrom(
                    backgroundColor: const Color(0xFFB89EFB),
                    padding: const EdgeInsets.all(20.0),
                    minimumSize: const Size(200.0, 60.0),
                  ),
                  child: const Text(
                    "+ Another One",
                    style: TextStyle(
                      color: Colors.black,
                      fontSize: 18.0,
                    ),
                  ),
                ),
                const SizedBox(height: 20.0),
                TransactionItem(
                  transaction: transaction,
                  onDelete: () => deleteTransaction(transaction.id),
                  onEdit: () {
                    final transactionId = transaction.id;
                    Navigator.push(
                      context,
                      MaterialPageRoute(builder: (_) => UpdatePage(onEdit: updateTransaction,transaction: transaction)),
                    );
                  },
                ),


              ],
            );
          }

          return Column(
            children: [
              TransactionItem(
                transaction: transaction,
                onDelete: () => deleteTransaction(transaction.id),
                onEdit: () {
                  final transactionId = transaction.id;
                  Navigator.push(
                    context,
                    MaterialPageRoute(builder: (_) => UpdatePage(onEdit: updateTransaction,transaction: transaction)),
                  );
                },
              ),
              const SizedBox(height: 16.0),
            ],
          );
        },
      ),
    );
  }
}

