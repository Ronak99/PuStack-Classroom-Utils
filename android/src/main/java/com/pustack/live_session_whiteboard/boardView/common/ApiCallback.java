package com.pustack.live_session_whiteboard.boardView.common;

public interface ApiCallback<T> {
    void onSuccess(T data);

    void onFailure(String message);
}