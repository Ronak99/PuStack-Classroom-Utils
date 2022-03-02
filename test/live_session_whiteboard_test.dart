import 'package:flutter/services.dart';
import 'package:flutter_test/flutter_test.dart';
import 'package:live_session_whiteboard/live_session_whiteboard.dart';

void main() {
  const MethodChannel channel = MethodChannel('live_session_whiteboard');

  TestWidgetsFlutterBinding.ensureInitialized();

  setUp(() {
    channel.setMockMethodCallHandler((MethodCall methodCall) async {
      return '42';
    });
  });

  tearDown(() {
    channel.setMockMethodCallHandler(null);
  });

  test('getPlatformVersion', () async {
    // expect(await LiveSessionWhiteboard.platformVersion, '42');
  });
}
