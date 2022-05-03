package com.travel.guide.apicalls.viewclass;

import com.travel.guide.apicalls.model.LoginResBean;

public interface ILoginView extends IUtopperView{
    void onLoginSucess(LoginResBean item);
}

