import 'package:examflutter/utils/utilities.dart';
import 'package:logger/logger.dart';

import 'network.dart';
import 'package:web_socket_channel/web_socket_channel.dart';


class WebSocketConnection {
  static final WebSocketConnection instance = WebSocketConnection._init();
  static WebSocketChannel? _channel = init();
  static bool isConnected = false;
  static late Function? _onConnectionChange;
  static var _connectivity = NetworkConnectivity.instance;
  Logger logger = Logger();


  WebSocketConnection._init();

  static WebSocketChannel init() {
    try {
      var connection =
          WebSocketChannel.connect(Uri.parse("ws://${Utilities.serverIpAndPort}"));
      connection.ready.then((value) {
        _connectionChange(true);
        isConnected = true;
      });
      return connection;
    } catch (e) {
      print("Error can not connect to web socket: $e");
      isConnected = false;
      _connectionChange(false);
      return init();
    }
  }

  static void _connectionChange(bool status) {
    if (_onConnectionChange == null) {
      return;
    }

    _onConnectionChange!(status);
    if (status == false) init();
  }

  void listen(Function onReceive, Function onDone, Function onError, Function onConnectionChange) async {
    _onConnectionChange = onConnectionChange;
    _connectionChange(isConnected);

    _channel?.stream.listen((event) {
      logger.log(Level.info, event);
      isConnected = true;
      _connectionChange(true);
      onReceive(event);
    }, onDone: () async {
      logger.log(Level.info,"Connection aborted!");
      isConnected = false;
      _connectionChange(false);
      _channel = init();
      await Future.delayed(const Duration(seconds: 10));
      listen(onReceive, onDone, onError, onConnectionChange);
      onDone();
    }, onError: (e) {
      logger.log(Level.error,"Server error: $e");
      _channel = init();
      _connectionChange(false);
      onError();
    });
  }
}