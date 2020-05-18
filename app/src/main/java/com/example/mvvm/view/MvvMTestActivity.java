package com.example.mvvm.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.mvvm.model.User;
import com.example.mvvm.viewmodel.CounterViewModel;
import com.example.mvvm.viewmodel.CounterViewModel2;
import com.example.router.BR;
import com.example.router.R;
import com.example.router.databinding.ActivityMvvMtestBinding;

public class MvvMTestActivity extends AppCompatActivity{
    private static final String TAG = "MvvMTestActivity";
    private TextView textResult;
    private Button btnAdd;

    private EditText textResult2;
    private Button btnAdd2;

    private CounterViewModel mViewModel;
    private CounterViewModel2 mViewModel2;
    private User user = new User();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_mvv_mtest);
        ActivityMvvMtestBinding activityMain2Binding = DataBindingUtil.setContentView(this, R.layout.activity_mvv_mtest);
//        activityMain2Binding.setCountModel2(mViewModel2);
        initViewModel();
        activityMain2Binding.setVariable(BR.countModel2,mViewModel2);
        activityMain2Binding.setUser(user);
        activityMain2Binding.setLifecycleOwner(this);
        activityMain2Binding.executePendingBindings();
//        activityMain2Binding.setCountModel2(mViewModel2);
        findViews();
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

        mViewModel2 = ViewModelProviders.of(this).get(CounterViewModel2.class);
        mViewModel2.countData.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String integer) {
                textResult2.setText(""+integer);
            }
        });

    }

    private void findViews() {
        textResult = findViewById(R.id.text_result);
        btnAdd = findViewById(R.id.btn_add);

        textResult2 = findViewById(R.id.tv_Result2);
        btnAdd2 = findViewById(R.id.btn_add2);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e(TAG, "onClick: usrName="+user.getName());
                mViewModel.add();
                user.setName(user.getName()+"1");
            }
        });

//        btnAdd2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                mViewModel2.add();
//            }
//        });

    }
}
