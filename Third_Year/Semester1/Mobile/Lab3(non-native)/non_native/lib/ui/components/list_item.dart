import 'package:flutter/material.dart';
import 'package:intl/intl.dart';
import 'package:non_native/model/transaction.dart';
import 'package:non_native/ui/components/delete_dialog.dart';
import '../../model/type.dart';


@immutable
class TransactionItem extends StatelessWidget {
  final Transaction transaction;
  final Function() onDelete;
  final void Function() onEdit;

  const TransactionItem({super.key,
    required this.transaction,
    required this.onDelete,
    required this.onEdit,
});

  @override
  Widget build(BuildContext context) {
    const bgColor = Color(0xFFFFF8E1);

    return Card(
      color: bgColor,
      elevation: 2.0,
      shape: RoundedRectangleBorder(
        borderRadius: BorderRadius.circular(4.0),
      ),
      child: SizedBox(
        width: 300.0,
        height: 120.0,
        child: Column(
          mainAxisAlignment: MainAxisAlignment.start,
          crossAxisAlignment: CrossAxisAlignment.center,
          children: <Widget>[
            Text(
              transaction.title,
              style: const TextStyle(
                fontSize: 18.0,
                fontWeight: FontWeight.w600,
              ),
            ),
            Row(
              mainAxisAlignment: MainAxisAlignment.spaceBetween,
              children: <Widget>[
                Column(
                  crossAxisAlignment: CrossAxisAlignment.start,
                  children: <Widget>[
                    Text(
                      transaction.category,
                      style: const TextStyle(
                        fontWeight: FontWeight.w600,
                        fontSize: 14.0,
                      ),
                    ),
                    Text(
                      formatDate(transaction.timestamp),
                      style: const TextStyle(
                        fontWeight: FontWeight.w600,
                        fontSize: 14.0,
                      ),
                    ),
                  ],
                ),
                Column(
                  crossAxisAlignment: CrossAxisAlignment.end,
                  children: <Widget>[
                    Text(
                      '${transaction.type == Type.income ? '+' : '-'}${transaction.amount.toStringAsFixed(2)}\$',
                      style: const TextStyle(
                        fontSize: 16.0,
                        fontWeight: FontWeight.w600,
                      ),
                    ),
                    Row(
                      mainAxisAlignment: MainAxisAlignment.end,
                      children: <Widget>[
                        IconButton(
                            onPressed: () {
                              showDialog(context: context,
                                  builder: (BuildContext context) {
                                      return DeleteDialog(onYes: onDelete);
                                  }
                              );
                            },
                            icon: const Icon(Icons.delete)),
                        IconButton(onPressed: onEdit,
                            icon: const Icon(Icons.edit)),
                      ],
                    ),
                  ],
                ),
              ],
            ),
          ],
        ),
      ),
    );
  }



  String formatDate(DateTime dateTime) {
    final formatter = DateFormat('MM/dd, HH:mm');
    return formatter.format(dateTime);
  }
}