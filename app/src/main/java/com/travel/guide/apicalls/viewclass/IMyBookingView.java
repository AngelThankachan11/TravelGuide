package com.travel.guide.apicalls.viewclass;

import com.travel.guide.apicalls.model.BannerResBean;
import com.travel.guide.apicalls.model.BookingDetailsResBean;
import com.travel.guide.apicalls.model.ExploreMoreResBean;
import com.travel.guide.apicalls.model.MyBookingsResBean;
import com.travel.guide.apicalls.model.PropertyTypeResBean;

public interface IMyBookingView extends IUtopperView{
    void onBannerSuccess(BannerResBean item);
    void onPropertyTypeSucess(PropertyTypeResBean item);
    void onBookingListSucess(MyBookingsResBean item);
    void onExploreSuccess(ExploreMoreResBean item);
    void onBookingDetailsSuccess(BookingDetailsResBean item);
}