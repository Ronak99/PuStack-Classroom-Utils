package com.pustack.live_session_whiteboard.whiteboardSdk.internal;

public class JsCallWrapper {
    private final Runnable runnable;
    private final String message;

    public JsCallWrapper(Runnable runnable, String message) {
        this.runnable = runnable;
        this.message = message;
    }

    public void run() {
        try {
            runnable.run();
        } catch (AssertionError a) {
            throw a;
        } catch (Throwable e) {
            Logger.error(message, e);
        }
    }
}
