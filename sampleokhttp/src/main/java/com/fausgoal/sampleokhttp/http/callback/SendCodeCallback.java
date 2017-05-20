package com.fausgoal.sampleokhttp.http.callback;

import com.fausgoal.okhttp.HttpHandler;
import com.fausgoal.okhttp.callback.Callback;
import com.fausgoal.sampleokhttp.http.helper.HttpHelper;
import com.fausgoal.sampleokhttp.result.JsonResult;
import com.fausgoal.sampleokhttp.utils.JsonUtils;
import com.google.gson.reflect.TypeToken;

import java.util.List;

import okhttp3.Call;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * Description：发送验证码的请求回调
 * <br/><br/>Created by Fausgoal on 5/19/17.
 * <br/><br/>
 */
public class SendCodeCallback extends Callback<JsonResult<List<Void>>> {

    private final HttpHelper<List<Void>> mHelper;

    public SendCodeCallback(HttpHandler<JsonResult<List<Void>>> handler) {
        mHelper = new HttpHelper<>(handler);
    }

    @Override
    public JsonResult<List<Void>> parseNetworkResponse(Response response, int id) throws Exception {
        ResponseBody body = response.body();
        if (null == body) {
            return null;
        }
        String string = body.string();
        // 将json转为实体类
        return JsonUtils.getJsonToModel(string, new TypeToken<JsonResult<List<Void>>>() {
        });
    }

    @Override
    public void onError(Call call, Exception e, int id) {
        mHelper.handleException(call, e, id);
    }

    @Override
    public void onResponse(JsonResult<List<Void>> response, int id) {
        mHelper.handleResult(response, id);
    }
}
