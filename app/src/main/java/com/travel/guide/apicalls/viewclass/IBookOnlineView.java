package com.travel.guide.apicalls.viewclass;

import com.travel.guide.apicalls.model.BannerResBean;
import com.travel.guide.apicalls.model.CouponAppliedResBean;
import com.travel.guide.apicalls.model.RoomBookingResBean;

public interface IBookOnlineView extends IUtopperView{
    void onBookOnlineSuccess(RoomBookingResBean item);
    void onBannerSuccess(BannerResBean item);
    void onCouponAppliedSuccess(CouponAppliedResBean item);
}
