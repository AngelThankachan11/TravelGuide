package com.travel.guide.apicalls.viewclass;

import com.travel.guide.apicalls.model.AddWishListResBean;
import com.travel.guide.apicalls.model.CouponAppliedResBean;
import com.travel.guide.apicalls.model.MessDetailsResBean;
import com.travel.guide.apicalls.model.PropertyDetailsResBean;
import com.travel.guide.apicalls.model.RoomBookingResBean;
import com.travel.guide.apicalls.model.RoomDetailsResBean;

public interface IPropertyDetailsView extends IUtopperView{
    void onPropertyDetailsSuccess(PropertyDetailsResBean item);
    void onRoomDetailsSuccess(RoomDetailsResBean item);
    void onMessDetailsSuccess(MessDetailsResBean item);
    void onRoomBookingSuccess(RoomBookingResBean item);
    void addWishListSuccess(AddWishListResBean item);
    void onCouponAppliedSuccess(CouponAppliedResBean item);
}


