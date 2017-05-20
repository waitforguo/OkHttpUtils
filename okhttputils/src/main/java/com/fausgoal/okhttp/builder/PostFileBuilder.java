package com.fausgoal.okhttp.builder;

import com.fausgoal.okhttp.request.PostFileRequest;
import com.fausgoal.okhttp.request.RequestCall;

import java.io.File;

import okhttp3.MediaType;

/**
 * Description：文件参数类型提交请求
 * <br/><br/>Created by zhy on 15/12/14.
 * <br/><br/>
 */
public class PostFileBuilder extends OkHttpRequestBuilder<PostFileBuilder> {
    private File mFile;
    private MediaType mMediaType;


    public OkHttpRequestBuilder file(File file) {
        this.mFile = file;
        return this;
    }

    public OkHttpRequestBuilder mediaType(MediaType mediaType) {
        this.mMediaType = mediaType;
        return this;
    }

    @Override
    public RequestCall build() {
        return new PostFileRequest(mUrl, mTag, mParams, mHeaders, mFile, mMediaType, mId)
                .build();
    }
}
