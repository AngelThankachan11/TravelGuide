package com.travel.guide.apicalls.presenter;

import android.app.Activity;
import android.widget.Toast;

import com.travel.guide.UTopperApp;
import com.travel.guide.apicalls.model.DistanceRangeResBean;
import com.travel.guide.apicalls.model.InstituteListResBean;
import com.travel.guide.apicalls.model.SearchByInsResBean;
import com.travel.guide.apicalls.model.ServiceableCityResBean;
import com.travel.guide.apicalls.viewclass.ISearchByInstituteView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchByInstitutePresenter extends BasePresenter<ISearchByInstituteView>{

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

    public void getDistanceRange(final Activity context, String accessToken){
        getView().enableLoadingBar(context ,true);

        UTopperApp.getInstance().getApiService().getDistanceRangeList("Bearer "+accessToken)
                .enqueue(new Callback<DistanceRangeResBean>() {
                    @Override
                    public void onResponse(Call<DistanceRangeResBean> call, Response<DistanceRangeResBean> response) {
                        getView().enableLoadingBar(context, false);
                        try {
                            if (!handleError(response, context)) {
                                if (response.isSuccessful() && response.code() == 200) {
                                    assert response.body() != null;
                                    getView().onDistanceRangeSuccess(response.body());

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
                    public void onFailure(Call<DistanceRangeResBean> call, Throwable t) {
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

    public void CallIntituteList(final Activity activity, String cityId, String accessToken){
        getView().enableLoadingBar(activity ,true);

        UTopperApp.getInstance().getApiService().getIntituteList(cityId, "Bearer "+accessToken)
                .enqueue(new Callback<InstituteListResBean>() {
                    @Override
                    public void onResponse(Call<InstituteListResBean> call, Response<InstituteListResBean> response) {
                        getView().enableLoadingBar(activity, false);
                        try {
                            if (!handleError(response, activity)) {
                                if (response.isSuccessful() && response.code() == 200) {
                                    assert response.body() != null;
                                    getView().onInstituteListSuccess(response.body());

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
                    public void onFailure(Call<InstituteListResBean> call, Throwable t) {
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

    public void CallSearchByInstitute(final Activity activity, String propertyTypeId, String rangeId, String cityId, String instituteId, String branchId, String accessToken){
        getView().enableLoadingBar(activity ,true);

        UTopperApp.getInstance().getApiService().searchByInstitute(propertyTypeId, rangeId, cityId, instituteId, branchId, "Bearer "+accessToken)
                .enqueue(new Callback<SearchByInsResBean>() {
                    @Override
                    public void onResponse(Call<SearchByInsResBean> call, Response<SearchByInsResBean> response) {
                        getView().enableLoadingBar(activity, false);
                        try {
                            if (!handleError(response, activity)) {
                                if (response.isSuccessful() && response.code() == 200) {
                                    assert response.body() != null;
                                    getView().onSearchByInsSuccess(response.body());

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
                    public void onFailure(Call<SearchByInsResBean> call, Throwable t) {
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
