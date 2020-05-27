package com.example.router.api.util;

import android.content.Context;
import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import dalvik.system.DexFile;

/**
 * 【说明】：通过反射机制获取位于同一个包下的接口的所有实现类--不起作用，可能不适合安卓
 *
 * @author daijun
 * @version 2.0
 * @date 2020/5/26 13:44
 */
public class ClassUtils {
    private static final String TAG = "ClassUtils";
    public static List<Class> getAllImplClassesByInterface(Class c) {
        // 给一个接口，返回这个接口的所有实现类 
        List<Class> returnClassList = new ArrayList<Class>();// 返回结果 
// 如果不是一个接口，则不做处理 
        if (c.isInterface()) {
            String packageName = c.getPackage().getName();// 获得当前包名 
            try {
                List<Class> allClass = getClassesByPackageName(packageName);// 获得当前包下以及包下的所有类 
                for (int i = 0; i < allClass.size(); i++) {
                    /*** 判定此 Class 对象所表示的类或接口与指定的 Class 参数cls所表示的类或接口是否相同，* 或是否是其超类或(超)接口，如果是则返回 true，否则返回 false。*/
                    if (c.isAssignableFrom(allClass.get(i))) {
                        if (!c.equals(allClass.get(i))) {// 本身加不进去 
                            returnClassList.add(allClass.get(i));
                        }
                    }
                }
            } catch (ClassNotFoundException e) {
// TODO Auto-generated catch block 
                e.printStackTrace();
            } catch (IOException e) {
                // TODO: handle exception 
                e.printStackTrace();
            }
        }
        return returnClassList;
    }

// 从一个包中查找出所有类,在jar包中不能查找 
private static List<Class> getClassesByPackageName(String packageName) throws IOException, ClassNotFoundException {
    ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
    String path = packageName.replace('.', '/');
    Enumeration<URL> resources = classLoader.getResources(path);
    List<File> dirs = new ArrayList<File>();
    while (resources.hasMoreElements()) {
        URL resource = resources.nextElement();
        dirs.add(new File(resource.getFile()));
    }
    ArrayList<Class> classes = new ArrayList<Class>();
    for (File directory : dirs) {
        classes.addAll(findClasses(directory, packageName));
    }
    return classes;
}

    private static List<Class> findClasses(File directory, String packageName)
            throws ClassNotFoundException {
        List<Class> classes = new ArrayList<Class>();
        if (!directory.exists()) {
            return classes;
        }
        File[] files = directory.listFiles();
        for (File file : files) {
            if (file.isDirectory()) {
                //递归查找文件夹下面的所有文件
                assert !file.getName().contains(".");
/**
 　　　　　　　　 * J2SE 1.4在语言上提供了一个新特性，就是assertion(断言)功能，它是该版本在Java语言方面最大的革新。在软件开发中，assertion是一种经典的调试、测试方式。

 　 　 　 　 　 在语法上，为了支持assertion，Java增加了一个关键字assert。它包括两种表达式，分别如下：

 assert expression1;

 assert expression1: expression2;

 在两种表达式中，expression1表示一个boolean表达式，expression2表示一个基本类型或者是一个对象(Object) ，基本类型包括boolean,char,double,float,int和long。由于所有类都为Object的子类，因此这个参数可以用于所有对象。

 assert

 如果为true，则程序继续执行。

 如果为false，则程序抛出AssertionError，并终止执行。
 　　　　　　　　 */
                classes.addAll(findClasses(file, packageName + '.' + file.getName()));
            } else if (file.getName().endsWith(".class")) {
                classes.add(Class.forName(packageName + "." + file.getName().substring(0, file.getName().length() - 6)));
            }
        }
        return classes;
    }


