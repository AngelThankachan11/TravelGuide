package com.travel.guide;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.travel.guide.databinding.ActivityRegisterBinding;
import com.travel.guide.adapters.CityAdapter;
import com.travel.guide.apicalls.model.RegisterResBean;
import com.travel.guide.apicalls.model.ServiceableCityResBean;
import com.travel.guide.apicalls.model.StateResBean;
import com.travel.guide.apicalls.presenter.RegisterPresenter;
import com.travel.guide.apicalls.viewclass.IRegisterView;
import com.travel.guide.utils.NetworkCheck;

import java.util.ArrayList;

public class RegisterActivity extends BaseActivity implements View.OnClickListener, IRegisterView, CityAdapter.ItemClickListener {

    ActivityRegisterBinding binding;
    RegisterPresenter presenter;
    ArrayList<String> stateArray = new ArrayList<>();
    ArrayList<StateResBean.DataItem> stateList = new ArrayList<>();
    ArrayList<String> cityArray = new ArrayList<>();
    ArrayList<ServiceableCityResBean.DataItem> cityList = new ArrayList<>();
    private ArrayAdapter bindingAdapter;
    private String selectedStateId="33";
    private String selectedCityId=null;
    private String mobileNumber ;
    private CityAdapter cityAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_register);
        presenter = new RegisterPresenter();
        presenter.setView(this);

        init();
        mobileNumber = getIntent().getStringExtra("mobileNumber");
        binding.edtPhone.setText(mobileNumber);
        binding.edtPhone.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {}

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                if(s.length() > 0)
                    binding.txtMandatory.setVisibility(View.GONE);

            }
        });
    }

    private void init(){

        cityAdapter = new CityAdapter(this, cityList, this);
        GridLayoutManager gridLayoutManager1 = new GridLayoutManager(getContext(), 2, LinearLayoutManager.VERTICAL, false);
        binding.recyCity.setLayoutManager(gridLayoutManager1);
        binding.recyCity.setItemAnimator(new DefaultItemAnimator());
        binding.recyCity.setAdapter(cityAdapter);
    }

    public void onClick(View v){
        switch (v.getId()){
            case R.id.txtRegister:
                if(binding.edtFullName.getText().toString().trim().isEmpty()){
                    toast("Please enter user name");
                }else  if(binding.edtPhone.getText().toString().isEmpty()){
                    toast("Please enter mobile number");
                    binding.txtMandatory.setVisibility(View.VISIBLE);
                }/*else  if(binding.edtPhone.getText().toString().length()<10){
                    toast("Please enter valid mobile number");
                }else  if(binding.edtEmail.getText().toString().trim().isEmpty() || !isValidEmail(binding.edtEmail.getText())){
                    toast("Please enter email");
                }*//*else  if(selectedStateId==null){
                    toast("Please select state");
                }*/else  if(selectedCityId==null){
                    toast("Please select city");
                }/*else  if(binding.edtAddress.getText().toString().trim().isEmpty()){
                    toast("Please enter address");
                }*/else if(NetworkCheck.isConnected(this)){
                    /*presenter.RegisterCall(this , binding.edtFullName.getText().toString(), binding.edtMobile.getText().toString()
                            , binding.edtEmail.getText().toString(), selectedStateId, selectedCityId, binding.edtAddress.getText().toString());*/
                presenter.RegisterCall(this, binding.edtFullName.getText().toString() , mobileNumber
                        , binding.edtEmail.getText().toString(), binding.edtReferral.getText().toString().trim(), selectedCityId);
                }else {
                    NetworkCheck.showNetworkFailureAlert(this);
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void OnCityClicked(int position) {
        for (int i=0; i<cityList.size(); i++){
            cityList.get(i).setIsSelected(false);
        }

        if (!cityList.get(position).getIsSelected()){
            cityList.get(position).setIsSelected(true);
            selectedCityId = ""+cityList.get(position).getId();
        }else {
            cityList.get(position).setIsSelected(false);
            selectedCityId = null;
        }

        binding.recyCity.post(new Runnable() {
            @Override
            public void run() {
                cityAdapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public void onResume(){
        super.onResume();
        presenter.CityCall(RegisterActivity.this, selectedStateId);
    }

    @Override
    public void onStateSucess(StateResBean item) {
        if (item.isStatus()){
            stateList.clear();
            stateArray.clear();
            stateArray.add("Select State");
            stateList.addAll(item.getData());
            for (int i=0; i<item.getData().size(); i++){
                stateArray.add(item.getData().get(i).getName());
            }
            bindingAdapter.notifyDataSetChanged();
        }else {
            toast("State Api Status false");
        }
    }

    @Override
    public void onCitySucess(ServiceableCityResBean item) {
        if (item.isStatus()){
            cityList.clear();
            cityList.addAll(item.getData());
            cityAdapter.notifyDataSetChanged();
        }else {
            toast("City Api Status false");
        }
    }

    @Override
    public void onRegisterSucess(RegisterResBean item) {
        if (item.isStatus()){
            if (item.getVerifyOtpStatus()!=null) {
                if (item.getVerifyOtpStatus().equalsIgnoreCase("1")) {
                    Intent intent = new Intent(this, LoginActivity.class);
                    intent.putExtra("mobileNumber", mobileNumber);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(this, OtpActivity.class);
                    intent.putExtra("mobileNumber", mobileNumber);
                    startActivity(intent);
                }
            }else {
                Intent intent = new Intent(this, OtpActivity.class);
                intent.putExtra("mobileNumber", mobileNumber);
                startActivity(intent);
            }
        }
        toast(item.getMessage());
    }

    @Override
    public Context getContext() {
        return null;
    }

    @Override
    public void onError(String reason) {

    }
}
