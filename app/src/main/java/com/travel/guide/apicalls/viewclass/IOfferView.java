package com.travel.guide.apicalls.viewclass;

import com.travel.guide.apicalls.model.BannerResBean;
import com.travel.guide.apicalls.model.OfferResBean;

public interface IOfferView extends IUtopperView{
    void onBannerSuccess(BannerResBean item);
    void onOfferSuccess(OfferResBean item);
}