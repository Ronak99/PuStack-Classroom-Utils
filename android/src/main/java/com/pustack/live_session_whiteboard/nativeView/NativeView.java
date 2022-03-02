package com.pustack.live_session_whiteboard.nativeView;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.VisibleForTesting;

import com.alibaba.sdk.android.httpdns.HttpDns;
import com.alibaba.sdk.android.httpdns.HttpDnsService;
import com.google.gson.Gson;
import com.pustack.live_session_whiteboard.boardView.LocalFileWebViewClient;
import com.pustack.live_session_whiteboard.boardView.WhiteWebViewClient;
import com.pustack.live_session_whiteboard.boardView.test.SimpleIdlingResource;
import com.pustack.live_session_whiteboard.boardView.utils.MapBuilder;
import com.pustack.live_session_whiteboard.whiteboardSdk.AbstractRoomCallbacks;
import com.pustack.live_session_whiteboard.whiteboardSdk.Room;
import com.pustack.live_session_whiteboard.whiteboardSdk.RoomCallbacks;
import com.pustack.live_session_whiteboard.whiteboardSdk.RoomParams;
import com.pustack.live_session_whiteboard.whiteboardSdk.WhiteSdk;
import com.pustack.live_session_whiteboard.whiteboardSdk.WhiteSdkConfiguration;
import com.pustack.live_session_whiteboard.whiteboardSdk.WhiteboardView;
import com.pustack.live_session_whiteboard.whiteboardSdk.domain.Appliance;
import com.pustack.live_session_whiteboard.whiteboardSdk.domain.MemberState;
import com.pustack.live_session_whiteboard.whiteboardSdk.domain.Promise;
import com.pustack.live_session_whiteboard.whiteboardSdk.domain.RoomPhase;
import com.pustack.live_session_whiteboard.whiteboardSdk.domain.RoomState;
import com.pustack.live_session_whiteboard.whiteboardSdk.domain.SDKError;

import io.flutter.plugin.platform.PlatformView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Map;
import java.util.Objects;

public class NativeView implements PlatformView{
    @NonNull private final WhiteboardView mWhiteboardView;
    WhiteSdk mWhiteSdk;
    Room mRoom;
    final Gson gson = new Gson();
    boolean showWhiteboard = true;

    @NonNull private final TextView textView;

    @VisibleForTesting
    RoomCallbacks mRoomCallbackHook = new AbstractRoomCallbacks() {
    };

    @NonNull private String roomId;
    @NonNull private String roomToken;
    @NonNull private String whiteboardAppId;
    @NonNull private String uid;

    private void initializeCredentials(Map<String, Object> creationParams){
        roomId = Objects.requireNonNull(creationParams.get("room_id")).toString();
        roomToken = Objects.requireNonNull(creationParams.get("room_token")).toString();
        whiteboardAppId = Objects.requireNonNull(creationParams.get("whiteboard_app_id")).toString();
        uid = Objects.requireNonNull(creationParams.get("uid")).toString();
    }

    NativeView(@NonNull Context context, int id, @Nullable Map<String, Object> creationParams) {
        assert creationParams != null;

        initializeCredentials(creationParams);

        textView = new TextView(context);
        textView.setTextSize(15);
        textView.setText("Loading...");

        mWhiteboardView = new WhiteboardView(context);
        mWhiteboardView.getSettings().setAllowUniversalAccessFromFileURLs(true);

        useHttpDnsService(false);

        LocalFileWebViewClient client = new LocalFileWebViewClient();
        mWhiteboardView.setWebViewClient(client);

        new SimpleIdlingResource().setIdleState(false);

        joinRoom();
    }

    @NonNull
    @Override
    public View getView() {
        return showWhiteboard ? mWhiteboardView : textView;
    }

    @Override
    public void dispose() {}

    void logRoomInfo(String str) {
        Log.i("native_view", Thread.currentThread().getStackTrace()[3].getMethodName() + " " + str);
    }

