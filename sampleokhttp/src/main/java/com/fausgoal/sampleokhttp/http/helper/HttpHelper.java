package com.fausgoal.sampleokhttp.http.helper;

import com.fausgoal.okhttp.callback.HttpHandler;
import com.fausgoal.sampleokhttp.http.HttpResponseCode;
import com.fausgoal.sampleokhttp.result.JsonResult;

import org.apache.http.conn.ConnectTimeoutException;

import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import okhttp3.Call;

/**
 * Description：封装的API结果帮助类
 * <br/><br/>Created by Fausgoal on 5/19/17.
 * <br/><br/>
 */
public class HttpHelper<T> implements HttpResponseCode {

    private final HttpHandler<JsonResult<T>> mHandler;

    public HttpHelper(HttpHandler<JsonResult<T>> handler) {
        mHandler = handler;
    }

    public void handleResult(JsonResult<T> result, int id) {
        // 请求成功
        if (null != result && result.getStatus() == SUCCESS) {
            mHandler.onSuccess(result, id);
        } else {
            mHandler.onFailure(result, id);
        }
    }

    public void handleException(Call call, Exception error, int id) {
        /**
         * 超时异常
         */
        if (error instanceof SocketTimeoutException
                || error instanceof ConnectTimeoutException) {
            mHandler.onRequestTimeout();
        }
        /**
         * 网络连接异常
         */
        else if (error instanceof UnknownHostException
                || error instanceof SocketException) {
            mHandler.onConnectFailed();
        } else {
            mHandler.onFailure(null, id);
        }
    }
}
