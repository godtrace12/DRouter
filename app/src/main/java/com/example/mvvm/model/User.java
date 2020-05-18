package com.example.mvvm.model;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.databinding.library.baseAdapters.BR;

public class User extends BaseObservable {
    private String name;
    @Bindable
    public String getName(){
        return this.name;
    }

    public void setName(String name){
        this.name = name;
        notifyPropertyChanged(BR.name);
    }
}
