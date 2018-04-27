package com.example.llcgs.android_kotlin.base.network;


import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okio.Buffer;
import okio.BufferedSink;
import okio.ForwardingSink;
import okio.Okio;
import okio.Sink;

/**
 * com.gomejr.myf.framework.network.responsebody.ProgressRequestBody
 *
 * @author liulongchao
 * @since 2017/4/12
 */

public class ProgressRequestBody extends RequestBody {

    private RequestBody delegate;
    private OnUploadProgress listener;
    private CountingSink countingSink;

    public void setDelegate(RequestBody delegate) {
        this.delegate = delegate;
    }

    public ProgressRequestBody(OnUploadProgress listener) {
        this.listener = listener;
    }

    @Override
    public MediaType contentType() {
        return delegate.contentType();
    }

    @Override
    public long contentLength() {
        try {
            return delegate.contentLength();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return -1;
    }

    @Override
    public void writeTo(BufferedSink sink) throws IOException {
        BufferedSink bufferedSink;
        countingSink = new CountingSink(sink);
        bufferedSink = Okio.buffer(countingSink);
        delegate.writeTo(bufferedSink);
        bufferedSink.flush();
    }

    protected final class CountingSink extends ForwardingSink {

        private long bytesWritten = 0;

        public CountingSink(Sink delegate) {
            super(delegate);
        }

        @Override
        public void write(Buffer source, long byteCount) throws IOException {
            super.write(source, byteCount);
            bytesWritten += byteCount;
            listener.progress("正在上传", bytesWritten, contentLength());
        }
    }
}
