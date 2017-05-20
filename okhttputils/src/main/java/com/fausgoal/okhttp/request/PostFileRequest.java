package com.fausgoal.okhttp.request;

import com.fausgoal.okhttp.OkHttpUtils;
import com.fausgoal.okhttp.callback.Callback;
import com.fausgoal.okhttp.common.MediaTypeConst;
import com.fausgoal.okhttp.uitls.Exceptions;

import java.io.File;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * Description：
 * <br/><br/>Created by zhy on 15/12/14.
 * <br/><br/>
 */
public class PostFileRequest extends OkHttpRequest {

    private File mFile;
    private MediaType mMediaType;

    public PostFileRequest(String url, Object tag, Map<String, String> params,
                           Map<String, String> headers, File file, MediaType mediaType,
                           int id) {
        super(url, tag, params, headers, id);
        this.mFile = file;
        this.mMediaType = mediaType;

        if (this.mFile == null) {
            Exceptions.illegalArgument("the file can not be null !");
        }
        if (this.mMediaType == null) {
            this.mMediaType = MediaTypeConst.MEDIA_TYPE_STREAM;
        }
    }

    @Override
    protected RequestBody buildRequestBody() {
        return RequestBody.create(mMediaType, mFile);
    }

    @Override
    protected RequestBody wrapRequestBody(RequestBody requestBody, final Callback callback) {
        if (callback == null) return requestBody;
        CountingRequestBody countingRequestBody = new CountingRequestBody(requestBody,
                new CountingRequestBody.Listener() {
                    @Override
                    public void onRequestProgress(final long bytesWritten, final long contentLength) {

                        OkHttpUtils.getIns().getDelivery().execute(new Runnable() {
                            @Override
                            public void run() {
                                callback.inProgress(bytesWritten * 1.0f / contentLength,
                                        contentLength, mId);
                            }
                        });

                    }
                });
        return countingRequestBody;
    }

    @Override
    protected Request buildRequest(RequestBody requestBody) {
        return mBuilder.post(requestBody).build();
    }
}
