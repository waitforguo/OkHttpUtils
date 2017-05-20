package com.fausgoal.okhttp.uitls;

import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;


/**
 * Description：json相互转换的类
 * <br/><br/>Created by Fausgoal on 2015/5/17.
 * <br/><br/>
 */
public class JsonUtils {
    private static final String TAG = "GLJsonToObject";

    public static <T> T getJsonToModel(JSONObject resonse, TypeToken<T> tTypeToken) {
        return getJsonToModel(resonse.toString(), tTypeToken);
    }

    public static <T> T getJsonToModel(Object obj, TypeToken<T> tTypeToken) {
        String json = getModeltoJson(obj);
        return getJsonToModel(json, tTypeToken);
    }

    public static <T> T getJsonToModel(String resonse, TypeToken<T> tTypeToken) {
        if (TextUtils.isEmpty(resonse)) {
            return null;
        }
        try {
            return new Gson().fromJson(resonse, tTypeToken.getType());
        } catch (Exception e) {
            Log.e(TAG, "解析json到实体转换失败！");
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 没有被 @Expose 标注的字段会被排除
     */
    public static <T> T getJsonToModelWithoutExpose(JSONObject resonse, TypeToken<T> tTypeToken) {
        return getJsonToModelWithoutExpose(resonse.toString(), tTypeToken);
    }

    /**
     * 没有被 @Expose 标注的字段会被排除
     */
    public static <T> T getJsonToModelWithoutExpose(String resonse, TypeToken<T> tTypeToken) {
        if (TextUtils.isEmpty(resonse)) {
            return null;
        }
        try {
            Gson gson = new GsonBuilder()
                    .excludeFieldsWithoutExposeAnnotation()
                    .create();
            return gson.fromJson(resonse, tTypeToken.getType());
        } catch (Exception e) {
            Log.e(TAG, "解析json到实体转换失败！");
            e.printStackTrace();
        }
        return null;
    }

    public static String getModeltoJson(Object obj) {
        if (null == obj) {
            return null;
        }
        try {
            return new Gson().toJson(obj);
        } catch (Exception e) {
            Log.e(TAG, "实体转换json失败！");
            e.printStackTrace();
        }
        return null;
    }
}
