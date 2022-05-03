package com.travel.guide.apicalls.presenter;

import android.app.Activity;
import android.widget.Toast;

import com.travel.guide.UTopperApp;
import com.travel.guide.apicalls.model.BannerResBean;
import com.travel.guide.apicalls.model.FaqResBean;
import com.travel.guide.apicalls.model.RequestCallbackResBean;
import com.travel.guide.apicalls.model.ServiceableCityResBean;
import com.travel.guide.apicalls.viewclass.ISupportView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SupportPresenter extends BasePresenter<ISupportView> {

    public void getBanners(final Activity context, String type, String accessToken){
        getView().enableLoadingBar(context ,true);

        UTopperApp.getInstance().getApiService().getBanners(type, "Bearer "+accessToken)
                .enqueue(new Callback<BannerResBean>() {
                    @Override
                    public void onResponse(Call<BannerResBean> call, Response<BannerResBean> response) {
                        getView().enableLoadingBar(context, false);
                        try {
                            if (!handleError(response, context)) {
                                if (response.isSuccessful() && response.code() == 200) {
                                    assert response.body() != null;
                                    getView().onBannerSuccess(response.body());

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
                    public void onFailure(Call<BannerResBean> call, Throwable t) {
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

    public void CityCall(final Activity activity, String accessToken){
        getView().enableLoadingBar(activity ,true);

        UTopperApp.getInstance().getApiService().getServiceableCityList("Bearer "+accessToken)
                .enqueue(new Callback<ServiceableCityResBean>() {
                    @Override
                    public void onResponse(Call<ServiceableCityResBean> call, Response<ServiceableCityResBean> response) {
                        getView().enableLoadingBar(activity, false);
                        try {
                            if (!handleError(response, activity)) {
                                if (response.isSuccessful() && response.code() == 200) {
                                    assert response.body() != null;
                                    getView().onCitySucess(response.body());

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
                    public void onFailure(Call<ServiceableCityResBean> call, Throwable t) {
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

    public void CallRequestCallback(final Activity context,String mobile, String cityId, String accesToken){

        getView().enableLoadingBar(context ,true);

        UTopperApp.getInstance().getApiService().sendRequestCallback(mobile, cityId, "Bearer "+accesToken)
                .enqueue(new Callback<RequestCallbackResBean>() {
                    @Override
                    public void onResponse(Call<RequestCallbackResBean> call, Response<RequestCallbackResBean> response) {
                        getView().enableLoadingBar(context, false);
                        try {
                            if (!handleError(response, context)) {
                                if (response.isSuccessful() && response.code() == 200) {
                                    assert response.body() != null;
                                    getView().onRequestCallbackSuccess(response.body());

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
                    public void onFailure(Call<RequestCallbackResBean> call, Throwable t) {
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
