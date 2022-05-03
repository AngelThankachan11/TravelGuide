package com.travel.guide.apicalls.presenter;

import android.app.Activity;
import android.widget.Toast;

import com.travel.guide.UTopperApp;
import com.travel.guide.apicalls.model.PropertyTypeResBean;
import com.travel.guide.apicalls.viewclass.IPropertyListView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PropertyListPresenter extends BasePresenter<IPropertyListView>{

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
                                    getView().onPropertyTypeSuccess(response.body());

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
}
