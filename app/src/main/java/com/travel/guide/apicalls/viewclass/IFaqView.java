package com.travel.guide.apicalls.viewclass;

import com.travel.guide.apicalls.model.FaqResBean;

public interface IFaqView extends IUtopperView{
    void onFaqSuccess(FaqResBean item);
}
