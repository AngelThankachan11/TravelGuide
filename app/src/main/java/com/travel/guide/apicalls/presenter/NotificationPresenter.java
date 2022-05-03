package com.travel.guide.apicalls.presenter;

import android.app.Activity;
import android.widget.Toast;

import com.travel.guide.UTopperApp;
import com.travel.guide.apicalls.model.BannerResBean;
import com.travel.guide.apicalls.model.NotificationResBean;
import com.travel.guide.apicalls.viewclass.INotificationView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NotificationPresenter extends BasePresenter<INotificationView> {

    public void getNotifications(final Activity context, String accessToken){
        getView().enableLoadingBar(context ,true);

        UTopperApp.getInstance().getApiService().getNotificationList("Bearer "+accessToken)
                .enqueue(new Callback<NotificationResBean>() {
                    @Override
                    public void onResponse(Call<NotificationResBean> call, Response<NotificationResBean> response) {
                        getView().enableLoadingBar(context, false);
                        try {
                            if (!handleError(response, context)) {
                                if (response.isSuccessful() && response.code() == 200) {
                                    assert response.body() != null;
                                    getView().onNotificationListSuccess(response.body());

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
                    public void onFailure(Call<NotificationResBean> call, Throwable t) {
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
}
