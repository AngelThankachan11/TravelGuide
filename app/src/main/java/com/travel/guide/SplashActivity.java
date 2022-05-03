package com.travel.guide;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.databinding.DataBindingUtil;

import com.travel.guide.utils.SharedPreferenceData;

import java.util.Map;

public class SplashActivity extends BaseActivity{

    private static int TIME_OUT = 2000;

    @Override
    public void onCreate(Bundle savedInstanteState){
        super.onCreate(savedInstanteState);
        DataBindingUtil.setContentView(this, R.layout.activity_splash);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if ((new SharedPreferenceData(SplashActivity.this).IsLogin())){
                    Intent i = new Intent(SplashActivity.this, MapActivity.class);
                    startActivity(i);
                    finish();
                }else {
                    Intent i = new Intent(SplashActivity.this, LoginActivity.class);
                    startActivity(i);
                    finish();
                }
            }
        }, TIME_OUT);
    }
}
