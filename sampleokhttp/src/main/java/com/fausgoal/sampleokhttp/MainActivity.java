package com.fausgoal.sampleokhttp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.fausgoal.okhttp.callback.HttpHandler;
import com.fausgoal.sampleokhttp.result.JsonResult;
import com.fausgoal.sampleokhttp.http.HttpManager;
import com.fausgoal.sampleokhttp.http.HttpRequestFiled;
import com.fausgoal.sampleokhttp.http.HttpRequestParams;
import com.fausgoal.sampleokhttp.http.callback.SendCodeCallback;

import java.util.LinkedHashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {

//    https://github.com/hongyangAndroid/okhttputils

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView tvSendCode = (TextView) findViewById(R.id.tvSendCode);

        tvSendCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendCode();
            }
        });
    }

    private void sendCode() {

        LinkedHashMap<String, Object> params = new LinkedHashMap<>();
        params.put(HttpRequestFiled.MOBILE, "18888888888");
        params.put(HttpRequestFiled.TYPE, "1");

        HttpManager.getInitialize().sendCode(HttpRequestParams.SEND_CODE, params,
                new SendCodeCallback(new HttpHandler<JsonResult<List<Void>>>(this) {
                    @Override
                    public void onSuccess(JsonResult<List<Void>> result, int id) {
                        Toast.makeText(MainActivity.this, "发送成功", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(JsonResult<List<Void>> result, int id) {
                        Toast.makeText(MainActivity.this, "发送失败", Toast.LENGTH_SHORT).show();
                    }
                }));
    }
}
