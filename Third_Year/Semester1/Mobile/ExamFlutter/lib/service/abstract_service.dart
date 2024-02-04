import 'dart:convert';

import 'package:examflutter/domain/abstract_entity.dart';
import 'package:examflutter/utils/utilities.dart';
import 'package:logger/logger.dart';

class AbstractService<T extends AbstractEntity> {

  Logger logger = Logger();
  // disable windows firewall?
  final String baseUrl = "http://${Utilities.serverIpAndPort}";
}