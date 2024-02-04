
import 'package:flutter/material.dart';
import '../../model/type.dart';
import '../../model/transaction.dart';

class UpdatePage extends StatefulWidget {
  final Function onEdit;
  final Transaction transaction;

  const UpdatePage({super.key, required this.onEdit, required this.transaction});

  @override
  _UpdatePageState createState() => _UpdatePageState(editFunction: onEdit, transaction: transaction);
}

class _UpdatePageState extends State<UpdatePage> {

  final GlobalKey<FormState> _formKey = GlobalKey<FormState>();
  final TextEditingController titleController = TextEditingController();
  final TextEditingController amountController = TextEditingController();
  Function editFunction;
  Transaction transaction;
  String selectedCategory = "Select a category";
  bool isChecked1 = false;
  bool isChecked2 = false;
  Type type = Type.expense;
  String category = "";
  bool errors = false;

  _UpdatePageState({required this.editFunction, required this.transaction});

  @override
  void initState() {
    // TODO: implement initState
    super.initState();
    titleController.text = transaction.title;
    amountController.text = transaction.amount.toString();
    selectedCategory = transaction.category;
    category = transaction.category;
    isChecked1 = transaction.type == Type.expense;
    isChecked2 = transaction.type == Type.income;
  }

  @override
  Widget build(BuildContext context) {

    return Scaffold(
      appBar: AppBar(
        title: const Text("Edit Transaction",
          //  style: TextStyle(color: Colors.black),
        ),
        backgroundColor: const Color(0xFFB57EDC),
      ),
      body: Container(
        height: MediaQuery.of(context).size.height,
        color: const Color(0xFFFFF8E1),
        child: Form(
          key: _formKey,
          child: SingleChildScrollView(
            padding: const EdgeInsets.all(16.0),
            child: Column(
              crossAxisAlignment: CrossAxisAlignment.stretch,
              children: <Widget>[
                const SizedBox(height: 30.0),
                TextFormField(
                  controller: titleController,

                  decoration: InputDecoration(
                    labelText: "Title",
                    errorText: errors && titleController.text.isEmpty ? "Title can't be empty" : null,
                    suffixIcon: errors && titleController.text.isEmpty ? const Icon(Icons.error, color: Colors.red) : null,
                  ),
                ),
                const SizedBox(height: 48.0),
                TextFormField(
                  controller: amountController,
                  decoration: InputDecoration(
                    labelText: "Amount",
                    errorText: errors &&
                        (amountController.text.isEmpty || double.tryParse(amountController.text) == null)
                        ? "Amount can't be empty and must be a number" : null,
                    suffixIcon: errors &&
                        (amountController.text.isEmpty || double.tryParse(amountController.text) == null)
                        ? const Icon(Icons.error, color: Colors.red) : null,
                  ),
                ),
                const SizedBox(height: 48.0),
                DropdownButton<String>(
                  value: transaction.category,
                  items: <String>[
                    "Select a category",
                    "Transport",
                    "Food",
                    "Entertaining",
                    "House",
                    "Health",
                    "Income"
                  ].map((String value) {
                    return DropdownMenuItem<String>(
                      value: value,
                      child: Text(value),
                    );
                  }).toList(),
                  onChanged: (value) {
                    setState(() {
                      selectedCategory = value ?? "Select a category";
                      category = value ?? "";
                    });
                  },
                  isExpanded: true,
                ),
                const SizedBox(height: 48.0),
                const Text(
                  "Type",
                  style: TextStyle(
                    fontSize: 24.0,
                    fontWeight: FontWeight.bold,
                  ),
                  textAlign: TextAlign.center,
                ),
                const SizedBox(height: 30.0),
                Row(
                  mainAxisAlignment: MainAxisAlignment.spaceAround,
                  children: [
                    Column(
                      children: [
                        const Text(
                          "Expense",
                          style: TextStyle(fontWeight: FontWeight.w600, fontSize: 20),
                        ),
                        Checkbox(
                          value: isChecked1,
                          onChanged: (value) {
                            setState(() {
                              isChecked1 = value ?? false;
                              if (isChecked1) {
                                isChecked2 = false;
                                type = Type.expense;
                              }
                            });
                          },
                        ),
                      ],
                    ),
                    Column(
                      children: [
                        const Text("Income", style: TextStyle(fontWeight: FontWeight.w600, fontSize: 20.0)),
                        Checkbox(
                          value: isChecked2,
                          onChanged: (value) {
                            setState(() {
                              isChecked2 = value ?? false;
                              if (isChecked2) {
                                isChecked1 = false;
                                type = Type.income;
                                category = "Income";
                              }
                            });
                          },
                        ),
                      ],
                    ),
                  ],
                ),
                const SizedBox(height: 32.0),
                ElevatedButton(
                    onPressed: () {
                      setState(() {
                        if (category.isEmpty || titleController.text.isEmpty || amountController.text.isEmpty || (isChecked2 && isChecked1)) {
                          errors = true;
                        }
                        else {
                          errors = false;
                          // TODO: save logic
                          //Transaction newTransaction = Transaction.createTransaction(titleController.text, type, category, double.parse(amountController.text), DateTime.now());
                          Transaction newTransaction = Transaction(id: transaction.id, title: titleController.text, type: type, category: category, amount: double.parse(amountController.text), timestamp: DateTime.now());
                          editFunction(newTransaction);
                          Navigator.pop(context);
                        }
                      });
                    },
                    style: ElevatedButton.styleFrom(
                      backgroundColor: const Color(0xFFB89EFB),

                    ),
                    child: const Text("Save", style: TextStyle(fontSize: 16.0))
                ),
                ElevatedButton(
                    onPressed: () {
                      Navigator.pop(context);
                    },
                    style: ElevatedButton.styleFrom(
                      backgroundColor: const Color(0xFFB89EFB),
                    ),
                    child: const Text("Cancel", style: TextStyle(fontSize: 16.0))
                ),
              ],
            ),
          ),
        ),
      ),


    );
  }

}