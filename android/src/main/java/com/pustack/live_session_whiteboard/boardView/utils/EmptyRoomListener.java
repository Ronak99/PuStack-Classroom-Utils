package com.pustack.live_session_whiteboard.boardView.utils;


import com.pustack.live_session_whiteboard.whiteboardSdk.RoomListener;
import com.pustack.live_session_whiteboard.whiteboardSdk.domain.RoomPhase;
import com.pustack.live_session_whiteboard.whiteboardSdk.domain.RoomState;

public class EmptyRoomListener implements RoomListener {
    @Override
    public void onPhaseChanged(RoomPhase phase) {

    }

    @Override
    public void onDisconnectWithError(Exception e) {

    }

    @Override
    public void onKickedWithReason(String reason) {

    }

    @Override
    public void onRoomStateChanged(RoomState modifyState) {

    }

    @Override
    public void onCanUndoStepsUpdate(long canUndoSteps) {

    }

    @Override
    public void onCanRedoStepsUpdate(long canRedoSteps) {

    }

    @Override
    public void onCatchErrorWhenAppendFrame(long userId, Exception error) {

    }
}
