import 'package:flutter/foundation.dart';
import 'package:flutter/gestures.dart';
import 'package:flutter/material.dart';
import 'package:flutter/rendering.dart';
import 'package:flutter/services.dart';

class LiveSessionWhiteboard extends StatelessWidget {
  final String roomId;
  final String roomToken;
  final String whiteboardAppId;
  final String uid;

  const LiveSessionWhiteboard({
    Key? key,
    required this.roomId,
    required this.roomToken,
    required this.whiteboardAppId,
    required this.uid,
  }) : super(key: key);

  @override
  Widget build(BuildContext context) {
    // This is used in the platform side to register the view.
    const String viewType = 'whiteboard';
    // Pass parameters to the platform side.
    Map<String, dynamic> creationParams = <String, dynamic>{
      "room_id": roomId,
      "room_token": roomToken,
      "whiteboard_app_id": whiteboardAppId,
      "uid": uid,
    };

    return PlatformViewLink(
      viewType: viewType,
      surfaceFactory:
          (BuildContext context, PlatformViewController controller) {
        return AndroidViewSurface(
          controller: controller as AndroidViewController,
          gestureRecognizers: const <Factory<OneSequenceGestureRecognizer>>{},
          hitTestBehavior: PlatformViewHitTestBehavior.opaque,
        );
      },
      onCreatePlatformView: (PlatformViewCreationParams params) {
        return PlatformViewsService.initSurfaceAndroidView(
          id: params.id,
          viewType: viewType,
          layoutDirection: TextDirection.ltr,
          creationParams: creationParams,
          creationParamsCodec: const StandardMessageCodec(),
          onFocus: () {
            params.onFocusChanged(true);
          },
        )
          ..addOnPlatformViewCreatedListener(params.onPlatformViewCreated)
          ..create();
      },
    );
  }
}
