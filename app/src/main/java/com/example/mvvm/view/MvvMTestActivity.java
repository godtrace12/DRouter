package com.example.mvvm.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.mvvm.viewmodel.CounterViewModel;
import com.example.router.R;

public class MvvMTestActivity extends AppCompatActivity{
    private TextView textResult;
    private Button btnAdd;
    private CounterViewModel mViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mvv_mtest);
        findViews();
        initViewModel();
    }

    private void initViewModel() {
//        mViewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication()).create()
        mViewModel = ViewModelProviders.of(this).get(CounterViewModel.class);
        mViewModel.countData.observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                textResult.setText(""+integer);
            }
        });

    }

    private void findViews() {
        textResult = findViewById(R.id.text_result);
        btnAdd = findViewById(R.id.btn_add);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewModel.add();
            }
        });
    }
}
