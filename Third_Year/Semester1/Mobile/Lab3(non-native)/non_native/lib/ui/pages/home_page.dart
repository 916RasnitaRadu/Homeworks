
import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:non_native/ui/pages/add_page.dart';
import '../../data/transaction_view_model.dart';

class HomePage extends StatelessWidget {
  final Color bgColor = const Color(0xFFB57EDC);
  final Color bgColorCards = const Color(0xFFFFF8E1);
  final TransactionViewModel transactionViewModel;

  HomePage(this.transactionViewModel, {super.key});


  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: Container(
        color: bgColor,
        child: Padding(
          padding: const EdgeInsets.all(20.0),
          child: Column(
            mainAxisAlignment: MainAxisAlignment.start,
            children: <Widget>[
              Container(
                width: double.infinity,
                height: 100.0,
                decoration: BoxDecoration(
                  color: bgColorCards,
                  borderRadius: BorderRadius.circular(8.0),
                  boxShadow: [
                    BoxShadow(
                      color: Colors.black.withOpacity(0.2),
                      spreadRadius: 8,
                      blurRadius: 8,
                      offset: const Offset(0, 2),
                    ),
                  ],
                ),
                child: const Center(
                  child: Text(
                    "Expense Wizardinho",
                    style: TextStyle(
                      fontSize: 24.0,
                      fontWeight: FontWeight.w600,
                    ),
                  ),
                ),
              ),
              const SizedBox(height: 24.0),
              Container(
                width: double.infinity,
                height: 630.0,
                color: bgColorCards,
                padding: const EdgeInsets.all(10.0),
                child: Center(
                  child: Column(
                    mainAxisAlignment: MainAxisAlignment.center,
                    children: <Widget>[
                      const InfoComponent(),
                      TransactionViewModel(),
                      const SizedBox(height: 32.0,),

                    ],
                  ),
                ),
              )
            ],
          ),
        ),
      ),
    );
  }
}

class InfoComponent extends StatelessWidget {
  final Color bgColorCards = const Color(0xFFFFF8E1);

  const InfoComponent({super.key});

  @override
  Widget build(BuildContext context) {
    return Container(
      width: double.infinity,
      decoration: BoxDecoration(
        color: bgColorCards,
        boxShadow: [
          BoxShadow(
            color: Colors.black.withOpacity(0.2),
            spreadRadius: 2,
            blurRadius: 8,
            offset: const Offset(0,2),
          ),
        ],
      ),
      padding: const EdgeInsets.all(0.8),
      child: const Row(
        mainAxisAlignment: MainAxisAlignment.spaceBetween,
        children: <Widget>[
          Row(
            mainAxisAlignment: MainAxisAlignment.start,
            children: <Widget>[
              Icon(
                Icons.info,
                color: Colors.black26,
                size: 24.0,
              ),
              Text(
                "Cash",
                style: TextStyle(
                  fontWeight: FontWeight.w600,
                  fontSize: 18.0,
                ),
              ),
            ],
          ),
          Text(
            "1.500\$",
            style: TextStyle(
              fontSize: 18.0,
              fontWeight: FontWeight.w600,
            ),
          ),
        ],
      ),
    );
  }
}