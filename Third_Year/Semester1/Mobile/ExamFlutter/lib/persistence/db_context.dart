import 'package:examflutter/utils/utilities.dart';
import 'package:path/path.dart';
import 'package:sqflite/sqflite.dart';


class DatabaseContext {
  static final DatabaseContext instance = DatabaseContext._init();
  static Database? _database;

  DatabaseContext._init();

  // implement a singleton for database
  Future<Database> get database async {
    if (_database != null) {
      return _database!;
    }

    _database = await init();

    return _database!;
  }


  Future<Database> init() async {
    String createDBQuery =
        'CREATE TABLE ${Utilities.principalTable}(id INTEGER Primary Key, date TEXT, type TEXT, duration integer, calories integer, category TEXT, description TEXT)';

    return openDatabase(join(await getDatabasesPath(), 'fitness_database.db'),
      onCreate: (db, version) async {
        await db.execute(createDBQuery);

        return await db.execute(
          'CREATE TABLE ${Utilities.secondTable}(${Utilities.secondTable} TEXT Primary Key)',
        );
      }, version: 1);
  }

}