package com.pustack.live_session_whiteboard.boardView.test.window;

import com.pustack.live_session_whiteboard.whiteboardSdk.domain.SyncedState;

public class UserSyncedState extends SyncedState {
    public DragViewState dragViewState = new DragViewState();
}

class DragViewState extends SyncedState {
    // position
    public float w = 0;
    public float h = 0;
    public float offX = 0;
    public float offY = 0;

    // logic
    public long leftTime = 0;
}
