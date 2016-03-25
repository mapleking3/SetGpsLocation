/*
 * MyApplication<br/>
 * 包含类名列表<br/>
 * 版本信息<br/>
 * date 2016/3/24 16:35<br/>
 * CopyRight IFLYTEK Co.,Ltd. All Rights Reserved.
 */
package com.example.setgpslocation;

import android.app.Application;

import com.baidu.mapapi.SDKInitializer;

/**
 * MyApplication
 *
 * @author ydwang3<br/>
 *         description: ${}<br/>
 *         create: 2016/3/24 16:35<br/>
 *         <p/>
 *         change by ydwang3, 2016/3/24 16:35, reason: ${}
 */
public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        // 在使用 SDK 各组间之前初始化 context 信息，传入 ApplicationContext
        SDKInitializer.initialize(this);
    }
}
