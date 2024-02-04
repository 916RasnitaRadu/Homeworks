import 'dart:async';
import 'dart:io';
import 'package:connectivity_plus/connectivity_plus.dart';
import 'package:logger/logger.dart';

// handles monitoring network connectivity
class NetworkConnectivity {
  NetworkConnectivity._();

  static final _instance = NetworkConnectivity._();
  static NetworkConnectivity get instance => _instance;
  final _networkConnectivity = Connectivity();
  final _controller = StreamController.broadcast(); // manage a broadcast stream
  Stream get myStream => _controller.stream.asBroadcastStream();
  Logger logger = Logger();

  // 1. set up the initial state of NetworkConnectivity instance
  void initialize() async {
    ConnectivityResult result = await _networkConnectivity.checkConnectivity(); // get initial connectivity status
    _checkStatus(result); // check the status
    _networkConnectivity.onConnectivityChanged.listen((result) { // subscribes to onConnectivityChanged
      // stream to listen for changes in network connectivity
      logger.log(Level.info, result);
      _checkStatus(result); // calls _checkStatus whenever a change occurs
    });
  }

  // 2.
  void _checkStatus(ConnectivityResult result) async {
    bool isOnline = false;
    try { // attempts to connect to google.com
      final result = await InternetAddress.lookup('google.com');
      isOnline = result.isNotEmpty && result[0].rawAddress.isNotEmpty; // set the isOnline field respectively to the result
    } on SocketException catch (_) {
      isOnline = false;
    }
    //  adds a map containing the connectivity result and the online status to the stream using _controller.sink.add
    _controller.sink.add({result : isOnline});
  }

  // close the stream when it's no longer needed.
  void disposeStream() => _controller.close();
}