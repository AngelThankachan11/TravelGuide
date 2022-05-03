package com.travel.guide.apicalls.presenter;

import android.app.Activity;
import android.widget.Toast;

import com.travel.guide.UTopperApp;
import com.travel.guide.apicalls.model.BannerResBean;
import com.travel.guide.apicalls.model.BookingDetailsResBean;
import com.travel.guide.apicalls.model.ExploreMoreResBean;
import com.travel.guide.apicalls.model.MyBookingsResBean;
import com.travel.guide.apicalls.model.PropertyTypeResBean;
import com.travel.guide.apicalls.viewclass.IMyBookingView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BookingPresenter extends BasePresenter<IMyBookingView> {

    public void getBanners(final Activity context, String type, String accessToken){
        getView().enableLoadingBar(context ,false);

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

    public void getPropertyType(final Activity context, String accesToken){

        getView().enableLoadingBar(context ,true);

        UTopperApp.getInstance().getApiService().getPropertyType("Bearer "+accesToken)
                .enqueue(new Callback<PropertyTypeResBean>() {
                    @Override
                    public void onResponse(Call<PropertyTypeResBean> call, Response<PropertyTypeResBean> response) {
                        getView().enableLoadingBar(context, false);
                        try {
                            if (!handleError(response, context)) {
                                if (response.isSuccessful() && response.code() == 200) {
                                    assert response.body() != null;
                                    getView().onPropertyTypeSucess(response.body());

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
                    public void onFailure(Call<PropertyTypeResBean> call, Throwable t) {
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

    public void CallMyBookings(final Activity context, String accesToken){

        getView().enableLoadingBar(context ,true);

        UTopperApp.getInstance().getApiService().getBookingList("Bearer "+accesToken)
                .enqueue(new Callback<MyBookingsResBean>() {
                    @Override
                    public void onResponse(Call<MyBookingsResBean> call, Response<MyBookingsResBean> response) {
                        getView().enableLoadingBar(context, false);
                        try {
                            if (!handleError(response, context)) {
                                if (response.isSuccessful() && response.code() == 200) {
                                    assert response.body() != null;
                                    getView().onBookingListSucess(response.body());

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
                    public void onFailure(Call<MyBookingsResBean> call, Throwable t) {
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

    public void CallBookingDetails(final Activity activity, String bookingId, String accesToken){

        getView().enableLoadingBar(activity ,true);

        UTopperApp.getInstance().getApiService().getBookingDetails(bookingId, "Bearer "+accesToken)
                .enqueue(new Callback<BookingDetailsResBean>() {
                    @Override
                    public void onResponse(Call<BookingDetailsResBean> call, Response<BookingDetailsResBean> response) {
                        getView().enableLoadingBar(activity, false);
                        try {
                            if (!handleError(response, activity)) {
                                if (response.isSuccessful() && response.code() == 200) {
                                    assert response.body() != null;
                                    getView().onBookingDetailsSuccess(response.body());

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
                    public void onFailure(Call<BookingDetailsResBean> call, Throwable t) {
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
