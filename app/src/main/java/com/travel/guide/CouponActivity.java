package com.travel.guide;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.travel.guide.databinding.ActivityCouponBinding;
import com.travel.guide.adapters.CouponAdapter;
import com.travel.guide.apicalls.model.CouponAppliedResBean;
import com.travel.guide.apicalls.model.OfferResBean;
import com.travel.guide.apicalls.presenter.CouponPresenter;
import com.travel.guide.apicalls.viewclass.ICouponListView;
import com.travel.guide.utils.SharedPreferenceData;

import java.util.ArrayList;
import java.util.List;

public class CouponActivity extends BaseActivity implements ICouponListView, CouponAdapter.ItemClickListener {

    ActivityCouponBinding binding;
    CouponAdapter couponAdapter;
    List<OfferResBean.DataItem> couponList = new ArrayList<>();

    CouponPresenter presenter;
    SharedPreferenceData userData;
    String couponCode;
    String rentAmount;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_coupon);
        presenter = new CouponPresenter();
        presenter.setView(this);
        userData = new SharedPreferenceData(this);
        rentAmount = getIntent().getStringExtra("amount");

        couponAdapter = new CouponAdapter(this, couponList, this);
        GridLayoutManager gridLayoutManager2 = new GridLayoutManager(this, 1, LinearLayoutManager.VERTICAL, false);
        binding.recyCouponList.setLayoutManager(gridLayoutManager2);
        binding.recyCouponList.setItemAnimator(new DefaultItemAnimator());
        binding.recyCouponList.setAdapter(couponAdapter);

        presenter.getCouponList(this, userData.getACCESS_TOKEN());
    }

    public void onClick(View view){
        switch (view.getId()){
            case R.id.imgBack:
                finish();
                break;
            case R.id.txtApply:
                if (!binding.txtCouponCode.getText().toString().trim().isEmpty()) {
                    presenter.couponApplied(this, binding.txtCouponCode.getText().toString(), rentAmount, userData.getACCESS_TOKEN());
                }else {
                    toast("Please enter a valid coupon code");
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void OnCouponClicked(int position) {
        couponCode = couponList.get(position).getCouponCode();
        binding.txtCouponCode.setText(""+couponCode);
    }

    @Override
    public void onCouponListSuccess(OfferResBean item) {

        couponList.clear();
        if (item.isStatus()){
            couponList.addAll(item.getData());
        }else {
            toast("No Coupon list found");
        }

        couponAdapter.notifyDataSetChanged();
    }

    @Override
    public void onCouponAppliedSuccess(CouponAppliedResBean item) {
        if (item.isStatus()){
            Intent returnIntent = new Intent();
            returnIntent.putExtra("couponCode",binding.txtCouponCode.getText().toString());
            returnIntent.putExtra("couponAmount",""+item.getMinusAmount());
            setResult(Activity.RESULT_OK,returnIntent);
            finish();
        }else {
            toast(item.getMessage());
        }
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void onError(String reason) {
        toast(reason);
    }
}
