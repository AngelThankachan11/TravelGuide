package com.travel.guide;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.databinding.DataBindingUtil;

import com.travel.guide.databinding.ActivityLoginBinding;
import com.travel.guide.apicalls.model.LoginResBean;
import com.travel.guide.apicalls.presenter.LoginPresenter;
import com.travel.guide.apicalls.viewclass.ILoginView;
import com.travel.guide.utils.NetworkCheck;

public class LoginActivity extends BaseActivity implements View.OnClickListener, ILoginView {

    ActivityLoginBinding binding;
    String[] country = { "+91", "+45", "+23", "+95", "+92"};

    LoginPresenter presenter;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login);

        presenter = new LoginPresenter();
        presenter.setView(this);

        /*ArrayAdapter aa = new ArrayAdapter(this, R.layout.spin_drop_down_view,country){
            @Override
            public View getDropDownView(int position, View convertView, ViewGroup parent) {
                // TODO Auto-generated method stub

                View view = super.getView(position, convertView, parent);

                TextView text = (TextView)view.findViewById(R.id.txtSpinItem);
                text.setTextColor(getResources().getColor(R.color.app_color));

                return view;

            }};
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spinPreFix.setAdapter(aa);*/

        binding.imgLogin.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.imgLogin:
                if (binding.edtNumber.getText().toString().trim().isEmpty()){
                    toast("Please enter valid mobile number");
                }else if(NetworkCheck.isConnected(this)){
                    presenter.LoginCall(this , binding.edtNumber.getText().toString());
                }else {
                    NetworkCheck.showNetworkFailureAlert(this);
                }
                break;

            case R.id.consTerm:
                startActivity(new Intent(this, TermsConditionsActivity.class));
                break;
            default:
                break;
        }
    }

    @Override
    public void onLoginSucess(LoginResBean item) {
        if (item.isStatus()){
            Intent intent = new Intent(LoginActivity.this, OtpActivity.class);
            intent.putExtra("mobileNumber", binding.edtNumber.getText().toString());
            //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }else {
            if (item.getMessage().equalsIgnoreCase("User not exist")){
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                intent.putExtra("mobileNumber", binding.edtNumber.getText().toString());
                startActivity(intent);
            }
        }
        toast(item.getMessage());
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void onError(String reason) {

    }
}
