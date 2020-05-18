package com.example.mvvm.viewmodel;

import android.util.Log;

import androidx.databinding.Bindable;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class CounterViewModel2 extends ViewModel {
    private static final String TAG = "CounterViewModel2";
    public MutableLiveData<String> countData = new MutableLiveData<>();
    public String count = 0+"";

    public void add(){
        count +="1";
        Log.e(TAG, "add: count="+count);
        countData.postValue(count);
    }
}
