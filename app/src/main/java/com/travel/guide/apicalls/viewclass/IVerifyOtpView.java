package com.travel.guide.apicalls.viewclass;

import com.travel.guide.apicalls.model.VerifyOtpResBean;

public interface IVerifyOtpView extends IUtopperView{
    void onVerifySucess(VerifyOtpResBean item);
}
