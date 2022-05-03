package com.travel.guide.apicalls.presenter;

import android.app.Activity;
import android.widget.Toast;

import com.travel.guide.UTopperApp;
import com.travel.guide.apicalls.model.BannerResBean;
import com.travel.guide.apicalls.model.CouponAppliedResBean;
import com.travel.guide.apicalls.model.RoomBookingResBean;
import com.travel.guide.apicalls.viewclass.IBookOnlineView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BookOnlinePresenter extends BasePresenter<IBookOnlineView>{

    public void callRoomBooking(final Activity context, String propertyId, String userName, String date, String mobile, String roomId, String roomTypeId, String bookingType
            , String walletAmount, String couponCode, String couponAmount, String transactionId, String accessToken){

        getView().enableLoadingBar(context ,true);

        UTopperApp.getInstance().getApiService().saveRoomBooking(propertyId, userName, date, mobile, roomId, roomTypeId, bookingType, walletAmount, couponCode,
                couponAmount, transactionId, "Bearer "+accessToken)
                .enqueue(new Callback<RoomBookingResBean>() {
                    @Override
                    public void onResponse(Call<RoomBookingResBean> call, Response<RoomBookingResBean> response) {
                        getView().enableLoadingBar(context, false);
                        try {
                            if (!handleError(response, context)) {
                                if (response.isSuccessful() && response.code() == 200) {
                                    assert response.body() != null;
                                    getView().onBookOnlineSuccess(response.body());

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
                    public void onFailure(Call<RoomBookingResBean> call, Throwable t) {
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

    public void couponApplied(final Activity context, String couponCode, String amount, String accesToken){

        getView().enableLoadingBar(context ,true);

        UTopperApp.getInstance().getApiService().couponApplied(couponCode, amount, "Bearer "+accesToken)
                .enqueue(new Callback<CouponAppliedResBean>() {
                    @Override
                    public void onResponse(Call<CouponAppliedResBean> call, Response<CouponAppliedResBean> response) {
                        getView().enableLoadingBar(context, false);
                        try {
                            if (!handleError(response, context)) {
                                if (response.isSuccessful() && response.code() == 200) {
                                    assert response.body() != null;
                                    getView().onCouponAppliedSuccess(response.body());

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
                    public void onFailure(Call<CouponAppliedResBean> call, Throwable t) {
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
