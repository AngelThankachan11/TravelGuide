package com.travel.guide;

import android.os.Bundle;
import android.view.View;

import androidx.databinding.DataBindingUtil;

import com.travel.guide.databinding.ActivityTermsconditionsBinding;

public class TermsConditionsActivity extends BaseActivity{

    ActivityTermsconditionsBinding binding;
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_termsconditions);
    }

    public void onClick(View view){
        switch (view.getId()){
            case R.id.imgBack:
                finish();
                break;
            default:
                break;
        }
    }
}
