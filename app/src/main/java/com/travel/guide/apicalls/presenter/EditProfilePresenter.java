package com.travel.guide.apicalls.presenter;

import android.app.Activity;
import android.widget.Toast;

import com.travel.guide.UTopperApp;
import com.travel.guide.apicalls.model.EditProfileResBean;
import com.travel.guide.apicalls.viewclass.IEditProfileView;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditProfilePresenter extends BasePresenter<IEditProfileView>{

    public void editProfileInfo(final Activity context, String name, String email, String mobile, String day,
                            String month, String year, MultipartBody.Part image, String accesToken){

        getView().enableLoadingBar(context ,true);

        RequestBody reqName = RequestBody.create(MediaType.parse("multipart/form-data"), name);
        RequestBody reqEmail = RequestBody.create(MediaType.parse("multipart/form-data"), email);
        RequestBody reqMobile = RequestBody.create(MediaType.parse("multipart/form-data"), mobile);
        RequestBody reqDay = RequestBody.create(MediaType.parse("multipart/form-data"), day);
        RequestBody reqMonth = RequestBody.create(MediaType.parse("multipart/form-data"), month);
        RequestBody reqYear = RequestBody.create(MediaType.parse("multipart/form-data"), year);

        UTopperApp.getInstance().getApiService().editProfile(reqName, reqEmail, reqMobile, reqDay, reqMonth, reqYear,
                image, "Bearer "+accesToken)
                .enqueue(new Callback<EditProfileResBean>() {
                    @Override
                    public void onResponse(Call<EditProfileResBean> call, Response<EditProfileResBean> response) {
                        getView().enableLoadingBar(context, false);
                        try {
                            if (!handleError(response, context)) {
                                if (response.isSuccessful() && response.code() == 200) {
                                    assert response.body() != null;
                                    getView().onEditProfileSuccess(response.body());

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
                    public void onFailure(Call<EditProfileResBean> call, Throwable t) {
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
