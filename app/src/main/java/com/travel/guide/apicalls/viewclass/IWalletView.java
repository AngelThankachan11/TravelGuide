package com.travel.guide.apicalls.viewclass;

import com.travel.guide.apicalls.model.ExploreMoreResBean;
import com.travel.guide.apicalls.model.WalletResBean;

public interface IWalletView extends IUtopperView{
    void onWalletHistorySuccess(WalletResBean item);
    void onExploreSuccess(ExploreMoreResBean item);
}
