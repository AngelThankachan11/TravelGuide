package com.travel.guide.apicalls.viewclass;

import com.travel.guide.apicalls.model.BannerResBean;
import com.travel.guide.apicalls.model.ExploreMoreResBean;
import com.travel.guide.apicalls.model.RecentlyVisitedResBean;

public interface IRecentlyView extends IUtopperView{
    void onBannerSuccess(BannerResBean item);
    void onRecentlyVisitedSuccess(RecentlyVisitedResBean item);
    void onExploreSuccess(ExploreMoreResBean item);
}