package com.travel.guide;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.databinding.DataBindingUtil;

public class LauncherActivity extends BaseActivity{

    private static int TIME_OUT = 1000;

    @Override
    public void onCreate(Bundle savedInstanteState){
        super.onCreate(savedInstanteState);
        DataBindingUtil.setContentView(this, R.layout.activity_launcher);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(LauncherActivity.this, SplashActivity.class);
                startActivity(i);
                finish();
            }
        }, TIME_OUT);
    }
}

