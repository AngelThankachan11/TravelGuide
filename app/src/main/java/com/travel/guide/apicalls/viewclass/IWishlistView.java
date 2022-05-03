package com.travel.guide.apicalls.viewclass;

import com.travel.guide.apicalls.model.WishlistResBean;

public interface IWishlistView extends IUtopperView{
    void onWishListSuccess(WishlistResBean item);
}
