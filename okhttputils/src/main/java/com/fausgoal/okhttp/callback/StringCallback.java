package com.fausgoal.okhttp.callback;

import java.io.IOException;

import okhttp3.Response;

/**
 * Description：
 * <br/><br/>Created by zhy on 15/12/14.
 * <br/><br/>
 */
public abstract class StringCallback extends Callback<String> {
    @Override
    public String parseNetworkResponse(Response response, int id) throws IOException {
        return response.body().string();
    }
}
