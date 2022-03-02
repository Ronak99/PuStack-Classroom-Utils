package com.pustack.live_session_whiteboard.boardView.test;

import android.content.pm.ActivityInfo;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;

import com.pustack.live_session_whiteboard.boardView.BaseActivity;
import com.pustack.live_session_whiteboard.R;
import com.pustack.live_session_whiteboard.boardView.common.DemoAPI;
import com.pustack.live_session_whiteboard.boardView.utils.EmptyRoomListener;
import com.pustack.live_session_whiteboard.whiteboardSdk.Room;
import com.pustack.live_session_whiteboard.whiteboardSdk.RoomParams;
import com.pustack.live_session_whiteboard.whiteboardSdk.WhiteSdk;
import com.pustack.live_session_whiteboard.whiteboardSdk.WhiteSdkConfiguration;
import com.pustack.live_session_whiteboard.whiteboardSdk.WhiteboardView;
import com.pustack.live_session_whiteboard.whiteboardSdk.domain.CameraConfig;
import com.pustack.live_session_whiteboard.whiteboardSdk.domain.ImageInformationWithUrl;
import com.pustack.live_session_whiteboard.whiteboardSdk.domain.Promise;
import com.pustack.live_session_whiteboard.whiteboardSdk.domain.RectangleConfig;
import com.pustack.live_session_whiteboard.whiteboardSdk.domain.SDKError;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class CameraMoveActivity extends BaseActivity {
    private DemoAPI demoAPI = DemoAPI.get();

    private WhiteboardView whiteboardView;
    private WhiteSdk whiteSdk;
    private Room room;

    private double imgH;
    private double imgW;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera_move);
        getSupportActionBar().hide();

        whiteboardView = findViewById(R.id.white);
        WhiteboardView.setWebContentsDebuggingEnabled(true);

        joinRoom();

        findViewById(R.id.insertImage).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (room == null) {
                    return;
                }
                ImageInformationWithUrl imageInformationWithUrl = new ImageInformationWithUrl();
                imageInformationWithUrl.setUrl("https://placekitten.com/1920/1080");
                imageInformationWithUrl.setHeight(imgH);
                imageInformationWithUrl.setWidth(imgW);
                imageInformationWithUrl.setCenterX(0);
                imageInformationWithUrl.setCenterY(0);

                room.insertImage(imageInformationWithUrl);
            }
        });

        findViewById(R.id.scaleSmall).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moveToSmall();
            }
        });

        findViewById(R.id.scaleCamera).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moveCamera();
            }
        });

        findViewById(R.id.scaleCameraFill).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moveCameraFillContent();
            }
        });

        findViewById(R.id.orientation).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                orientation();
            }
        });
    }

    private void joinRoom() {
        WhiteSdkConfiguration configuration = new WhiteSdkConfiguration(demoAPI.getAppId(), true);
        whiteSdk = new WhiteSdk(whiteboardView, this, configuration);

        RoomParams roomParams = new RoomParams(demoAPI.getRoomUUID(), demoAPI.getRoomToken(), DemoAPI.DEFAULT_UID);
        whiteSdk.joinRoom(roomParams, new EmptyRoomListener(), new Promise<Room>() {
            @Override
            public void then(Room room) {
                CameraMoveActivity.this.room = room;
                loadImageInfo("https://placekitten.com/1920/1080");
            }

            @Override
            public void catchEx(SDKError t) {
                showToast(t.getMessage());
            }
        });
    }

    /**
     * This code is used as an example, the application needs to manage io and async itself.
     * The application may get the image width and height from the api
     *
     * @param src
     */
    public void loadImageInfo(String src) {
        new Thread(() -> {
            try {
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inJustDecodeBounds = true;

                InputStream input = new URL(src).openStream();
                BitmapFactory.decodeStream(input, null, options);
                imgH = options.outHeight;
                imgW = options.outWidth;
            } catch (IOException e) {
            }
        }).start();
    }

    public void moveToSmall() {
        CameraConfig config = new CameraConfig();
        config.setScale(0.1);
        room.moveCamera(config);
    }

    public void moveCamera() {
        RectangleConfig config = new RectangleConfig(imgW, imgH);
        room.moveCameraToContainer(config);
    }

    public void moveCameraFillContent() {
        double height = whiteboardView.getHeight();
        double width = whiteboardView.getWidth();

        double factor = Math.min((imgW / imgH) / (width / height), (imgH / imgW) / (height / width));
        room.moveCameraToContainer(new RectangleConfig(imgW * factor, imgH * factor));
    }

    public void orientation() {
        if (getRequestedOrientation() == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        } else {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (room != null) {
            room.disconnect();
        }
    }
}
