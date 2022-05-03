package com.travel.guide.apicalls.viewclass;

import com.travel.guide.apicalls.model.ProfileResBean;

public interface IProfileView extends IUtopperView{
    void onProfileSuccess(ProfileResBean item);
}
