package com.travel.guide.apicalls.viewclass;

import com.travel.guide.apicalls.model.AreaListResBean;
import com.travel.guide.apicalls.model.SearchByLPropertyResBean;
import com.travel.guide.apicalls.model.ServiceableCityResBean;

public interface ISearchByLView extends IUtopperView{
    void onCitySucess(ServiceableCityResBean item);
    void onSearchByLSuccess(SearchByLPropertyResBean item);
    void onAreaListSuccess(AreaListResBean item);
}
