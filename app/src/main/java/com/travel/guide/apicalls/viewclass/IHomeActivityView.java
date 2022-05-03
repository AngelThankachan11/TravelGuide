package com.travel.guide.apicalls.viewclass;

import com.travel.guide.apicalls.model.OfflinePaynowResBean;

public interface IHomeActivityView extends IUtopperView{
    void onOfflinePaynowSuccess(OfflinePaynowResBean item);
}
