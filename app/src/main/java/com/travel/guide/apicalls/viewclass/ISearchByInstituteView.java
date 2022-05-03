package com.travel.guide.apicalls.viewclass;

import com.travel.guide.apicalls.model.DistanceRangeResBean;
import com.travel.guide.apicalls.model.InstituteListResBean;
import com.travel.guide.apicalls.model.SearchByInsResBean;
import com.travel.guide.apicalls.model.ServiceableCityResBean;

public interface ISearchByInstituteView extends IUtopperView{
    void onCitySucess(ServiceableCityResBean item);
    void onDistanceRangeSuccess(DistanceRangeResBean item);
    void onInstituteListSuccess(InstituteListResBean item);
    void onSearchByInsSuccess(SearchByInsResBean item);
}
