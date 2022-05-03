package com.travel.guide.apicalls.presenter;

import android.app.Activity;
import android.widget.Toast;

import com.travel.guide.UTopperApp;
import com.travel.guide.apicalls.model.FaqResBean;
import com.travel.guide.apicalls.viewclass.IFaqView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FaqPresenter extends BasePresenter<IFaqView> {

    public void CallFAQData(final Activity activity, String accesToken){

        getView().enableLoadingBar(activity ,true);

        UTopperApp.getInstance().getApiService().getFaqList("Bearer "+accesToken)
                .enqueue(new Callback<FaqResBean>() {
                    @Override
                    public void onResponse(Call<FaqResBean> call, Response<FaqResBean> response) {
                        getView().enableLoadingBar(activity, false);
                        try {
                            if (!handleError(response, activity)) {
                                if (response.isSuccessful() && response.code() == 200) {
                                    assert response.body() != null;
                                    getView().onFaqSuccess(response.body());

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
                    public void onFailure(Call<FaqResBean> call, Throwable t) {
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

