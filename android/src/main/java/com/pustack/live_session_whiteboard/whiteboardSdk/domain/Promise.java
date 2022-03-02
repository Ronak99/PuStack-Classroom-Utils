package com.pustack.live_session_whiteboard.whiteboardSdk.domain;


// Created by buhe on 2018/8/12.

/**
 * 异步回调接口。
 */
public interface Promise<T> {
    void then(T t);

    void catchEx(SDKError t);
}
