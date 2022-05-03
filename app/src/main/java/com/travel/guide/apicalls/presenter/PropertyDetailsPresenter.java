package com.travel.guide.apicalls.presenter;

import android.app.Activity;
import android.widget.Toast;

import com.travel.guide.UTopperApp;
import com.travel.guide.apicalls.model.AddWishListResBean;
import com.travel.guide.apicalls.model.CouponAppliedResBean;
import com.travel.guide.apicalls.model.MessDetailsResBean;
import com.travel.guide.apicalls.model.PropertyDetailsResBean;
import com.travel.guide.apicalls.model.RoomBookingResBean;
import com.travel.guide.apicalls.model.RoomDetailsResBean;
import com.travel.guide.apicalls.viewclass.IPropertyDetailsView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PropertyDetailsPresenter extends BasePresenter<IPropertyDetailsView> {

    public void getPropertyDetails(final Activity context, String propertyId, String accessToken){
        getView().enableLoadingBar(context ,true);

        UTopperApp.getInstance().getApiService().getPropertyDetails(propertyId, "Bearer "+accessToken)
                .enqueue(new Callback<PropertyDetailsResBean>() {
                    @Override
                    public void onResponse(Call<PropertyDetailsResBean> call, Response<PropertyDetailsResBean> response) {
                        getView().enableLoadingBar(context, false);
                        try {
                            if (!handleError(response, context)) {
                                if (response.isSuccessful() && response.code() == 200) {
                                    assert response.body() != null;
                                    getView().onPropertyDetailsSuccess(response.body());

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
                    public void onFailure(Call<PropertyDetailsResBean> call, Throwable t) {
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

    public void getRoomDetails(final Activity context, String propertyId, String accessToken){
        getView().enableLoadingBar(context ,true);

        UTopperApp.getInstance().getApiService().getRoomDetails(propertyId, "Bearer "+accessToken)
                .enqueue(new Callback<RoomDetailsResBean>() {
                    @Override
                    public void onResponse(Call<RoomDetailsResBean> call, Response<RoomDetailsResBean> response) {
                        getView().enableLoadingBar(context, false);
                        try {
                            if (!handleError(response, context)) {
                                if (response.isSuccessful() && response.code() == 200) {
                                    assert response.body() != null;
                                    getView().onRoomDetailsSuccess(response.body());

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
                    public void onFailure(Call<RoomDetailsResBean> call, Throwable t) {
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

    public void getMessDetails(final Activity context, String propertyId, String accessToken){
        getView().enableLoadingBar(context ,true);

        UTopperApp.getInstance().getApiService().getMessDetails(propertyId, "Bearer "+accessToken)
                .enqueue(new Callback<MessDetailsResBean>() {
                    @Override
                    public void onResponse(Call<MessDetailsResBean> call, Response<MessDetailsResBean> response) {
                        getView().enableLoadingBar(context, false);
                        try {
                            if (!handleError(response, context)) {
                                if (response.isSuccessful() && response.code() == 200) {
                                    assert response.body() != null;
                                    getView().onMessDetailsSuccess(response.body());

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
                    public void onFailure(Call<MessDetailsResBean> call, Throwable t) {
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

    public void callRoomBooking(final Activity context, String propertyId, String userName, String date, String mobile, String roomId, String roomTypeID, String bookingType
            , String walletAmount, String couponCode, String couponAmount, String transactionId, String accessToken){

        getView().enableLoadingBar(context ,true);

        UTopperApp.getInstance().getApiService().saveRoomBooking(propertyId, userName, date, mobile, roomId, roomTypeID, bookingType, walletAmount, couponCode, couponAmount,
                transactionId, "Bearer "+accessToken)
                .enqueue(new Callback<RoomBookingResBean>() {
                    @Override
                    public void onResponse(Call<RoomBookingResBean> call, Response<RoomBookingResBean> response) {
                        getView().enableLoadingBar(context, false);
                        try {
                            if (!handleError(response, context)) {
                                if (response.isSuccessful() && response.code() == 200) {
                                    assert response.body() != null;
                                    getView().onRoomBookingSuccess(response.body());

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

    public void addToWishList(final Activity context, String propertyId, String accessToken){
        getView().enableLoadingBar(context ,true);

        UTopperApp.getInstance().getApiService().addWishList(propertyId, "Bearer "+accessToken)
                .enqueue(new Callback<AddWishListResBean>() {
                    @Override
                    public void onResponse(Call<AddWishListResBean> call, Response<AddWishListResBean> response) {
                        getView().enableLoadingBar(context, false);
                        try {
                            if (!handleError(response, context)) {
                                if (response.isSuccessful() && response.code() == 200) {
                                    assert response.body() != null;
                                    getView().addWishListSuccess(response.body());

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
                    public void onFailure(Call<AddWishListResBean> call, Throwable t) {
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

