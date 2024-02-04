import 'package:examflutter/persistence/abstract_repository.dart';
import 'package:examflutter/utils/utilities.dart';
import 'package:logger/logger.dart';
import '../domain/fitness.dart';
import 'db_context.dart';

class Repository extends AbstractRepository<Fitness> {
  Future<List<Fitness>> getAllFitness() async {
    return await super.getAll(Utilities.principalTable, Fitness.fromMap);
  }

  Future<List<String>> getAllDates() async {
    final db = await DatabaseContext.instance.database;
    final result = await db.query(Utilities.secondTable);
    logger.log(Level.info, "DB: GET ALL DATES");
    return result.map((e) => e[Utilities.secondTable].toString()).toList();
  }

  Future<int> insertFitness(Fitness entity) {
    return super.insert(entity.toMap(), Utilities.principalTable);
  }
}