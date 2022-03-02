package com.pustack.live_session_whiteboard.boardView;

import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.VisibleForTesting;
import androidx.appcompat.app.AppCompatActivity;
import androidx.test.espresso.IdlingResource;

import com.pustack.live_session_whiteboard.boardView.test.SimpleIdlingResource;

public abstract class BaseActivity extends AppCompatActivity {
    @Nullable
    private SimpleIdlingResource mIdlingResource = new SimpleIdlingResource();

    @VisibleForTesting
    @NonNull
    public IdlingResource getIdlingResource() {
        return mIdlingResource;
    }

    protected void testMarkIdling(boolean idling) {
        mIdlingResource.setIdleState(idling);
    }


    public void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}
