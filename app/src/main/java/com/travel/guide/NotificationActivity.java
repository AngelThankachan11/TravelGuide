package com.travel.guide;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.viewpager.widget.ViewPager;

import com.travel.guide.databinding.ActivityNotificationBinding;
import com.travel.guide.adapters.NotificationAdapter;
import com.travel.guide.adapters.ViewPagerAdapter;
import com.travel.guide.apicalls.model.BannerResBean;
import com.travel.guide.apicalls.model.NotificationResBean;
import com.travel.guide.apicalls.presenter.NotificationPresenter;
import com.travel.guide.apicalls.viewclass.INotificationView;
import com.travel.guide.utils.SharedPreferenceData;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class NotificationActivity extends BaseActivity implements NotificationAdapter.ItemClickListener, INotificationView, ViewPagerAdapter.ItemClickListener {

    ActivityNotificationBinding binding;
    NotificationPresenter presenter;
    NotificationAdapter notificationAdapter;
    List<String> bannerImageList = new ArrayList<>();
    private ViewPagerAdapter viewPagerAdapter;
    private int dotscount;
    private ImageView[] dots;
    int currentPage = 0;
    Timer timer;
    final long DELAY_MS = 500;//delay in milliseconds before task is to be executed
    final long PERIOD_MS = 3000;

    List<NotificationResBean.NotificationItem> notificationList = new ArrayList<>();
    private List<BannerResBean.BannerItem> bannerItemList = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_notification);
        presenter = new NotificationPresenter();
        presenter.setView(this);

        notificationAdapter = new NotificationAdapter(this, notificationList, this);
        GridLayoutManager gridLayoutManager2 = new GridLayoutManager(this, 1, LinearLayoutManager.VERTICAL, false);
        binding.recyNotification.setLayoutManager(gridLayoutManager2);
        binding.recyNotification.setItemAnimator(new DefaultItemAnimator());
        binding.recyNotification.setAdapter(notificationAdapter);

        viewPagerAdapter = new ViewPagerAdapter(bannerImageList, getContext(), this);
        binding.viewPager.setAdapter(viewPagerAdapter);

        presenter.getNotifications(this, (new SharedPreferenceData(this).getACCESS_TOKEN()));
    }

    @Override
    public void OnNotificatonClicked(int Position) {

    }

    @Override
    public void onBannerClick(int position) {
        Intent intent = new Intent(this, BannerDetailsActivity.class);
        intent.putExtra("bannerData", (Serializable) bannerItemList.get(position));
        intent.putExtra("from", "banner");
        startActivity(intent);
    }

    public void onClick(View view) {
        switch (view.getId()){
            case R.id.imgBack:
                finish();
                break;
            default:
                break;
        }
    }

    @Override
    public void onNotificationListSuccess(NotificationResBean item) {
        notificationList.clear();
        if (item.isStatus()){
            notificationList.addAll(item.getNotification());
        }else {
            toast("Notification list not found");
        }
        notificationAdapter.notifyDataSetChanged();
        presenter.getBanners(this, "support", (new SharedPreferenceData(this).getACCESS_TOKEN()));
    }

    @Override
    public void onBannerSuccess(BannerResBean item) {
        if (item.isStatus()){
            bannerItemList.addAll(item.getBanner());
            for (int i=0; i<item.getBanner().size(); i++){
                bannerImageList.add(item.getBanner().get(i).getImage());
            }
            viewPagerAdapter.notifyDataSetChanged();
            LoadImageSlider();
        }else{
            toast("Banner not found for Notification section");
        }
    }

    public void LoadImageSlider(){
        dotscount = viewPagerAdapter.getCount();
        dots = new ImageView[dotscount];

        for(int i = 0; i < dotscount; i++) {

            dots[i] = new ImageView(getContext());
            dots[i].setImageDrawable(ContextCompat.getDrawable(getContext().getApplicationContext(), R.drawable.nonactive_dot));

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

            params.setMargins(8, 0, 8, 0);

            binding.SliderDots.addView(dots[i], params);
        }

        dots[0].setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.active_dot));

        binding.viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                try {
                    for (int i = 0; i < dotscount; i++) {
                        dots[i].setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.nonactive_dot));
                    }
                    dots[position].setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.active_dot));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        /*After setting the adapter use the timer */
        final Handler handler = new Handler();
        final Runnable Update = new Runnable() {
            public void run() {
                if (currentPage == (dotscount - 1)) {
                    currentPage = -1;
                }
                currentPage = currentPage + 1;
                binding.viewPager.setCurrentItem(currentPage);
            }
        };
        timer = new Timer(); // This will create a new Thread
        timer.schedule(new TimerTask() { // task to be scheduled
            @Override
            public void run() {
                handler.post(Update);
            }
        }, DELAY_MS, PERIOD_MS);
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void onError(String reason) {
        toast(reason);
    }
}
