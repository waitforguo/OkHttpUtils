package com.fausgoal.okhttp.request;

import android.text.TextUtils;

import com.fausgoal.okhttp.OkHttpUtils;
import com.fausgoal.okhttp.common.MediaTypeConst;
import com.fausgoal.okhttp.uitls.Exceptions;

import java.util.Map;

import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.internal.http.HttpMethod;

/**
 * Description：
 * <br/><br/>Created by zhy on 16/2/23.
 * <br/><br/>
 */
public class OtherRequest extends OkHttpRequest {
    private RequestBody mRequestBody;
    private String mMethod;
    private String mContent;

    public OtherRequest(RequestBody requestBody, String content, String method, String url, Object tag, Map<String, String> params, Map<String, String> headers, int id) {
        super(url, tag, params, headers, id);
        this.mRequestBody = requestBody;
        this.mMethod = method;
        this.mContent = content;
    }

    @Override
    protected RequestBody buildRequestBody() {
        if (mRequestBody == null && TextUtils.isEmpty(mContent)
                && HttpMethod.requiresRequestBody(mMethod)) {
            Exceptions.illegalArgument("requestBody and content can not be null in method:" + mMethod);
        }

        if (mRequestBody == null && !TextUtils.isEmpty(mContent)) {
            mRequestBody = RequestBody.create(MediaTypeConst.MEDIA_TYPE_PLAIN, mContent);
        }
        return mRequestBody;
    }

    @Override
    protected Request buildRequest(RequestBody requestBody) {
        switch (mMethod) {
            case OkHttpUtils.METHOD.PUT:
                mBuilder.put(requestBody);
                break;
            case OkHttpUtils.METHOD.DELETE:
                if (requestBody == null) {
                    mBuilder.delete();
                } else {
                    mBuilder.delete(requestBody);
                }
                break;
            case OkHttpUtils.METHOD.HEAD:
                mBuilder.head();
                break;
            case OkHttpUtils.METHOD.PATCH:
                mBuilder.patch(requestBody);
                break;
        }
        return mBuilder.build();
    }
}
