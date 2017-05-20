package com.fausgoal.okhttp.builder;

import com.fausgoal.okhttp.request.PostStringRequest;
import com.fausgoal.okhttp.request.RequestCall;

import okhttp3.MediaType;

/**
 * Description：String参数类型提交请求
 * <br/><br/>Created by zhy on 15/12/14.
 * <br/><br/>
 */
public class PostStringBuilder extends OkHttpRequestBuilder<PostStringBuilder> {
    private String mContent;
    private MediaType mMediaType;

    public PostStringBuilder content(String content) {
        this.mContent = content;
        return this;
    }

    public PostStringBuilder mediaType(MediaType mediaType) {
        this.mMediaType = mediaType;
        return this;
    }

    @Override
    public RequestCall build() {
        return new PostStringRequest(mUrl, mTag, mParams, mHeaders, mContent, mMediaType, mId)
                .build();
    }
}
