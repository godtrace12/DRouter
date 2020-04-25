package com.example.moduleshop.entry;

import android.content.Context;
import android.content.Intent;

import com.example.aninterface.IShopEntry;
import com.example.moduleshop.ShopActivity;

public class ShopEntry implements IShopEntry {
    @Override
    public void gotoShopEntry(Context context) {
        Intent intent = new Intent();
        intent.setClassName(context, ShopActivity.class.getName());
        context.startActivity(intent);
    }
}
