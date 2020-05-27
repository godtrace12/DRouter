package com.example.base;

import android.app.Application;

import com.example.router.api.util.ClassUtils;
import com.example.router.applife.IAppLifecycleService;

import java.util.List;

/**
 * 【说明】：
 *
 * @author daijun
 * @version 2.0
 * @date 2020/5/27 10:02
 */
public class MainApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        List<Class> appServiceList = ClassUtils.getClassesList(this,"com.example.applife", IAppLifecycleService.class);
        for (int i = 0; i < appServiceList.size(); i++) {
            try {
                IAppLifecycleService service = (IAppLifecycleService) appServiceList.get(i).newInstance();
                service.onCreate();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            }
        }
    }
}
