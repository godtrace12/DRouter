package com.example.router.api;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * 【说明】：
 *
 * @author daijun
 * @version 2.0
 * @date 2020/5/25 19:01
 */
public class DRouter {

    private static volatile DRouter instance;
    private Map<String,String> routes = new HashMap<>();
    private String selRoutePath = null;
    private Context context;

    private DRouter(){

    }

    public static DRouter getInstance(){
        if (instance == null){
            synchronized (DRouter.class){
                if (instance == null){
                    instance = new DRouter();
                }
            }
        }
        return instance;
    }

    public boolean init(){
        try {
            Class<?> clazz = Class.forName("com.apt.demo.MyRouterClass");
            Object myRouterInstance = clazz.newInstance();
            Method methodGetRoutes = clazz.getDeclaredMethod("getAllPath");
            routes = (Map<String, String>) methodGetRoutes.invoke(myRouterInstance);
            return true;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return false;
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            return false;
        } catch (InstantiationException e) {
            e.printStackTrace();
            return false;
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
            return false;
        } catch (InvocationTargetException e) {
            e.printStackTrace();
            return false;
        }
    }

    public Map<String,String> getRoutes(){
        return routes;
    }

    public DRouter build(String routePath){
        if (routePath != null && !routes.isEmpty()){
            selRoutePath = routes.get(routePath);
        }
        return this;
    }

    public void inject(Context context){
        this.context = context;
    }

    public void navigation(){
        Intent intent = new Intent();
        intent.setClassName(context,selRoutePath);
        ((Activity)context).startActivity(intent);
    }
}