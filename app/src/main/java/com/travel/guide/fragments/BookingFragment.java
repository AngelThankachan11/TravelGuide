package com.travel.guide.fragments;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.travel.guide.databinding.FragmentBookingBinding;
import com.travel.guide.BannerDetailsActivity;
import com.travel.guide.BookingConfimationActivity;
import com.travel.guide.MainActivity;
import com.travel.guide.MapMarkerActivity;
import com.travel.guide.R;
import com.travel.guide.ReferandEarnActivity;
import com.travel.guide.adapters.MyBookingAdapter;
import com.travel.guide.adapters.SelectNeedsAdapter;
import com.travel.guide.adapters.ViewPagerAdapter;
import com.travel.guide.apicalls.api.ApiConstants;
import com.travel.guide.apicalls.model.BannerResBean;
import com.travel.guide.apicalls.model.BookingDetailsResBean;
import com.travel.guide.apicalls.model.ExploreMoreResBean;
import com.travel.guide.apicalls.model.MyBookingsResBean;
import com.travel.guide.apicalls.model.PropertyTypeResBean;
import com.travel.guide.apicalls.presenter.BookingPresenter;
import com.travel.guide.apicalls.viewclass.IMyBookingView;
import com.travel.guide.utils.AppUtils;
import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class BookingFragment extends Fragment implements MyBookingAdapter.ItemClickListener, View.OnClickListener, IMyBookingView,
        SelectNeedsAdapter.PropertTypeClickListener, ViewPagerAdapter.ItemClickListener {

    FragmentBookingBinding binding;
    List<MyBookingsResBean.BookingsItem> bookingList = new ArrayList<>();
    List<PropertyTypeResBean.DataItem> propertyTypeList = new ArrayList<>();
    MyBookingAdapter myBookingAdapter;
    BookingPresenter presenter;
    List<String> bannerImageList = new ArrayList<>();
    private ViewPagerAdapter viewPagerAdapter;

    private int dotscount;
    private ImageView[] dots;
    int currentPage = 0;
    Timer timer;
    final long DELAY_MS = 500;//delay in milliseconds before task is to be executed
    final long PERIOD_MS = 3000;
    Dialog dialog;
    public static final int MY_PERMISSIONS_REQUEST_CALL_PHONE = 23;
    Intent callIntent;
    LayoutAnimationController animation;
    private List<BannerResBean.BannerItem> bannerItemList = new ArrayList<>();
    private List<ExploreMoreResBean.DataItem> exploreItemList = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(layoutInflater, container, savedInstanceState);
        binding = DataBindingUtil.inflate(layoutInflater, R.layout.fragment_booking, container, false);
        presenter = new BookingPresenter();
        presenter.setView(this);

        myBookingAdapter = new MyBookingAdapter(getActivity(), bookingList, false, this);
        GridLayoutManager gridLayoutManager2 = new GridLayoutManager(getContext(), 1, LinearLayoutManager.VERTICAL, false);
        binding.recyMyBooking.setLayoutManager(gridLayoutManager2);
        binding.recyMyBooking.setItemAnimator(new DefaultItemAnimator());
        binding.recyMyBooking.setAdapter(myBookingAdapter);

        int resId = R.anim.layout_animation;
        animation = AnimationUtils.loadLayoutAnimation(getContext(), resId);

        viewPagerAdapter = new ViewPagerAdapter(bannerImageList, getContext(), this);
        binding.viewPager.setAdapter(viewPagerAdapter);

        binding.imgBack.setOnClickListener(this);
        binding.txtBookNow.setOnClickListener(this);
        binding.imgExplore1.setOnClickListener(this);
        binding.imgExplore2.setOnClickListener(this);

        presenter.CallMyBookings(getActivity(), ((MainActivity)getActivity()).userData.getACCESS_TOKEN());


        return binding.getRoot();
    }

    public void showSelectServiceDialog(Activity activity){
        dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.filter_dialog);

        RecyclerView recyPropertyType = dialog.findViewById(R.id.recyPropertyType);
        CardView cardCross = dialog.findViewById(R.id.cardCross);

        SelectNeedsAdapter selectNeedsAdapter = new SelectNeedsAdapter(getActivity(), propertyTypeList, this);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 1, LinearLayoutManager.VERTICAL, false);
        recyPropertyType.setLayoutManager(gridLayoutManager);
        recyPropertyType.setItemAnimator(new DefaultItemAnimator());
        recyPropertyType.setAdapter(selectNeedsAdapter);

        dialog.show();

        cardCross.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        Window window = dialog.getWindow();
        window.setLayout(ConstraintLayout.LayoutParams.MATCH_PARENT, ConstraintLayout.LayoutParams.MATCH_PARENT);
        window.setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
    }

    @Override
    public void OnLocationClicked(int position) {
        Intent intent = new Intent(getActivity(), MapMarkerActivity.class);
        intent.putExtra("lat", bookingList.get(position).getServiceProperty().getLatitude());
        intent.putExtra("lng", bookingList.get(position).getServiceProperty().getLongitude());
        intent.putExtra("propertyName", bookingList.get(position).getServiceProperty().getBusinessName());
        startActivity(intent);
    }

    @Override
    public void OnCallClicked(int position) {
        callIntent = new Intent(Intent.ACTION_CALL);
        callIntent.setData(Uri.parse("tel:"+bookingList.get(position).getHostelowner().getMobile()));
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CALL_PHONE}, MY_PERMISSIONS_REQUEST_CALL_PHONE);
        } else {
            startActivity(callIntent);
        }
    }

    @Override
    public void OnBookingClicked(int position) {
        presenter.CallBookingDetails(getActivity(), ""+bookingList.get(position).getId(), ((MainActivity)getActivity()).userData.getACCESS_TOKEN());
    }

    @Override
    public void OnPayNowClicked(int position) {

    }

    @Override
    public void OnPropertyTypeClicked(int position) {
        //LoadDBookingData();
        dialog.dismiss();
        Fragment fragment = new SearchFragment();
        Bundle bundle = new Bundle();
        bundle.putString("propertyTypeId", ""+propertyTypeList.get(position).getId());
        fragment.setArguments(bundle);
        AppUtils.goFragmentAddWithoutBackStack(getContext(), fragment);
    }

    @Override
    public void onBannerClick(int position) {
        Intent intent = new Intent(getActivity(), BannerDetailsActivity.class);
        intent.putExtra("bannerData", (Serializable) bannerItemList.get(position));
        intent.putExtra("from", "banner");
        startActivity(intent);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.imgBack:
                ((MainActivity)getActivity()).ChangeBottobar(((MainActivity)getActivity()).binding.consHome, new HomeFragment());
                break;
            case R.id.txtBookNow:
                if (propertyTypeList.size()>0){
                    showSelectServiceDialog(getActivity());
                }else {
                    presenter.getPropertyType(getActivity(), ((MainActivity) getActivity()).userData.getACCESS_TOKEN());
                }
                break;
            case R.id.txtReferEarn:
                getActivity().startActivity(new Intent(getActivity(), ReferandEarnActivity.class));
                break;
            case R.id.imgExplore1:
                Intent intent = new Intent(getActivity(), BannerDetailsActivity.class);
                intent.putExtra("exploreData", (Serializable) exploreItemList.get(0));
                intent.putExtra("from", "explore");
                startActivity(intent);
                break;
            case R.id.imgExplore2:
                Intent intent1 = new Intent(getActivity(), BannerDetailsActivity.class);
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
            ((MainActivity)getActivity()).toast("Data Not found for popular properties section");
        }
        presenter.CallExploreMore(getActivity(), ((MainActivity)getActivity()).userData.getACCESS_TOKEN());
    }

    @Override
    public void onPropertyTypeSucess(PropertyTypeResBean item) {
        if (item.isStatus()){
            propertyTypeList.addAll(item.getData());
            showSelectServiceDialog(getActivity());
        }else {
            ((MainActivity)getActivity()).toast("No property types found");
        }
    }

    @Override
    public void onBookingListSucess(MyBookingsResBean item) {
        bookingList.clear();
        if (item.isStatus()){
            bookingList.addAll(item.getBookings());
            myBookingAdapter.notifyDataSetChanged();
            binding.consSorry.setVisibility(View.GONE);
            binding.recyMyBooking.setLayoutAnimation(animation);
        }else {
            ((MainActivity)getActivity()).toast("No booking found");
            binding.consSorry.setVisibility(View.VISIBLE);
        }
        presenter.getBanners(getActivity(), "booking", ((MainActivity)getActivity()).userData.getACCESS_TOKEN());
    }

    @Override
    public void onExploreSuccess(ExploreMoreResBean item) {
        if (item.isStatus()){
            exploreItemList.addAll(item.getData());
            Picasso.get().load(ApiConstants.BASE_IMAGE_URL+item.getData().get(0).getImage()).into(binding.imgExplore1);
            Picasso.get().load(ApiConstants.BASE_IMAGE_URL+item.getData().get(1).getImage()).into(binding.imgExplore2);
        }else {
            ((MainActivity)getActivity()).toast("Explore more banner not found");
        }
    }

    @Override
    public void onBookingDetailsSuccess(BookingDetailsResBean item) {
        if (item.isStatus()) {
            Intent intent = new Intent(getActivity(), BookingConfimationActivity.class);
            intent.putExtra("bookingData", (Serializable) item.getData());
            intent.putExtra("from", "BookingDetails");
            startActivity(intent);
        }else {
            ((MainActivity)getActivity()).toast(item.getMessage());
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
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_CALL_PHONE:
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    startActivity(callIntent);

                } else {
                    ((MainActivity)getActivity()).toast("You have denied required permission for call action");
                }
            break;

            default:
                break;

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }

    @Override
    public void enableLoadingBar(Context context, boolean enable) {
        ((MainActivity)getActivity()).enableLoadingBar(context, enable);
    }

    @Override
    public void onError(String reason) {
        ((MainActivity)getActivity()).toast(reason);
    }
}
