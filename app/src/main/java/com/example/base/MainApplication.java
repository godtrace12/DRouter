package com.example.base;

import android.app.Application;
import android.util.Log;

import com.example.router.api.DRouter;
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
    private static final String TAG = "MainApplication";
    @Override
    public void onCreate() {
        super.onCreate();
        // 法1 - 模块初始化，每个模块在固定包名下定义一个接口实现类
/*        List<Class> appServiceList = ClassUtils.getClassesList(this,"com.example.applife", IAppLifecycleService.class);
        for (int i = 0; i < appServiceList.size(); i++) {
            try {
                IAppLifecycleService service = (IAppLifecycleService) appServiceList.get(i).newInstance();
                service.onCreate();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            }
        }*/
        //法 2 - 模块初始化，每个模块定义一个接口实现类，用注解获取实现类，然后调用进行初始化
        Log.e(TAG, "onCreate: 使用注解进行初始化");
        List<IAppLifecycleService> serviceList = DRouter.getInstance().getAppServices();
        for (IAppLifecycleService service : serviceList){
            service.onCreate();
        }
    }
}
