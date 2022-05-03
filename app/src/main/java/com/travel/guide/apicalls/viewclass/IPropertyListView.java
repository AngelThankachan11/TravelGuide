package com.travel.guide.apicalls.viewclass;

import com.travel.guide.apicalls.model.PropertyTypeResBean;

public interface IPropertyListView extends IUtopperView{
    void onPropertyTypeSuccess(PropertyTypeResBean item);
}
