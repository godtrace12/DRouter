package com.example.router;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.aninterface.IOrderEntry;
import com.example.aninterface.IShopEntry;

import java.util.Iterator;
import java.util.ServiceLoader;

import javax.security.auth.login.LoginException;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "MainActivity";
    private Button button;
    private Button button2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViews();
    }

    private void findViews() {
        button = findViewById(R.id.button);
        button2 = findViewById(R.id.button2);
        button.setOnClickListener(this);
        button2.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int viewId = v.getId();
        switch (viewId){
            case R.id.button:
                //resources -Meta-INF下的服务配置文件，后期应从手动改为apt等方式自动生成?
                Log.e(TAG, "onClick: module order click");
                ServiceLoader<IOrderEntry> orderEntry = ServiceLoader.load(IOrderEntry.class);
                Iterator<IOrderEntry> iterator = orderEntry.iterator();
                if (iterator.hasNext()){
                    IOrderEntry orderEntryImp = iterator.next();
                    String result = orderEntryImp.sayHello();
                    orderEntryImp.gotoOrderEntry(MainActivity.this);
                    Toast.makeText(getApplicationContext(),result,Toast.LENGTH_LONG).show();

                }else{
                    Log.e(TAG, "onClick: did not find service iml");
                }
                break;
            case R.id.button2:
                Log.e(TAG, "onClick: "+MainActivity.class.getName());
                //resources -Meta-INF下的服务配置文件，后期应从手动改为apt等方式自动生成?
                Log.e(TAG, "onClick: module go shop click");
                ServiceLoader<IShopEntry> spEntry = ServiceLoader.load(IShopEntry.class);
                Iterator<IShopEntry> iteratorSp = spEntry.iterator();
                if (iteratorSp.hasNext()){
                    IShopEntry orderEntryImp = iteratorSp.next();
                    orderEntryImp.gotoShopEntry(MainActivity.this);

                }else{
                    Log.e(TAG, "onClick: did not find service iml");
                }
                break;
        }
    }
}
