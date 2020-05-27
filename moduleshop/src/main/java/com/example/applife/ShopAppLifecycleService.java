package com.example.applife;

import android.util.Log;

import com.example.router.applife.IAppLifecycleService;

/**
 * 【说明】：
 *
 * @author daijun
 * @version 2.0
 * @date 2020/5/27 9:50
 */
public class ShopAppLifecycleService implements IAppLifecycleService {
    private static final String TAG = "ShopAppLifecycleService";
    @Override
    public void onCreate() {
        Log.e(TAG, "onCreate: 购物模块初始化");
    }
}
