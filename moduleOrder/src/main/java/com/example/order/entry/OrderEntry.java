package com.example.order.entry;

import android.content.Context;
import android.content.Intent;

import com.example.aninterface.IOrderEntry;
import com.example.order.OrderActivity;

public class OrderEntry implements IOrderEntry {
    @Override
    public String sayHello() {
        String helloWorld = "hello world from Module Order";
        return helloWorld;
    }

    @Override
    public void gotoOrderEntry(Context context){
        Intent intent = new Intent();
        intent.setClassName(context,OrderActivity.class.getName());
        context.startActivity(intent);
    }
}
