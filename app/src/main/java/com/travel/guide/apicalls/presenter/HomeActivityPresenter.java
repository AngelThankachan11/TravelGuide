package com.travel.guide.apicalls.presenter;

import android.app.Activity;
import android.widget.Toast;

import com.travel.guide.UTopperApp;
import com.travel.guide.apicalls.model.OfflinePaynowResBean;
import com.travel.guide.apicalls.viewclass.IHomeActivityView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeActivityPresenter extends BasePresenter<IHomeActivityView>{

    public void CallOfflineBookingPay(final Activity activity, String bookingId, String transactionId, String accesToken){

        getView().enableLoadingBar(activity ,true);

        UTopperApp.getInstance().getApiService().offlinePayNow(bookingId, transactionId, "Bearer "+accesToken)
                .enqueue(new Callback<OfflinePaynowResBean>() {
                    @Override
                    public void onResponse(Call<OfflinePaynowResBean> call, Response<OfflinePaynowResBean> response) {
                        getView().enableLoadingBar(activity, false);
                        try {
                            if (!handleError(response, activity)) {
                                if (response.isSuccessful() && response.code() == 200) {
                                    assert response.body() != null;
                                    getView().onOfflinePaynowSuccess(response.body());

                                }else {
                                    Toast.makeText(activity, response.message(), Toast.LENGTH_LONG).show();
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
                    public void onFailure(Call<OfflinePaynowResBean> call, Throwable t) {
                        try {
                            getView().enableLoadingBar(activity, false);
                            t.printStackTrace();
                            getView().onError(null);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
    }
}
