package com.fausgoal.okhttp.builder;

import android.net.Uri;

import com.fausgoal.okhttp.request.GetRequest;
import com.fausgoal.okhttp.request.RequestCall;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

/**
 * Description：
 * <br/><br/>Created by zhy on 15/12/14.
 * <br/><br/>
 */
public class GetBuilder extends OkHttpRequestBuilder<GetBuilder> implements HasParamsable {

    @Override
    public RequestCall build() {
        if (mParams != null) {
            mUrl = appendParams(mUrl, mParams);
        }
        return new GetRequest(mUrl, mTag, mParams, mHeaders, mId)
                .build();
    }

    protected String appendParams(String url, Map<String, String> params) {
        if (url == null || params == null || params.isEmpty()) {
            return url;
        }
        Uri.Builder builder = Uri.parse(url).buildUpon();
        Set<String> keys = params.keySet();
        for (String key : keys) {
            builder.appendQueryParameter(key, params.get(key));
        }
        return builder.build().toString();
    }

    @Override
    public GetBuilder params(Map<String, String> params) {
        this.mParams = params;
        return this;
    }

    @Override
    public GetBuilder addParams(String key, String val) {
        if (this.mParams == null) {
            mParams = new LinkedHashMap<>();
        }
        mParams.put(key, val);
        return this;
    }
}
