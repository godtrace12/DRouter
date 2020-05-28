package debug;

import android.app.Application;
import android.util.Log;

/**
 * 【说明】：
 *
 * @author daijun
 * @version 2.0
 * @date 2020/5/28 10:21
 */
public class ShopApplication extends Application {
    private static final String TAG = "ShopApplication";
    @Override
    public void onCreate() {
        super.onCreate();
        Log.e(TAG, "onCreate: ShopApplication onCreate");
    }
}
