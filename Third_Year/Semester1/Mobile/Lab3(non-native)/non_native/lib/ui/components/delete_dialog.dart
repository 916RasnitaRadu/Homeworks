
import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';

class DeleteDialog extends StatelessWidget {
  final VoidCallback onYes;


  const DeleteDialog({super.key, required this.onYes});

  @override
  Widget build(BuildContext context) {
    return AlertDialog(
      title: const Text("Delete Transaction"),
      content: const Text("Are you sure you want to delete this transaction?"),
      actions: [
        TextButton(
            onPressed: () {
              Navigator.of(context).pop();
        },
            child: const Text("Cancel")
        ),
        TextButton(
            onPressed: () {
              onYes();
              Navigator.of(context).pop();
            },
            child: const Text("Delete"),
        ),
      ],
    );
  }
}