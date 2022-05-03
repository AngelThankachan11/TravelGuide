package com.travel.guide.apicalls.presenter;

import android.app.Activity;
import android.widget.Toast;

import com.travel.guide.UTopperApp;
import com.travel.guide.apicalls.model.ExploreMoreResBean;
import com.travel.guide.apicalls.model.WalletResBean;
import com.travel.guide.apicalls.viewclass.IWalletView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WalletPresenter extends BasePresenter<IWalletView> {

    public void CallWalletData(final Activity activity, String type, String accesToken){

        getView().enableLoadingBar(activity ,true);

        UTopperApp.getInstance().getApiService().getWalletHistory(type, "Bearer "+accesToken)
                .enqueue(new Callback<WalletResBean>() {
                    @Override
                    public void onResponse(Call<WalletResBean> call, Response<WalletResBean> response) {
                        getView().enableLoadingBar(activity, false);
                        try {
                            if (!handleError(response, activity)) {
                                if (response.isSuccessful() && response.code() == 200) {
                                    assert response.body() != null;
                                    getView().onWalletHistorySuccess(response.body());

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
                    public void onFailure(Call<WalletResBean> call, Throwable t) {
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

    public void CallExploreMore(final Activity activity, String accesToken){

        getView().enableLoadingBar(activity ,true);

        UTopperApp.getInstance().getApiService().getExploreBanners("Bearer "+accesToken)
                .enqueue(new Callback<ExploreMoreResBean>() {
                    @Override
                    public void onResponse(Call<ExploreMoreResBean> call, Response<ExploreMoreResBean> response) {
                        getView().enableLoadingBar(activity, false);
                        try {
                            if (!handleError(response, activity)) {
                                if (response.isSuccessful() && response.code() == 200) {
                                    assert response.body() != null;
                                    getView().onExploreSuccess(response.body());

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
                    public void onFailure(Call<ExploreMoreResBean> call, Throwable t) {
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
