package com.example.moduleshop;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.router.annotation.Route;

//@Route("shop/ShopActivity")
public class ShopActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);
    }
}
