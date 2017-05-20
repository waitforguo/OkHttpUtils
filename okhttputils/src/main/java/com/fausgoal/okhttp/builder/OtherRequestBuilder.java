package com.fausgoal.okhttp.builder;

import com.fausgoal.okhttp.request.OtherRequest;
import com.fausgoal.okhttp.request.RequestCall;

import okhttp3.RequestBody;

/**
 * DELETE、PUT、PATCH等其他方法
 */
public class OtherRequestBuilder extends OkHttpRequestBuilder<OtherRequestBuilder> {
    private RequestBody mRequestBody;
    private String mMethod;
    private String mContent;

    public OtherRequestBuilder(String method) {
        this.mMethod = method;
    }

    @Override
    public RequestCall build() {
        return new OtherRequest(mRequestBody, mContent, mMethod, mUrl, mTag, mParams, mHeaders, mId).build();
    }

    public OtherRequestBuilder requestBody(RequestBody requestBody) {
        this.mRequestBody = requestBody;
        return this;
    }

    public OtherRequestBuilder requestBody(String content) {
        this.mContent = content;
        return this;
    }
}
