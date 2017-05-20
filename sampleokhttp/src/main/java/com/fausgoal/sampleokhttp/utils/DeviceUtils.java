package com.fausgoal.sampleokhttp.utils;

import android.content.Context;
import android.content.Intent;
import android.os.Build;

/**
 * Description：设备工具类
 * <br/><br/>Created by Fausgoal on 5/20/17.
 * <br/><br/>
 */
public class DeviceUtils {
    /**
     * 获取 安卓系统版本
     *
     * @return 如HTC ||SDK8|| 4.0
     */
    public static String getSystemVersion() {
        return Build.MODEL + "||"
                + Build.VERSION.SDK_INT + "||Android "
                + Build.VERSION.RELEASE;
    }

    /**
     * 打开系统网络设置
     *
     * @param context context
     */
    public static void startWirelessSetting(Context context) {
        if (null == context) {
            return;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            //3.0以上打开设置界面
            context.startActivity(new Intent(android.provider.Settings.ACTION_SETTINGS));
        } else {
            context.startActivity(new Intent(android.provider.Settings.ACTION_WIRELESS_SETTINGS));
        }
    }
}
