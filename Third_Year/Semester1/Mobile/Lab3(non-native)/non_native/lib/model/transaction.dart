import 'type.dart';

class Transaction {
  int id;
  String title;
  Type type;
  String category;
  double amount;
  DateTime timestamp;

  static int currentId = 0;

  Transaction({
    required this.id,
    required this.title,
    required this.type,
    required this.category,
    required this.amount,
    required this.timestamp,
});
  
  factory Transaction.createTransaction(
      String title,
      Type type,
      String category,
      double amount,
      DateTime timestamp,
      ) {
    return Transaction(id: currentId++, title: title, type: type, category: category, amount: amount, timestamp: timestamp);
  }

  static Transaction newTransaction(
      String title,
      Type type,
      String category,
      double amount,
      DateTime timestamp,
      ) {
    return Transaction(id: currentId++, title: title, type: type, category: category, amount: amount, timestamp: timestamp);

  }
}
