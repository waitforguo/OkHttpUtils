package com.fausgoal.okhttp.builder;

import com.fausgoal.okhttp.OkHttpUtils;
import com.fausgoal.okhttp.request.OtherRequest;
import com.fausgoal.okhttp.request.RequestCall;

/**
 * Description：
 * <br/><br/>Created by zhy on 16/3/2.
 * <br/><br/>
 */
public class HeadBuilder extends GetBuilder {

    @Override
    public RequestCall build() {
        return new OtherRequest(null, null, OkHttpUtils.METHOD.HEAD, mUrl, mTag, mParams, mHeaders, mId)
                .build();
    }
}
