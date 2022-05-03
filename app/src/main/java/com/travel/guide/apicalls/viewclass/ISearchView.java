package com.travel.guide.apicalls.viewclass;

import com.travel.guide.apicalls.model.PriceRangeResBean;
import com.travel.guide.apicalls.model.PropertyTypeResBean;
import com.travel.guide.apicalls.model.RecommendedResBean;

public interface ISearchView extends IUtopperView{
    void onRecommendedSuccess(RecommendedResBean item);
    void onPriceRangeSuccess(PriceRangeResBean item);
    void onPropertyTypeSucess(PropertyTypeResBean item);
}
