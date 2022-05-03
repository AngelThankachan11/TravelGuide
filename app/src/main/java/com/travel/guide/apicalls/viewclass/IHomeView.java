package com.travel.guide.apicalls.viewclass;

import com.travel.guide.apicalls.model.AmenitiesResBean;
import com.travel.guide.apicalls.model.BannerResBean;
import com.travel.guide.apicalls.model.BookingDetailsResBean;
import com.travel.guide.apicalls.model.HomeDataResBean;
import com.travel.guide.apicalls.model.MyBookingsResBean;
import com.travel.guide.apicalls.model.PopularPropertyResBean;
import com.travel.guide.apicalls.model.PropertyTypeResBean;
import com.travel.guide.apicalls.model.SearchPropertyResBean;

public interface IHomeView extends IUtopperView{
    void onBookingListSucess(MyBookingsResBean item);
    void onPopularPropertySuccess(PopularPropertyResBean item);
    void onBannerSuccess(BannerResBean item);
    void onHomeDataSuccess(HomeDataResBean item);
    void onAmenitiesSucess(AmenitiesResBean item);
    void onPropertyTypeSucess(PropertyTypeResBean item);
    void onBookingDetailsSuccess(BookingDetailsResBean item);
    void onSearchSuccess(SearchPropertyResBean item);
}

