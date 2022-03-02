package com.pustack.live_session_whiteboard.whiteboardSdk;


import com.pustack.live_session_whiteboard.whiteboardSdk.domain.PlayerPhase;
import com.pustack.live_session_whiteboard.whiteboardSdk.domain.PlayerState;
import com.pustack.live_session_whiteboard.whiteboardSdk.domain.SDKError;

// Created by buhe on 2018/8/12.

/**
 * `PlayerEventListener` 接口的缺省（空）实现。详见 {@link PlayerEventListener PlayerEventListener}。
 *
 * @deprecated 已废弃。空实现类由用户应用处理。
 */
@Deprecated
public abstract class AbstractPlayerEventListener implements PlayerEventListener {

    @Override
    public void onPhaseChanged(PlayerPhase phase) {

    }

    @Override
    public void onLoadFirstFrame() {

    }

    @Override
    public void onSliceChanged(String slice) {

    }

    @Override
    public void onPlayerStateChanged(PlayerState modifyState) {

    }

    @Override
    public void onStoppedWithError(SDKError error) {

    }

    @Override
    public void onScheduleTimeChanged(long time) {

    }

    @Override
    public void onCatchErrorWhenAppendFrame(SDKError error) {

    }

    @Override
    public void onCatchErrorWhenRender(SDKError error) {

    }
}
