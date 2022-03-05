import 'package:flutter/material.dart';
import 'dart:async';

import 'package:flutter/services.dart';
import 'package:live_session_whiteboard/live_session_whiteboard.dart';

void main() {
  runApp(const MyApp());
}

class MyApp extends StatefulWidget {
  const MyApp({Key? key}) : super(key: key);

  @override
  State<MyApp> createState() => _MyAppState();
}

class _MyAppState extends State<MyApp> {
  String _platformVersion = 'Unknown';

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      home: Scaffold(
        backgroundColor: Colors.blue,
        appBar: AppBar(
          title: const Text('Plugin example app'),
        ),
        body: Container(
          margin: EdgeInsets.all(25),
          child: AspectRatio(
            aspectRatio: 16 / 9,
            child: LiveSessionWhiteboard(
              whiteboardAppId: "VHBvQEEXEeuIHrEufR7KaQ/cMKLzXlzkZaSoQ",
              roomId: "29d6eb609c6a11ecb30441b5cfef60d4",
              roomToken:
                  "NETLESSROOM_YWs9X3ZMZmdPeGtJelhhM1VENyZub25jZT0xNjQ2NDc2MjkzNzkwMDAmcm9sZT0yJnNpZz1hM2M1YjZmODdlOTI4MTEyNTVlNmFjMmQ1YWZhNTE3NGUyYTJkN2I0ZDdmMDQ0NDhlNThlNTg2NzYzNGY3ZGFmJnV1aWQ9MjlkNmViNjA5YzZhMTFlY2IzMDQ0MWI1Y2ZlZjYwZDQ",
              uid: "iodiod",
            ),
          ),
        ),
      ),
    );
  }
}
