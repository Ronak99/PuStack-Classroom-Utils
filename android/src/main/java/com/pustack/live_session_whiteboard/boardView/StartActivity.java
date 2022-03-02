package com.pustack.live_session_whiteboard.boardView;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.pustack.live_session_whiteboard.R;
import com.pustack.live_session_whiteboard.boardView.common.DemoAPI;
import com.pustack.live_session_whiteboard.boardView.test.window.WindowTestActivity;

public class StartActivity extends AppCompatActivity {
    public static final String EXTRA_ROOM_UUID = "com.pustack.live_session_whiteboard.UUID";

    DemoAPI demoAPI = DemoAPI.get();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        demoAPI.downloadZip("https://convertcdn.netless.link/dynamicConvert/e1ee27fdb0fc4b7c8f649291010c4882.zip", getCacheDir().getAbsolutePath());
    }

    String getUuid() {
        EditText text = findViewById(R.id.editText);
        return text.getText().toString();
    }

    void tokenAlert() {
        tokenAlert("token", "Get SDK Token");
    }

    void tokenAlert(String title, String message) {
        AlertDialog alertDialog = new AlertDialog.Builder(StartActivity.this).create();
        alertDialog.setTitle(title);
        alertDialog.setMessage(message);
        alertDialog.setButton(
                AlertDialog.BUTTON_NEUTRAL,
                "OK",
                (dialog, which) -> dialog.dismiss());
        alertDialog.show();
    }

    public void joinNewRoom(View view) {
        if (demoAPI.invalidToken()) {
            tokenAlert();
            return;
        }

        Intent intent = new Intent(this, RoomActivity.class);
        startActivity(intent);
    }

    public void joinRoom(View view) {
        if (demoAPI.invalidToken()) {
            tokenAlert();
            return;
        }

        Intent intent = new Intent(this, RoomActivity.class);
        String uuid = getUuid();
        if (uuid.length() > 0) {
            intent.putExtra(EXTRA_ROOM_UUID, uuid);
        }
        startActivity(intent);
    }

    public void replayRoom(View view) {
        if (demoAPI.invalidToken()) {
            tokenAlert();
            return;
        }

        Intent intent = new Intent(this, PlayActivity.class);
        String uuid = getUuid();
        if (uuid.length() > 0) {
            intent.putExtra(EXTRA_ROOM_UUID, uuid);
            startActivity(intent);
        } else if (demoAPI.getRoomUUID().length() > 0) {
            intent.putExtra(EXTRA_ROOM_UUID, demoAPI.getRoomUUID());
            startActivity(intent);
        } else {
            tokenAlert("uuid", "请填入回放用 uuid");
        }
    }

    public void pureReplay(View view) {
        if (demoAPI.invalidToken()) {
            tokenAlert();
            return;
        }

        Intent intent = new Intent(this, PureReplayActivity.class);

        String uuid = getUuid();
        if (uuid.length() > 0) {
            intent.putExtra(EXTRA_ROOM_UUID, uuid);
            startActivity(intent);
        } else if (demoAPI.getRoomUUID().length() > 0) {
            intent.putExtra(EXTRA_ROOM_UUID, demoAPI.getRoomUUID());
            startActivity(intent);
        } else {
            tokenAlert("uuid", "请填入回放用 uuid");
        }
    }

    public void windowTest(View view) {
        if (demoAPI.invalidToken()) {
            tokenAlert();
            return;
        }

        Intent intent = new Intent(this, WindowTestActivity.class);
        startActivity(intent);
    }
}
