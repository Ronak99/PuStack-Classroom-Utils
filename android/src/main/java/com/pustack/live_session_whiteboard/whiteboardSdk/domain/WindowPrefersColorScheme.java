package com.pustack.live_session_whiteboard.whiteboardSdk.domain;

import com.google.gson.annotations.SerializedName;

public enum WindowPrefersColorScheme {
    @SerializedName("light")
    Light,

    @SerializedName("dark")
    Dark,

    @SerializedName("auto")
    Auto,
}
