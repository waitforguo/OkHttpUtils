package com.fausgoal.okhttp;

import android.content.Context;
import android.widget.Toast;

/**
 * Description：HTTP结果回调到界面处理
 * <br/><br/>Created by Fausgoal on 5/19/17.
 * <br/><br/>
 */
public class HttpHandler<T> {
    private final Context mContext;

    protected HttpHandler(Context context) {
        mContext = context;
    }

    public void onSuccess(T result, int id) {

    }

    public void onFailure(T result, int id) {

    }

    public void onRequestTimeout() {
        Toast.makeText(mContext, "连接超时", Toast.LENGTH_SHORT).show();
    }

    public void onConnectFailed() {
        Toast.makeText(mContext, "连接失败", Toast.LENGTH_SHORT).show();
    }
}
