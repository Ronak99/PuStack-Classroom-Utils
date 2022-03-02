package com.pustack.live_session_whiteboard.boardView;

import android.app.Application;

import com.pustack.live_session_whiteboard.boardView.common.DemoAPI;
import com.pustack.live_session_whiteboard.whiteboardSdk.WhiteboardView;

public class MainApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        DemoAPI.get().init(getApplicationContext());

        WhiteboardView.setWebContentsDebuggingEnabled(true);
    }
}
