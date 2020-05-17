package com.example.mvvm.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class CounterViewModel extends ViewModel {
    public MutableLiveData<Integer> countData = new MutableLiveData<>();
    private Integer count = 0;

    public void add(){
        count++;
        countData.postValue(count);
    }
}
