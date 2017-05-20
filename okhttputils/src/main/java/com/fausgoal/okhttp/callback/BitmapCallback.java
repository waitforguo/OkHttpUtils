package com.fausgoal.okhttp.callback;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * Description：
 * <br/><br/>Created by zhy on 15/12/14.
 * <br/><br/>
 */
public abstract class BitmapCallback extends Callback<Bitmap> {

    @Override
    public Bitmap parseNetworkResponse(Response response, int id) throws Exception {
        ResponseBody body = response.body();
        if (null != body) {
            return BitmapFactory.decodeStream(body.byteStream());
        }
        return null;
    }
}
