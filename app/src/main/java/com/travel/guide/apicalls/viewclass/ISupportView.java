package com.travel.guide.apicalls.viewclass;

import com.travel.guide.apicalls.model.BannerResBean;
import com.travel.guide.apicalls.model.FaqResBean;
import com.travel.guide.apicalls.model.RequestCallbackResBean;
import com.travel.guide.apicalls.model.ServiceableCityResBean;

public interface ISupportView extends IUtopperView{
    void onBannerSuccess(BannerResBean item);
    void onFaqSuccess(FaqResBean item);
    void onCitySucess(ServiceableCityResBean item);
    void onRequestCallbackSuccess(RequestCallbackResBean item);
}