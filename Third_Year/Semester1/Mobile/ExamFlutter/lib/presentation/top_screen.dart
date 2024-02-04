import 'package:examflutter/service/service.dart';
import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:collection/collection.dart';

class TopScreen extends StatefulWidget {
  const TopScreen({super.key});
  
  @override
  State<TopScreen> createState() => _TopScreenState();
}

class _TopScreenState extends State<TopScreen> {
  FitnessService itemService = FitnessService();
  List<String> top3 = [];
  bool _isLoading = true;
  
  @override
  void initState() {
    super.initState();
    itemService.getAll("allActivities").then((value) {
        print(value);
        Map<String, int> popularActivites = {};
        for (var item in value) {
          popularActivites[item.category] = (popularActivites[item.category] ?? 0) + 1;
        }

        top3 = popularActivites.entries.toList()
            .sorted((a,b) => b.value.compareTo(a.value))
            .map((e) => "${e.key}: ${e.value}")
            .toList();


        setState(() {
          _isLoading = false;
        });
      }
    );
    
  }
  
  @override
  Widget build(BuildContext buildContext) {
    return Scaffold(
      appBar: AppBar(
        title: const Text("TOP 3"),
      ),
      body: _isLoading == true
        ? const Center(child: CircularProgressIndicator())
          : Column(
              children: [
                const SizedBox(
                  height: 20,
                ),
                const Text("Top 3 Categories", style: TextStyle(fontSize: 20),),
                const SizedBox(
                  height: 20,
                ),
                Column(
                  mainAxisAlignment: MainAxisAlignment.center,
                  crossAxisAlignment: CrossAxisAlignment.center,
                  children: [
                    Text(
                      top3[0],
                      style: const TextStyle(fontSize: 20),
                    ),
                    Text(
                      top3[1],
                      style: const TextStyle(fontSize: 20),
                    ),
                    Text(
                      top3[2],
                      style: const TextStyle(fontSize: 20),
                    ),
                  ],
                ),
                const SizedBox(
                  height: 20,
                ),
              ],
      ),
    );
  }
}