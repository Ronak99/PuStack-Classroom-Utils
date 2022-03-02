package com.pustack.live_session_whiteboard.whiteboardSdk.domain;

public enum ConverterStatus {
    Idle,
    Created,
    CreateFail,
    Checking,
    WaitingForNextCheck,
    Timeout,
    CheckingFail,
    GetDynamicFail,
    Success,
    Fail,
}
