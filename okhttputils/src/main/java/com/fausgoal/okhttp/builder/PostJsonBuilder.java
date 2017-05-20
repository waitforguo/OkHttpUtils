package com.fausgoal.okhttp.builder;

import com.fausgoal.okhttp.request.PostJsonRequest;
import com.fausgoal.okhttp.request.RequestCall;

import okhttp3.MediaType;

/**
 * Description：Json参数类型提交请求
 * <br/><br/>Created by zhy on 15/12/14.
 * <br/><br/>
 */
public class PostJsonBuilder extends OkHttpRequestBuilder<PostJsonBuilder> {
    private String mContent;
    private MediaType mMediaType;

    public PostJsonBuilder content(String jsonContent) {
        this.mContent = jsonContent;
        return this;
    }

    public PostJsonBuilder mediaType(MediaType mediaType) {
        this.mMediaType = mediaType;
        return this;
    }

    @Override
    public RequestCall build() {
        return new PostJsonRequest(mUrl, mTag, mParams, mHeaders, mContent, mMediaType, mId)
                .build();
    }
}
