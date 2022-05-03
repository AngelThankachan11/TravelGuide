package com.travel.guide.apicalls.viewclass;

import com.travel.guide.apicalls.model.BannerResBean;
import com.travel.guide.apicalls.model.NotificationResBean;

public interface INotificationView extends IUtopperView{
    void onNotificationListSuccess(NotificationResBean item);
    void onBannerSuccess(BannerResBean item);
}
