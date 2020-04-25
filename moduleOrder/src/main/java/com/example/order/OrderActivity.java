package com.example.order;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.aninterface.IOrderEntry;
import com.example.aninterface.IShopEntry;
import com.example.router.annotation.Route;

import java.util.Iterator;
import java.util.ServiceLoader;

@Route("com.example.order.OrderActivity")
public class OrderActivity extends AppCompatActivity {
    private static final String TAG = "OrderActivity";
    private Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        findViews();
    }

    private void findViews() {
        button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //resources -Meta-INF下的服务配置文件，后期应从手动改为apt等方式自动生成?
                Log.e(TAG, "onClick: module go shop click");
                ServiceLoader<IShopEntry> spEntry = ServiceLoader.load(IShopEntry.class);
                Iterator<IShopEntry> iterator = spEntry.iterator();
                if (iterator.hasNext()){
                    IShopEntry orderEntryImp = iterator.next();
                    orderEntryImp.gotoShopEntry(OrderActivity.this);

                }else{
                    Log.e(TAG, "onClick: did not find service iml");
                }
            }
        });
    }
}
