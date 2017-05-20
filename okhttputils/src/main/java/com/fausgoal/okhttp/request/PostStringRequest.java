package com.fausgoal.okhttp.request;

import com.fausgoal.okhttp.common.MediaTypeConst;
import com.fausgoal.okhttp.uitls.Exceptions;

import java.util.Map;

import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * Description：
 * <br/><br/>Created by zhy on 15/12/14.
 * <br/><br/>
 */
public class PostStringRequest extends OkHttpRequest {

    private String mContent;
    private MediaType mMediaType;

    public PostStringRequest(String url, Object tag, Map<String, String> params,
                             Map<String, String> headers, String content, MediaType mediaType,
                             int id) {
        super(url, tag, params, headers, id);
        this.mContent = content;
        this.mMediaType = mediaType;

        if (this.mContent == null) {
            Exceptions.illegalArgument("the content can not be null !");
        }
        if (this.mMediaType == null) {
            this.mMediaType = MediaTypeConst.MEDIA_TYPE_PLAIN;
        }
    }

    @Override
    protected RequestBody buildRequestBody() {
        return RequestBody.create(mMediaType, mContent);
    }

    @Override
    protected Request buildRequest(RequestBody requestBody) {
        return mBuilder.post(requestBody).build();
    }
}
