import 'package:examflutter/domain/abstract_entity.dart';

class Fitness extends AbstractEntity {
  String date;
  String type;
  double duration;
  double calories;
  String category;
  String description;

  Fitness(
      {required this.date,
        required this.type,
        required this.duration,
        required this.calories,
        required this.category,
        required this.description,
        int? id})
      : super(id: id);


  Map<String, dynamic> toMap() {
    return {
      'date': date,
      'type': type,
      'duration': duration,
      'calories': calories,
      'category': category,
      'description': description,
      'id': id
    };
  }

  factory Fitness.fromMap(Map<String, dynamic> map) {
    return Fitness(
        date: map['date'],
        type: map['type'],
        duration: (map['duration'] as num).toDouble(),
        calories: (map['calories'] as num).toDouble(),
        category: map['category'],
        description: map['description'],
        id: map['id'],
        );
  }

  @override
  String toString() {
    return 'Fitness{date: $date, type: $type, duration: $duration, calories: $calories, category: $category, description: $description}';
  }
}