import 'package:examflutter/domain/fitness.dart';
import 'package:examflutter/utils/utilities.dart';
import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:examflutter/service/service.dart';
import 'package:fluttertoast/fluttertoast.dart';

import '../persistence/Repository.dart';

class AddFitness extends StatefulWidget {
  const AddFitness({super.key});

  @override
  State<AddFitness> createState() => _AddFitnessState();
}

class _AddFitnessState extends State<AddFitness> {
  final _formKey = GlobalKey<FormState>();

  final FitnessService _itemService = FitnessService();
  final Repository repository = Repository();
  String _date = "";
  String _type = "";
  String _category = "";
  String _description = "";
  double _duration = 1;
  double _calories = 0;
  bool _isLoading = false;

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text("Add Item"),
      ),
      body: SingleChildScrollView(
        child: Form(
          key: _formKey,
          child: Container(
            child: Column(
              children: [
                TextFormField(
                  decoration: const InputDecoration(
                    labelText: "Date",
                  ),
                  validator: (value) {
                    if (value == null || value.isEmpty) {
                      return "Please enter a date!";
                    }
                    return null;
                  },
                  onSaved: (value) {
                    _date = value!;
                  },
                ),
                TextFormField(
                  decoration: const InputDecoration(
                    labelText: "Type",
                  ),
                  validator: (value) {
                    if (value == null || value.isEmpty) {
                      return "Please enter a type!";
                    }
                    return null;
                  },
                  onSaved: (value) {
                    _type = value!;
                  },
                ),
                TextFormField(
                  decoration: const InputDecoration(
                    labelText: "Duration",
                  ),
                  validator: (value) {
                    if (value == null || value.isEmpty) {
                      return "Please enter an duration!";
                    }
                    return null;
                  },
                  onSaved: (value) {
                    _duration = double.parse(value!);
                  },
                ),
                TextFormField(
                  decoration: const InputDecoration(
                    labelText: "Category",
                  ),
                  validator: (value) {
                    if (value == null || value.isEmpty) {
                      return "Please enter the category!";
                    }
                    return null;
                  },
                  onSaved: (value) {
                    _category = value!;
                  },
                ),
                TextFormField(
                  decoration: const InputDecoration(
                    labelText: "Calories",
                  ),
                  validator: (value) {
                    if (value == null || value.isEmpty) {
                      return "Please enter the calories!";
                    }
                    return null;
                  },
                  onSaved: (value) {
                    _calories = double.parse(value!);
                  },
                ),
                TextFormField(
                  decoration: const InputDecoration(
                    labelText: "Description",
                  ),
                  validator: (value) {
                    if (value == null || value.isEmpty) {
                      return "Please enter a description!";
                    }
                    return null;
                  },
                  onSaved: (value) {
                    _description = value!;
                  },
                ),
                ElevatedButton(onPressed: () async {
                  if (!_formKey.currentState!.validate()) {
                    return;
                  }
                  _formKey.currentState!.save();
                  setState(() {
                    _isLoading = true;
                  });
                  try {
                    var result = await _itemService.addFitness(Fitness(
                        date: _date,
                        type: _type,
                        duration: _duration,
                        calories: _calories,
                        category: _category,
                        description: _description
                    ));
                    repository.insert(result.toMap(), Utilities.principalTable);
                    setState(() {
                      _isLoading = false;
                    });
                    Navigator.pop(context);
                  } catch (e) {
                      setState(() {
                        _isLoading = false;
                      });
                      print(e);
                      Fluttertoast.showToast(msg: e.toString(), backgroundColor: Colors.red);
                  }
                },
                    child: _isLoading == false
                      ? const Text("Add Item")
                        : const CircularProgressIndicator()
                ),
              ],
            ),
          ),
        ),
      ),
    );
  }
}