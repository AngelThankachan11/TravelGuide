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

import com.travel.guide.databinding.ActivityRecentlyVisitedBinding;
import com.travel.guide.adapters.RecentlyVisitedHostelAdapter;
import com.travel.guide.adapters.ViewPagerAdapter;
import com.travel.guide.apicalls.api.ApiConstants;
import com.travel.guide.apicalls.model.BannerResBean;
import com.travel.guide.apicalls.model.ExploreMoreResBean;
import com.travel.guide.apicalls.model.RecentlyVisitedResBean;
import com.travel.guide.apicalls.presenter.RecentlyVisitedPresenter;
import com.travel.guide.apicalls.viewclass.IRecentlyView;
import com.travel.guide.utils.SharedPreferenceData;
import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class RecentlyVisitedActivity extends BaseActivity implements RecentlyVisitedHostelAdapter.ItemClickListener, View.OnClickListener,
        IRecentlyView, ViewPagerAdapter.ItemClickListener {

    ActivityRecentlyVisitedBinding binding;
    RecentlyVisitedHostelAdapter recentlyVisitedHostelAdapter;
    List<RecentlyVisitedResBean.DataItem> recentlyVisitedList = new ArrayList<>();

    RecentlyVisitedPresenter presenter;
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
    private List<ExploreMoreResBean.DataItem> exploreItemList = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_recently_visited);
        presenter = new RecentlyVisitedPresenter();
        presenter.setView(this);
        userData = new SharedPreferenceData(this);

        recentlyVisitedHostelAdapter = new RecentlyVisitedHostelAdapter(this, recentlyVisitedList, this);
        GridLayoutManager gridLayoutManager2 = new GridLayoutManager(this, 1, LinearLayoutManager.VERTICAL, false);
        binding.recyHostels.setLayoutManager(gridLayoutManager2);
        binding.recyHostels.setItemAnimator(new DefaultItemAnimator());
        binding.recyHostels.setAdapter(recentlyVisitedHostelAdapter);

        viewPagerAdapter = new ViewPagerAdapter(bannerImageList, getContext(), this);
        binding.viewPager.setAdapter(viewPagerAdapter);

        presenter.CallRecentlyVisited(this, userData.getACCESS_TOKEN());

        binding.imgBack.setOnClickListener(this);
        binding.imgExplore1.setOnClickListener(this);
        binding.imgExplore2.setOnClickListener(this);
    }

    @Override
    public void OnPropertyClicked(int Position) {
        Intent intent = new Intent(this, PropertyDetailsActivity.class);
        intent.putExtra("propertyId", ""+recentlyVisitedList.get(Position).getId());
        intent.putExtra("couponId", "");
        startActivity(intent);
    }


    @Override
    public void onBannerClick(int position) {
        Intent intent = new Intent(this, BannerDetailsActivity.class);
        intent.putExtra("bannerData", (Serializable) bannerItemList.get(position));
        intent.putExtra("from", "banner");
        startActivity(intent);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.imgBack:
                finish();
                break;
            case R.id.imgExplore1:
                Intent intent = new Intent(this, BannerDetailsActivity.class);
                intent.putExtra("exploreData", (Serializable) exploreItemList.get(0));
                intent.putExtra("from", "explore");
                startActivity(intent);
                break;
            case R.id.imgExplore2:
                Intent intent1 = new Intent(this, BannerDetailsActivity.class);
                intent1.putExtra("exploreData", (Serializable) exploreItemList.get(1));
                intent1.putExtra("from", "explore");
                startActivity(intent1);
                break;
            default:
                break;
        }
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
            toast("Banner not found for RecentlyVisited section");
        }
        presenter.CallExploreMore(this, userData.getACCESS_TOKEN());
    }

    @Override
    public void onRecentlyVisitedSuccess(RecentlyVisitedResBean item) {
        recentlyVisitedList.clear();
        if (item.isStatus()){
            recentlyVisitedList.addAll(item.getData());
        }else {
            toast("No recently visited items found");
        }
        recentlyVisitedHostelAdapter.notifyDataSetChanged();
        presenter.getBanners(this, "recent", userData.getACCESS_TOKEN());
    }

    @Override
    public void onExploreSuccess(ExploreMoreResBean item) {
        if (item.isStatus()){
            exploreItemList.addAll(item.getData());
            Picasso.get().load(ApiConstants.BASE_IMAGE_URL+item.getData().get(0).getImage()).into(binding.imgExplore1);
            Picasso.get().load(ApiConstants.BASE_IMAGE_URL+item.getData().get(1).getImage()).into(binding.imgExplore2);
        }else {
            toast("Explore more banner not found");
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
