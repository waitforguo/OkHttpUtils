package com.fausgoal.okhttp.request;

import com.fausgoal.okhttp.OkHttpUtils;
import com.fausgoal.okhttp.callback.Callback;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Description：对OkHttpRequest的封装，对外提供更多的接口：cancel(),readTimeOut()...
 * <br/><br/>Created by zhy on 15/12/15.
 * <br/><br/>
 */
public class RequestCall {
    private OkHttpRequest mOkHttpRequest;
    private Request mRequest;
    private Call mCall;

    private long mReadTimeOut;
    private long mWriteTimeOut;
    private long mConnTimeOut;

    public RequestCall(OkHttpRequest request) {
        this.mOkHttpRequest = request;
    }

    public RequestCall readTimeOut(long readTimeOut) {
        this.mReadTimeOut = readTimeOut;
        return this;
    }

    public RequestCall writeTimeOut(long writeTimeOut) {
        this.mWriteTimeOut = writeTimeOut;
        return this;
    }

    public RequestCall connTimeOut(long connTimeOut) {
        this.mConnTimeOut = connTimeOut;
        return this;
    }

    public Call buildCall(Callback callback) {
        mRequest = generateRequest(callback);

        if (mReadTimeOut > 0 || mWriteTimeOut > 0 || mConnTimeOut > 0) {
            mReadTimeOut = mReadTimeOut > 0 ? mReadTimeOut : OkHttpUtils.DEFAULT_MILLISECONDS;
            mWriteTimeOut = mWriteTimeOut > 0 ? mWriteTimeOut : OkHttpUtils.DEFAULT_MILLISECONDS;
            mConnTimeOut = mConnTimeOut > 0 ? mConnTimeOut : OkHttpUtils.DEFAULT_MILLISECONDS;

            OkHttpClient clone = OkHttpUtils.getIns().getOkHttpClient().newBuilder()
                    .readTimeout(mReadTimeOut, TimeUnit.MILLISECONDS)
                    .writeTimeout(mWriteTimeOut, TimeUnit.MILLISECONDS)
                    .connectTimeout(mConnTimeOut, TimeUnit.MILLISECONDS)
                    .build();

            mCall = clone.newCall(mRequest);
        } else {
            mCall = OkHttpUtils.getIns().getOkHttpClient().newCall(mRequest);
        }
        return mCall;
    }

    private Request generateRequest(Callback callback) {
        return mOkHttpRequest.generateRequest(callback);
    }

    public void execute(Callback callback) {
        buildCall(callback);

        if (callback != null) {
            callback.onBefore(mRequest, getOkHttpRequest().getId());
        }

        OkHttpUtils.getIns().execute(this, callback);
    }

    public Call getCall() {
        return mCall;
    }

    public Request getRequest() {
        return mRequest;
    }

    public OkHttpRequest getOkHttpRequest() {
        return mOkHttpRequest;
    }

    public Response execute() throws IOException {
        buildCall(null);
        return mCall.execute();
    }

    public void cancel() {
        if (mCall != null) {
            mCall.cancel();
        }
    }
}