    /**
    *【说明】：Android获取某一个包名下的，指定接口的实现类。亲测有效
    *@author daijun
    *@date 2020/5/26 14:59
    *@param
    *@return
    */
    public static List<String> getClasses(Context mContext, String packageName,Class clzzInterface) {
        ArrayList<String> classes = new ArrayList<>();
        try {
            String packageCodePath = mContext.getPackageCodePath();
            DexFile df = new DexFile(packageCodePath);
            String regExp = "^" + packageName + ".\\w+$";
            for (Enumeration iter = df.entries(); iter.hasMoreElements(); ) {
                String className = (String) iter.nextElement();
                if (className.matches(regExp)) {
                    Class<?> clazz = Class.forName(className);
                    Log.e(TAG, "getClasses: "+clazz.getName());
                    //父类.class.isAssignableFrom(子类.class) 调用者为父类，参数为本身或者其子类。
                    //用于判断继承关系
                    if (clzzInterface.isAssignableFrom(clazz) && !clazz.isInterface()){
                        classes.add(className);
//                        continue;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return classes;
    }

    /**
     *【说明】：Android获取某一个包名下的，指定接口的实现类。亲测有效
     *@author daijun
     *@date 2020/5/26 14:59
     *@param
     *@return
     */
    public static List<Class> getClassesList(Context mContext, String packageName,Class clzzInterface) {
        ArrayList<String> classes = new ArrayList<>();
        ArrayList<Class> classList = new ArrayList<>();
        try {
            String packageCodePath = mContext.getPackageCodePath();
            DexFile df = new DexFile(packageCodePath);
            String regExp = "^" + packageName + ".\\w+$";
            for (Enumeration iter = df.entries(); iter.hasMoreElements(); ) {
                String className = (String) iter.nextElement();
                if (className.matches(regExp)) {
                    Class<?> clazz = Class.forName(className);
                    Log.e(TAG, "getClasses: "+clazz.getName());
                    //父类.class.isAssignableFrom(子类.class) 调用者为父类，参数为本身或者其子类。
                    //用于判断继承关系
                    if (clzzInterface.isAssignableFrom(clazz) && !clazz.isInterface()){
                        classes.add(className);
                        classList.add(clazz);
//                        continue;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return classList;
    }

    /**
     *【说明】：Android获取某一个包名下的所有实现类
     *@author daijun
     *@date 2020/5/26 14:59
     *@param
     *@return
     */
    public static List<Class> getClassesList(Context mContext, String packageName) {
        ArrayList<String> classes = new ArrayList<>();
        ArrayList<Class> classList = new ArrayList<>();
        try {
            String packageCodePath = mContext.getPackageCodePath();
            DexFile df = new DexFile(packageCodePath);
            String regExp = "^" + packageName + ".\\w+$";
            for (Enumeration iter = df.entries(); iter.hasMoreElements(); ) {
                String className = (String) iter.nextElement();
//                if (className.startsWith("com")){
//                    Log.e(TAG, "getClassesList: packName="+className);
//                }
                if (className.matches(regExp)) {
                    Class<?> clazz = Class.forName(className);
                    Log.e(TAG, "getClasses: "+clazz.getName());
                    classList.add(clazz);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return classList;
    }


    //暂时不用
    @Deprecated
    public static List<Class> getClassesFromPackage(Context ctx, String pkgName) {
        List<Class> rtnList = new ArrayList<>();
        String[] apkPaths = ctx.getApplicationInfo().splitSourceDirs;// 获得所有的APK的路径
        DexFile dexfile = null;
        Enumeration<String> entries = null;
        String name = null;
        for (String apkPath : apkPaths) {
            try {
                dexfile = new DexFile(apkPath);// 获得编译后的dex文件
                entries = dexfile.entries();// 获得编译后的dex文件中的所有class
                while (entries.hasMoreElements()) {
                    name = (String) entries.nextElement();
                    if (name.startsWith(pkgName)) {// 判断类的包名是否符合
                        rtnList.add(Class.forName(name));
                    }
                }
            } catch (ClassNotFoundException | IOException e) {
            } finally {
                try {
                    if (dexfile != null) {
                        dexfile.close();
                    }
                } catch (IOException e) {
                }
            }
        }
        return rtnList;
    }

}
