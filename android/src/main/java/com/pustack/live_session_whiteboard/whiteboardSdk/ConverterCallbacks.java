package com.pustack.live_session_whiteboard.whiteboardSdk;

import com.pustack.live_session_whiteboard.whiteboardSdk.domain.ConversionInfo;
import com.pustack.live_session_whiteboard.whiteboardSdk.domain.ConvertException;
import com.pustack.live_session_whiteboard.whiteboardSdk.domain.ConvertedFiles;

public interface ConverterCallbacks {
    void onProgress(Double progress, ConversionInfo convertInfo);

    void onFinish(ConvertedFiles ppt, ConversionInfo convertInfo);

    void onFailure(ConvertException e);
}
