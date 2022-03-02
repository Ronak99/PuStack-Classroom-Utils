package com.pustack.live_session_whiteboard.whiteboardSdk;

import com.pustack.live_session_whiteboard.whiteboardSdk.domain.ConversionInfo;
import com.pustack.live_session_whiteboard.whiteboardSdk.domain.ConvertException;
import com.pustack.live_session_whiteboard.whiteboardSdk.domain.ConvertedFiles;

/**
 * @deprecated 空实现类由用户应用处理
 */
@Deprecated
public class AbstractConverterCallbacks implements ConverterCallbacks {
    @Override
    public void onProgress(Double progress, ConversionInfo convertInfo) {

    }

    @Override
    public void onFinish(ConvertedFiles ppt, ConversionInfo convertInfo) {

    }

    @Override
    public void onFailure(ConvertException e) {

    }
}
