package com.travel.guide.apicalls.presenter;

import android.app.Activity;
import android.widget.Toast;

import com.travel.guide.UTopperApp;
import com.travel.guide.apicalls.model.VerifyOtpResBean;
import com.travel.guide.apicalls.viewclass.IVerifyOtpView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VerifyOtpPresenter extends BasePresenter<IVerifyOtpView> {

    public void VerifyOtpCall(final Activity context, String mobile, String deviceKey, String otp){
        getView().enableLoadingBar(context ,true);

        UTopperApp.getInstance().getApiService().verifyOTP(mobile, deviceKey, otp)
                .enqueue(new Callback<VerifyOtpResBean>() {
                    @Override
                    public void onResponse(Call<VerifyOtpResBean> call, Response<VerifyOtpResBean> response) {
                        getView().enableLoadingBar(context, false);
                        try {
                            if (!handleError(response, context)) {
                                if (response.isSuccessful() && response.code() == 200) {
                                    assert response.body() != null;
                                    getView().onVerifySucess(response.body());

                                }else {
                                    Toast.makeText(context, response.message(), Toast.LENGTH_LONG).show();
                                }
                            }
                            else {
                                getView().onError(null);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            getView().onError(e.getMessage());
                        }
                    }

                    @Override
                    public void onFailure(Call<VerifyOtpResBean> call, Throwable t) {
                        try {
                            getView().enableLoadingBar(context, false);
                            t.printStackTrace();
                            getView().onError(null);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
    }
}

