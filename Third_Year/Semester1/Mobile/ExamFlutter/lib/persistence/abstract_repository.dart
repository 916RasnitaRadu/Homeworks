import 'package:examflutter/domain/abstract_entity.dart';
import 'package:logger/logger.dart';
import 'package:sqflite/sqflite.dart';
import 'db_context.dart';

class AbstractRepository<T extends AbstractEntity> {

  Logger logger = Logger();

  Future<List<T>> getAll(String table, Function constructor) async {
    final db = await DatabaseContext.instance.database;
    final result = await db.query(table);
    logger.log(Level.info, "DB: GET ALL ITEMS");
    return result.map((e) => constructor(e) as T).toList();
  }

  Future<int> insert(Map<String, dynamic> entity, String tableName) async {
    final db = await DatabaseContext.instance.database;
    final int result = await db.insert(tableName, entity,
        conflictAlgorithm: ConflictAlgorithm.replace);
    logger.log(Level.info, "DB: NEW ITEM ADDED");
    return result;
  }

  Future<int> delete(int id, String table) async {
    final db = await DatabaseContext.instance.database;
    final result = await db.delete(table, where: 'id = ?', whereArgs: [id]);
    logger.log(Level.info, "DB: ITEM WITH ID $id DELETED");
    return result;
  }

  Future<int> update(Map<String, dynamic> entity, String table) async {
    final db = await DatabaseContext.instance.database;
    final result = await db.update(table, entity, where: 'id = ?', whereArgs: [entity['id']]);
    logger.log(Level.info, "DB: ITEM WITH ID ${entity['id']} UPDATED");
    return result;
  }
}