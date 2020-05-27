package com.example.applife;

import android.util.Log;

import com.example.router.annotation.RouterAppService;
import com.example.router.applife.IAppLifecycleService;

/**
 * 【说明】：
 *
 * @author daijun
 * @version 2.0
 * @date 2020/5/27 9:59
 */
@RouterAppService()
public class OrderAppLifecycleService implements IAppLifecycleService {
    private static final String TAG = "OrderAppLifecycleServic";
    @Override
    public void onCreate() {
        Log.e(TAG, "onCreate: 订单模块初始化");
    }
}
