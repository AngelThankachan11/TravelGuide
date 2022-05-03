package com.travel.guide.apicalls.viewclass;

import com.travel.guide.apicalls.model.CouponAppliedResBean;
import com.travel.guide.apicalls.model.OfferResBean;

public interface ICouponListView extends IUtopperView{
    void onCouponListSuccess(OfferResBean item);
    void onCouponAppliedSuccess(CouponAppliedResBean item);
}
