import 'dart:convert';

import 'package:examflutter/domain/fitness.dart';
import 'package:examflutter/service/abstract_service.dart';
import 'package:examflutter/utils/utilities.dart';
import 'package:http/http.dart' as http;
import 'package:logger/logger.dart';


class FitnessService extends AbstractService<Fitness> {
  FitnessService();

  Future<List<String>> getAllDates() async {
    var url = Uri.parse("$baseUrl/dayData");

    final response = await http.get(url).timeout(const Duration(seconds: 15));

    if (response.statusCode == 200) {
      logger.log(Level.info, "SERVER: GET ALL DATES");
      var items = <String>[];
      var body = response.body;
      var jsonList = jsonDecode(body) as List;
      for (var item in jsonList) {
        var entity = item as String;
        items.add(entity);
      }
      return items;
    }
    else {
      var body = response.body;
      var errorMessage = jsonDecode(body) as String;
      logger.log(Level.error, "SERVER ERROR: $errorMessage");
      throw Exception(errorMessage);
    }
  }

  Future<List<Fitness>> getAllFitness(String date) async {
    var url = Uri.parse("$baseUrl/activities/$date");
    try {
      final response = await http.get(url).timeout(const Duration(seconds: 15));
      if (response.statusCode == 200) {
        logger.log(Level.info, "SERVER: GET ALL items FOR GENRE $date");
        var items = <Fitness>[];
        var body = response.body;
        var jsonList = jsonDecode(body) as List;
        for (var item in jsonList) {
          var entity = Fitness.fromMap(item);
          items.add(entity);
        }
        return items;
      } else {
        var body = response.body;
        var errorMessage = jsonDecode(body) as String;
        throw Exception(errorMessage);
      }
    } catch (e) {
      logger.log(Level.error, "SERVER ERROR: $e");
      throw e;
    }
  }

  Future<List<Fitness>> getAll(String endpoint) async {
    var url = Uri.parse(baseUrl + "/" + endpoint);

    final response = await http.get(url);

    if (response.statusCode == 200) {
      logger.log(Level.info, "SERVER: GET ALL ITEMS FOR $endpoint");
      var items = <Fitness>[];
      var body = response.body;
      var jsonList = jsonDecode(body) as List;
      for (var item in jsonList) {
        var entity = Fitness.fromMap(item);
        items.add(entity);
      }

      return items;
    }
    else {
      var body = response.body;
      var errorMessage = jsonDecode(body) as String;
      logger.log(Level.error, "SERVER ERROR: $errorMessage");
      throw Exception(errorMessage);

    }
  }

  Future deleteItem(int id) async {
    var url = Uri.parse("$baseUrl/activity/$id");

    final response =
    await http.delete(url).timeout(const Duration(seconds: 7));

    if (response.statusCode == 200) {
      logger.log(Level.info, "SERVER: ITEM WITH ID $id DELETED");
      return;
    } else {
      var body = response.body;
      var errorMessage = jsonDecode(body) as Map<String, dynamic>;
      logger.log(Level.error, "SERVER ERROR: $errorMessage");
      throw Exception(errorMessage['text']);
    }
  }

  Future<Fitness> addFitness(Fitness item) async {
    var url = Uri.parse("$baseUrl/activity");
    try {
      final response = await http.post(url, headers: <String, String> {
        'Content-Type': 'application/json; charset=UTF-8',
      },
      body: jsonEncode(item.toMap()));
      if (response.statusCode == 200) {
        logger.log(Level.info, "SERVER: NEW ITEM ADDED");
        var body = response.body;
        var jsonItem = jsonDecode(body) as Map<String, dynamic>;
        var entity = Fitness.fromMap(jsonItem);
        return entity;
      }
      else {
        var body = response.body;
        var errorMessage = jsonDecode(body) as Map<String, dynamic>;
        logger.log(Level.error, "SERVER ERROR: $errorMessage");
        throw Exception(errorMessage['text']);
      }
    } catch (e) {
      logger.log(Level.error, "SERVER ERROR: $e");
      throw(e);
    }
  }

}