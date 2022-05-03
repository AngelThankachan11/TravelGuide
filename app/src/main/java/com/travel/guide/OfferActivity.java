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

import com.travel.guide.databinding.ActivityOfferBinding;
import com.travel.guide.adapters.OfferAdapter;
import com.travel.guide.adapters.OfferSecondAdapter;
import com.travel.guide.adapters.ViewPagerAdapter;
import com.travel.guide.apicalls.model.BannerResBean;
import com.travel.guide.apicalls.model.OfferResBean;
import com.travel.guide.apicalls.presenter.OfferPresenter;
import com.travel.guide.apicalls.viewclass.IOfferView;
import com.travel.guide.utils.SharedPreferenceData;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class OfferActivity extends BaseActivity implements OfferAdapter.ItemClickListener, OfferSecondAdapter.ItemClickListener, IOfferView,
        ViewPagerAdapter.ItemClickListener {

    ActivityOfferBinding binding;
    OfferAdapter offerAdapter;
    OfferSecondAdapter offerSecondAdapter;
    List<OfferResBean.DataItem> offerData = new ArrayList<>();
    List<String> offerSecondData = new ArrayList<>();
    OfferPresenter presenter;
    List<String> bannerImageList = new ArrayList<>();
    private ViewPagerAdapter viewPagerAdapter;

    private int dotscount;
    private ImageView[] dots;
    int currentPage = 0;
    Timer timer;
    final long DELAY_MS = 500;//delay in milliseconds before task is to be executed
    final long PERIOD_MS = 3000;
    SharedPreferenceData userData;
    private List<BannerResBean.BannerItem> bannerItemList = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_offer);
        presenter = new OfferPresenter();
        presenter.setView(this);
        userData = new SharedPreferenceData(this);

        offerAdapter = new OfferAdapter(this, offerData, this);
        GridLayoutManager gridLayoutManager1 = new GridLayoutManager(this, 1, LinearLayoutManager.VERTICAL, false);
        binding.recyOffer.setLayoutManager(gridLayoutManager1);
        binding.recyOffer.setItemAnimator(new DefaultItemAnimator());
        binding.recyOffer.setAdapter(offerAdapter);

        offerSecondAdapter = new OfferSecondAdapter(this, offerSecondData, this);
        GridLayoutManager gridLayoutManager2 = new GridLayoutManager(this, 1, LinearLayoutManager.HORIZONTAL, false);
        binding.recySecondOffer.setLayoutManager(gridLayoutManager2);
        binding.recySecondOffer.setItemAnimator(new DefaultItemAnimator());
        binding.recySecondOffer.setAdapter(offerSecondAdapter);

        viewPagerAdapter = new ViewPagerAdapter(bannerImageList, getContext(), this);
        binding.viewPager.setAdapter(viewPagerAdapter);

        presenter.getOffers(this, userData.getACCESS_TOKEN());
    }

    @Override
    public void OnOfferClicked(int Position) {
        Intent intent = new Intent(this, PropertyListActivity.class);
        intent.putExtra("propertyData", (Serializable) offerData.get(Position).getGrabNow());
        intent.putExtra("couponId", ""+offerData.get(Position).getCouponCode());
        startActivity(intent);
    }

    @Override
    public void OnOfferDetailsClicked(int position) {
        Intent intent = new Intent(this, OfferDetailsActivity.class);
        intent.putExtra("offerData", (Serializable) offerData.get(position));
        startActivity(intent);
    }

    @Override
    public void OnOfferSecondClicked(int Position) {

    }

    @Override
    public void onBannerClick(int position) {
        Intent intent = new Intent(this, BannerDetailsActivity.class);
        intent.putExtra("bannerData", (Serializable) bannerItemList.get(position));
        intent.putExtra("from", "banner");
        startActivity(intent);
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
            toast("Banner not found for Offer section");
        }
    }

    @Override
    public void onOfferSuccess(OfferResBean item) {
        offerData.clear();
        if (item.isStatus()){
            offerData.addAll(item.getData());
        }else {
            toast("Offer list not found");
        }
        offerAdapter.notifyDataSetChanged();
        presenter.getBanners(this, "offer", userData.getACCESS_TOKEN());
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
                if (position == 0) {
                    try {
                        for (int i = 0; i < dotscount; i++) {
                            dots[i].setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.nonactive_dot));
                        }
                        dots[position].setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.active_dot));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

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

    public void onClick(View view){
        switch (view.getId()){
            case R.id.imgBack:
                finish();
                break;
            default:
                break;
        }
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
