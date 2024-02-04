import 'package:flutter/cupertino.dart';
import 'package:collection/collection.dart';
import 'package:flutter/material.dart';
import '../service/service.dart';

class ProgressScreen extends StatefulWidget {
  const ProgressScreen({super.key});

  @override
  State<ProgressScreen> createState() => _ProgressScreenState();
}

class _ProgressScreenState extends State<ProgressScreen> {
  FitnessService itemService = FitnessService();
  final Map<String, double> _durations = {};
  bool _isLoading = true;

  @override
  void initState() {
    super.initState();
    itemService.getAll("allActivities").then((value) {
      for (var item in value) {
        var elements = item.date.split("-");
        var month = elements[1];
        _durations[month] = (_durations[month] ?? 0) + item.duration;

      }
      var sortedDurations = _durations.entries.toList()..sort((a,b) => b.value.compareTo(a.value));

      _durations.clear();
      for (var entry in sortedDurations) {
        _durations[entry.key] = entry.value;
      }

      setState(() {
        _isLoading = false;
      });
    });
  }

  @override
  Widget build(BuildContext buildContext) {
    return Scaffold(
      appBar: AppBar(
        title: const Text("Progress"),
      ),
      body: _isLoading == true
              ? const Center(child: CircularProgressIndicator())
          : Column(
            children: [
              const SizedBox(
                height: 20,
              ),
              const Text(
                "Duration per month",
                style: TextStyle(fontSize: 20),
              ),
              const SizedBox(
                height: 20,
              ),
              Expanded(
                  child: ListView(
                    children: _durations.entries.toList()
                        .map((e) => Row(
                      mainAxisAlignment: MainAxisAlignment.spaceEvenly,
                      children: [
                        Text(
                          "Month ${e.key}",
                          style: const TextStyle(fontSize: 20),
                        ),
                        Text(
                          "${e.value} (duration idk)",
                          style: const TextStyle(fontSize: 20),
                        ),
                      ],
                    ))
                        .toList(),
                  )),
            ],
          ),
    );
  }


}