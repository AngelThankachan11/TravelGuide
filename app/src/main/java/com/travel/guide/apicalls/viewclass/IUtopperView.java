package com.travel.guide.apicalls.viewclass;

import android.content.Context;

/**
 * Created by BhuvneshGautam on 18/11/2021
 * Contacts : 8005911809
 * Email id : bhunnu.chasta22@gmail.com.
 */

public interface IUtopperView {

    Context getContext();
    void enableLoadingBar(Context context, boolean enable);
    void onError(String reason);
}