    private void joinRoom(){
        WhiteSdkConfiguration configuration = new WhiteSdkConfiguration(whiteboardAppId, true);
        /*显示用户头像*/
        configuration.setUserCursor(true);
        //动态 ppt 需要的自定义字体，如果没有使用，无需调用
        configuration.setFonts(new MapBuilder<String, String>().put("宋体", "https://your-cdn.com/Songti.ttf").build());

        mWhiteSdk = new WhiteSdk(mWhiteboardView, getView().getContext(), configuration);

        RoomParams roomParams = new RoomParams(roomId, roomToken, uid);

        final Date joinDate = new Date();

        mWhiteSdk.joinRoom(roomParams, new RoomCallbacks() {
            @Override
            public void onCanUndoStepsUpdate(long canUndoSteps) {
                mRoomCallbackHook.onCanUndoStepsUpdate(canUndoSteps);
                logRoomInfo("canUndoSteps: " + canUndoSteps);
            }

            @Override
            public void onCanRedoStepsUpdate(long canRedoSteps) {
                mRoomCallbackHook.onCanRedoStepsUpdate(canRedoSteps);
                logRoomInfo("onCanRedoStepsUpdate: " + canRedoSteps);
            }

            @Override
            public void onCatchErrorWhenAppendFrame(long userId, Exception error) {
                mRoomCallbackHook.onCatchErrorWhenAppendFrame(userId, error);
                logRoomInfo("onCatchErrorWhenAppendFrame: " + userId + " error " + error.getMessage());
            }

            @Override
            public void onPhaseChanged(RoomPhase phase) {
                mRoomCallbackHook.onPhaseChanged(phase);
                //在此处可以处理断连后的重连逻辑
                logRoomInfo("onPhaseChanged: " + phase.name());
            }

            @Override
            public void onDisconnectWithError(Exception e) {
                mRoomCallbackHook.onDisconnectWithError(e);
                logRoomInfo("onDisconnectWithError: " + e.getMessage());
            }

            @Override
            public void onKickedWithReason(String reason) {
                mRoomCallbackHook.onKickedWithReason(reason);
                logRoomInfo("onKickedWithReason: " + reason);
            }

            @Override
            public void onRoomStateChanged(RoomState modifyState) {
                mRoomCallbackHook.onRoomStateChanged(modifyState);
                logRoomInfo("onRoomStateChanged:" + gson.toJson(modifyState));
            }
        }, new Promise<Room>() {
            @Override
            public void then(Room room) {
                //记录加入房间消耗的时长
                logRoomInfo("native join in room duration: " + (System.currentTimeMillis() - joinDate.getTime()) / 1000f + "s");
                mRoom = room;

                MemberState memberState = new MemberState();
                memberState.setStrokeColor(new int[]{99, 99, 99});
                memberState.setCurrentApplianceName(Appliance.PENCIL);
                memberState.setStrokeWidth(10);
                memberState.setTextSize(10);
                mRoom.setMemberState(memberState);


                addCustomEventListener();
            }

            @Override
            public void catchEx(SDKError t) {
                logRoomInfo("native join fail: " + t.getMessage());
            }
        });
    }

    private void addCustomEventListener() {
        mRoom.addMagixEventListener("EVENT_NAME", event -> {
            logRoomInfo("customEvent payload: " + event.getPayload().toString());
        });
    }

    private void useHttpDnsService(boolean use) {
        if (use) {
            /** 直接使用此 id 即可，sdk 已经在阿里云 HttpDns 后台做过配置 */
            HttpDnsService httpDnsService = HttpDns.getService(getView().getContext(), "188301");
            httpDnsService.setPreResolveHosts(new ArrayList<>(Arrays.asList("expresscloudharestoragev2.herewhite.com", "cloudharev2.herewhite.com", "scdncloudharestoragev3.herewhite.com", "cloudcapiv4.herewhite.com")));
            mWhiteboardView.setWebViewClient(new WhiteWebViewClient(httpDnsService));
        }
    }
}
