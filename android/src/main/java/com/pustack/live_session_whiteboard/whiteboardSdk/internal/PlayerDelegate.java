package com.pustack.live_session_whiteboard.whiteboardSdk.internal;

import com.pustack.live_session_whiteboard.whiteboardSdk.domain.EventEntry;
import com.pustack.live_session_whiteboard.whiteboardSdk.domain.PlayerPhase;
import com.pustack.live_session_whiteboard.whiteboardSdk.domain.SDKError;

public interface PlayerDelegate {
    void fireMagixEvent(EventEntry fromJson);

    void fireHighFrequencyEvent(EventEntry[] events);

    void setPlayerPhase(PlayerPhase phase);

    void onLoadFirstFrame();

    void onSliceChanged(String valueOf);

    void syncDisplayerState(String valueOf);

    void onStoppedWithError(SDKError resolverSDKError);

    void setScheduleTime(long scheduleTime);

    void onCatchErrorWhenAppendFrame(SDKError resolverSDKError);

    void onCatchErrorWhenRender(SDKError resolverSDKError);
}
