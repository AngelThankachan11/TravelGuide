package com.travel.guide.apicalls.presenter;

import android.app.Activity;
import android.widget.Toast;

import com.travel.guide.UTopperApp;
import com.travel.guide.apicalls.model.AreaListResBean;
import com.travel.guide.apicalls.model.SearchByLPropertyResBean;
import com.travel.guide.apicalls.model.ServiceableCityResBean;
import com.travel.guide.apicalls.viewclass.ISearchByLView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchByLocationPresenter extends BasePresenter<ISearchByLView>{

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

    public void CallSearchByLocation(final Activity activity, String cityId, String areaId, String propertTypeId, String rangeId, String accessToken){
        getView().enableLoadingBar(activity ,true);

        UTopperApp.getInstance().getApiService().searchByLocation(cityId, areaId, propertTypeId, rangeId, "Bearer "+accessToken)
                .enqueue(new Callback<SearchByLPropertyResBean>() {
                    @Override
                    public void onResponse(Call<SearchByLPropertyResBean> call, Response<SearchByLPropertyResBean> response) {
                        getView().enableLoadingBar(activity, false);
                        try {
                            if (!handleError(response, activity)) {
                                if (response.isSuccessful() && response.code() == 200) {
                                    assert response.body() != null;
                                    getView().onSearchByLSuccess(response.body());

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
                    public void onFailure(Call<SearchByLPropertyResBean> call, Throwable t) {
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

    public void callAreaList(final Activity context, String cityId, String accessToken){
        getView().enableLoadingBar(context ,true);

        UTopperApp.getInstance().getApiService().getAreaList(cityId, "Bearer "+accessToken)
                .enqueue(new Callback<AreaListResBean>() {
                    @Override
                    public void onResponse(Call<AreaListResBean> call, Response<AreaListResBean> response) {
                        getView().enableLoadingBar(context, false);
                        try {
                            if (!handleError(response, context)) {
                                if (response.isSuccessful() && response.code() == 200) {
                                    assert response.body() != null;
                                    getView().onAreaListSuccess(response.body());

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
                    public void onFailure(Call<AreaListResBean> call, Throwable t) {
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

