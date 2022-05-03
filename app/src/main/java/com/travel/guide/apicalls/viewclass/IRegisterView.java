package com.travel.guide.apicalls.viewclass;

import com.travel.guide.apicalls.model.RegisterResBean;
import com.travel.guide.apicalls.model.ServiceableCityResBean;
import com.travel.guide.apicalls.model.StateResBean;

public interface IRegisterView extends IUtopperView{
    void onStateSucess(StateResBean item);
    void onCitySucess(ServiceableCityResBean item);
    void onRegisterSucess(RegisterResBean item);
}
