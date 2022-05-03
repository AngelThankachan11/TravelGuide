package com.travel.guide.fragments;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.viewpager.widget.ViewPager;

import com.travel.guide.MapActivity;
import com.travel.guide.databinding.FragmentHomeBinding;
import com.travel.guide.BannerDetailsActivity;
import com.travel.guide.BookingConfimationActivity;
import com.travel.guide.MapMarkerActivity;
import com.travel.guide.PropertyDetailsActivity;
import com.travel.guide.MainActivity;
import com.travel.guide.NotificationActivity;
import com.travel.guide.ProfileActivity;
import com.travel.guide.R;
import com.travel.guide.adapters.AmenitiesAdapter;
import com.travel.guide.adapters.OfflineBookingsAdapter;
import com.travel.guide.adapters.PopularAdapter;
import com.travel.guide.adapters.PropertyTypeAdapter;
import com.travel.guide.adapters.ViewPagerAdapter;
import com.travel.guide.apicalls.model.AmenitiesResBean;
import com.travel.guide.apicalls.model.BannerResBean;
import com.travel.guide.apicalls.model.BookingDetailsResBean;
import com.travel.guide.apicalls.model.HomeDataResBean;
import com.travel.guide.apicalls.model.MyBookingsResBean;
import com.travel.guide.apicalls.model.PopularPropertyResBean;
import com.travel.guide.apicalls.model.PropertyTypeResBean;
import com.travel.guide.apicalls.model.SearchPropertyResBean;
import com.travel.guide.apicalls.presenter.HomePresenter;
import com.travel.guide.apicalls.viewclass.IHomeView;
import com.travel.guide.utils.AppUtils;
import com.travel.guide.utils.SharedPreferenceData;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class HomeFragment extends Fragment implements AmenitiesAdapter.ItemClickListener, PopularAdapter.ItemClickListener, View.OnClickListener,
        IHomeView, PropertyTypeAdapter.PropertTypeClickListener, OfflineBookingsAdapter.ItemClickListener, ViewPagerAdapter.ItemClickListener {

    FragmentHomeBinding binding;
    List<HomeDataResBean.AmenitiesItem> amenitiesList = new ArrayList<>();
    List<HomeDataResBean.ServicePropertyItem> popularPropertiesList = new ArrayList<>();
    List<HomeDataResBean.CategoryItem> propertyTypeList = new ArrayList<>();
    List<HomeDataResBean.BookingsItem> bookingList = new ArrayList<>();
    private List<String> bannerImageList = new ArrayList<>();
    private List<HomeDataResBean.BannerItem> bannerItemList = new ArrayList<>();
    private AmenitiesAdapter amenitiesAdapter;
    private PopularAdapter popularAdapter;
    private PropertyTypeAdapter propertyTypeAdapter;
    private ViewPagerAdapter viewPagerAdapter;

    private int dotscount;
    private ImageView[] dots;
    /*LayoutAnimationController animationV, animationH;*/
    HomePresenter presenter;
    SharedPreferenceData userData;
    OfflineBookingsAdapter offlineBookingsAdapter;
    Intent callIntent;

    int currentPage = 0;
    Timer timer;
    final long DELAY_MS = 500;//delay in milliseconds before task is to be executed
    final long PERIOD_MS = 3000;
    public static final int MY_PERMISSIONS_REQUEST_CALL_PHONE = 23;
    String bookingIdForOfflinePay;

    private static final int REQUEST_CODE_LOCATION_PERMISSION = 1;
    int LAUNCH_SECOND_ACTIVITY = 1;


    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup container, Bundle savedInstanceState){
        super.onCreateView(layoutInflater, container, savedInstanceState);
        binding = DataBindingUtil.inflate(layoutInflater, R.layout.fragment_home, container, false);

        String getArgument = getArguments().getString("city");
        binding.txtAreaName.setText(getArgument);
        String getArgument1 = getArguments().getString("addressline");
        binding.txtAddressLine.setText(getArgument1);

        presenter = new HomePresenter();
        presenter.setView(this);
        userData = new SharedPreferenceData(getContext());

        amenitiesAdapter = new AmenitiesAdapter(getActivity(), amenitiesList, this);
        GridLayoutManager gridLayoutManager1 = new GridLayoutManager(getContext(), 1, LinearLayoutManager.HORIZONTAL, false);
        binding.recyAmenities.setLayoutManager(gridLayoutManager1);
        binding.recyAmenities.setItemAnimator(new DefaultItemAnimator());
        binding.recyAmenities.setAdapter(amenitiesAdapter);

        offlineBookingsAdapter = new OfflineBookingsAdapter(getActivity(), bookingList, true, this);
        GridLayoutManager gridLayoutManager2 = new GridLayoutManager(getContext(), 1, LinearLayoutManager.HORIZONTAL, false);
        binding.recyMyBooking.setLayoutManager(gridLayoutManager2);
        binding.recyMyBooking.setItemAnimator(new DefaultItemAnimator());
        binding.recyMyBooking.setAdapter(offlineBookingsAdapter);

        viewPagerAdapter = new ViewPagerAdapter(bannerImageList, getContext(), this);
        binding.viewPager.setAdapter(viewPagerAdapter);

        propertyTypeAdapter = new PropertyTypeAdapter(getActivity(), propertyTypeList, this);
        GridLayoutManager gridLayoutManager3 = new GridLayoutManager(getContext(), 2, LinearLayoutManager.VERTICAL, false);
        binding.recyPropertyType.setLayoutManager(gridLayoutManager3);
        binding.recyPropertyType.setItemAnimator(new DefaultItemAnimator());
        binding.recyPropertyType.setAdapter(propertyTypeAdapter);

        popularAdapter = new PopularAdapter(getActivity(), popularPropertiesList, this);
        GridLayoutManager gridLayoutManager4 = new GridLayoutManager(getContext(), 1, LinearLayoutManager.HORIZONTAL, false);
        binding.recyPopular.setLayoutManager(gridLayoutManager4);
        binding.recyPopular.setItemAnimator(new DefaultItemAnimator());
        binding.recyPopular.setAdapter(popularAdapter);

        /*int resId = R.anim.layout_animation;
        animationV = AnimationUtils.loadLayoutAnimation(getContext(), resId);*/

        /*int resId2 = R.anim.layout_animation1;
        animationH = AnimationUtils.loadLayoutAnimation(getContext(), resId2);*/



        //binding.recyCategories.setLayoutAnimation(animation);
        //binding.recyPopular.setLayoutAnimation(animation);

        //binding.relaySearchByLocation.setOnClickListener(this);
        binding.imgMenu.setOnClickListener(this);
        binding.imgNotification.setOnClickListener(this);
        binding.txtReferEarn.setOnClickListener(this);
        binding.txtViewAll.setOnClickListener(this);
        binding.imgLocation.setOnClickListener(this);

        binding.edtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int position, int i1, int i2) {
                //if (charSequence.length()>0)
                    presenter.callSearchPropertyList(getActivity(), charSequence.toString(), userData.getACCESS_TOKEN());
            }

            @Override
            public void afterTextChanged(Editable editable) {
                //filter(editable.toString());
            }
        });

        //presenter.getBanners(getActivity(), "home", userData.getACCESS_TOKEN());
        presenter.callHomeData(getActivity(), userData.getACCESS_TOKEN());

        return binding.getRoot();
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
    public void onResume(){
        super.onResume();
    }

    @Override
    public void OnItemClicked(int Position) {
        AppUtils.goFragmentAddWithoutBackStack(getContext(), new SearchFragment());
    }

    @Override
    public void OnPopularClicked(int Position) {
        Intent intent = new Intent(getActivity(), PropertyDetailsActivity.class);
        intent.putExtra("propertyId", ""+popularPropertiesList.get(Position).getId());
        intent.putExtra("couponId", "");
        getActivity().startActivity(intent);
    }

    @Override
    public void OnPropertyTypeClicked(int position) {
        Fragment fragment = new SearchFragment();
        Bundle bundle = new Bundle();
        bundle.putString("propertyTypeId", ""+propertyTypeList.get(position).getId());
        fragment.setArguments(bundle);
        AppUtils.goFragmentAddWithoutBackStack(getContext(), fragment);
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
        bookingIdForOfflinePay = bookingList.get(position).getUniqueId();
        ((MainActivity)getActivity()).RAZORPAY(bookingList.get(position).getTotalAmount(), bookingIdForOfflinePay);
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
            case R.id.imgMenu:
                startActivity(new Intent(getActivity(), ProfileActivity.class));
                //((MainActivity)getActivity()).openDrawer();
                break;
            case R.id.imgNotification:
                startActivity(new Intent(getActivity(), NotificationActivity.class));
                break;
            /*case R.id.relaySearchByLocation:
                ((MainActivity)getActivity()).ChangeBottobar(((MainActivity)getActivity()).binding.consSearch, new SearchFragment());
                break;*/
            case R.id.txtReferEarn:
                Intent intent = new Intent(android.content.Intent.ACTION_SEND);

                String shareBody = "Hey there! Sign-up on the MeraRoom app using my referral code & book your room. You can also avail some coupon codes MeraRoom Cashback. So, book now before the offer expires! \n" +
                        "\n" +
                        "Download the app: https://play.google.com/store/apps/details?id=com.travel.guide or use code"+userData.getREFERRAL_CODE()+"  while signing up.";

                /*String shareBody = "I am using MeraRoom app for booking room for study purpose, If you also want to do prebooking for room" +
                        ", can also get benefits from this app. \n\n Use my referral code for register :\n\n"+userData.getREFERRAL_CODE();*/
                intent.setType("text/plain");
                intent.putExtra(android.content.Intent.EXTRA_SUBJECT, getString(R.string.referral_earning));
                intent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
                startActivity(Intent.createChooser(intent, getString(R.string.share_using)));
                break;
            case R.id.txtViewAll:
                Fragment fragment = new PropertyListFragment();
                Bundle bundle = new Bundle();
                bundle.putSerializable("propertyData", (Serializable) popularPropertiesList);
                bundle.putSerializable("from", "Home");
                fragment.setArguments(bundle);
                AppUtils.goFragmentAddWithoutBackStack(getContext(), fragment);
                break;

            case R.id.imgLocation:
                Intent i = new Intent(getActivity(), MapActivity.class);
                startActivityForResult(i, LAUNCH_SECOND_ACTIVITY);
            default:
                break;
        }
    }

    @Override
    public void onHomeDataSuccess(HomeDataResBean item) {
        if (item.isStatus()){
            binding.consRoot.setVisibility(View.VISIBLE);
            if (item.getBanner().size()>0){
                bannerItemList.clear();
                bannerImageList.clear();
                bannerItemList.addAll(item.getBanner());
                for (int i=0; i<item.getBanner().size(); i++){
                    bannerImageList.add(item.getBanner().get(i).getImage());
                }
                viewPagerAdapter.notifyDataSetChanged();
                LoadImageSlider();
            }
            if (item.getAmenities().size()>0){
                amenitiesList.clear();
                amenitiesList.addAll(item.getAmenities());
                amenitiesAdapter.notifyDataSetChanged();
                /*binding.recyAmenities.setLayoutAnimation(animationV);*/
            }
            if (item.getBookings().size()>0){
                bookingList.clear();
                for (int i=0; i<item.getBookings().size(); i++){
                    if (item.getBookings().get(i).getPaymentType().equalsIgnoreCase("BookOffline") && item.getBookings().get(i)
                            .getPaymentStatus().equalsIgnoreCase("pending") && !item.getBookings().get(i)
                            .getStatus().equalsIgnoreCase("rejected"))
                        bookingList.add(item.getBookings().get(i));
                }

                offlineBookingsAdapter.notifyDataSetChanged();
                /*binding.recyMyBooking.setLayoutAnimation(animationH);*/
            }
            if (item.getCategory().size()>0){
                propertyTypeList.clear();
                propertyTypeList.addAll(item.getCategory());
                propertyTypeAdapter.notifyDataSetChanged();
                /*binding.recyPropertyType.setLayoutAnimation(animationV);*/
            }
            if (item.getServiceProperty().size()>0){
                popularPropertiesList.addAll(item.getServiceProperty());
                popularAdapter.notifyDataSetChanged();

            }else {
                binding.txtPopular.setVisibility(View.GONE);
                binding.txtViewAll.setVisibility(View.GONE);
                ((MainActivity)getActivity()).toast("Data Not found for popular properties section");
            }
        }else {

        }
    }

    @Override
    public void onBannerSuccess(BannerResBean item) {
        /*if (item.isStatus()){
            bannerItemList.addAll(item.getBanner());
            for (int i=0; i<item.getBanner().size(); i++){
                bannerImageList.add(item.getBanner().get(i).getImage());
            }
            viewPagerAdapter.notifyDataSetChanged();
            LoadImageSlider();
        }else{
            ((MainActivity)getActivity()).toast("Banner not found for Home section");
        }
        presenter.getAmenities(getActivity(), userData.getACCESS_TOKEN());*/
    }

    @Override
    public void onBookingListSucess(MyBookingsResBean item) {
        /*bookingList.clear();
        if (item.isStatus()){
            for (int i=0; i<item.getBookings().size(); i++){
                if (item.getBookings().get(i).getPaymentType().equalsIgnoreCase("BookOffline") && item.getBookings().get(i)
                        .getPaymentStatus().equalsIgnoreCase("pending") && !item.getBookings().get(i)
                        .getStatus().equalsIgnoreCase("rejected"))
                bookingList.add(item.getBookings().get(i));
            }
        }

        myBookingAdapter.notifyDataSetChanged();
        binding.recyMyBooking.setLayoutAnimation(animationH);
        presenter.getPropertyType(getActivity(), userData.getACCESS_TOKEN());*/
    }

    @Override
    public void onPopularPropertySuccess(PopularPropertyResBean item) {
        /*if (item.isStatus()){
            popularPropertiesList.addAll(item.getData());
            popularAdapter.notifyDataSetChanged();
        }else{
            binding.txtPopular.setVisibility(View.GONE);
            binding.txtViewAll.setVisibility(View.GONE);
            ((MainActivity)getActivity()).toast("Data Not found for popular properties section");
        }*/
    }

    @Override
    public void onAmenitiesSucess(AmenitiesResBean item) {
        /*amenitiesList.clear();
        if (item.isStatus()){
            amenitiesList.addAll(item.getData());
            amenitiesAdapter.notifyDataSetChanged();
        }else {
            ((MainActivity)getActivity()).toast("Amenties not found");
        }
        binding.recyAmenities.setLayoutAnimation(animationV);
        presenter.CallMyBookings(getActivity(), ((MainActivity)getActivity()).userData.getACCESS_TOKEN());*/
    }

    @Override
    public void onPropertyTypeSucess(PropertyTypeResBean item) {
       /* if (item.isStatus()){
            propertyTypeList.addAll(item.getData());
            propertyTypeAdapter.notifyDataSetChanged();
        }
        binding.recyPropertyType.setLayoutAnimation(animationV);
        presenter.getPopularProperty(getActivity(), userData.getACCESS_TOKEN());*/
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

    @Override
    public void onSearchSuccess(SearchPropertyResBean item) {
        if (item.isStatus() && item.getData().size()>0) {
            List<SearchPropertyResBean.Data> searchData = new ArrayList<>();
            List<String> searchItems = new ArrayList<String>();

            searchData.addAll(item.getData());
            for (int i = 0; i < item.getData().size(); i++) {
                searchItems.add(item.getData().get(i).getName());
            }

            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getActivity(), R.layout.list_drop_down_view, R.id.txtSpinItem, searchItems);
            binding.listSearchItem.setAdapter(arrayAdapter);
            binding.listSearchItem.setVisibility(View.VISIBLE);

            binding.listSearchItem.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                    Intent intent = new Intent(getActivity(), PropertyDetailsActivity.class);
                    intent.putExtra("propertyId", "" + searchData.get(position).getId());
                    intent.putExtra("couponId", "");
                    getActivity().startActivity(intent);
                }
            });
        }else {
            ((MainActivity)getActivity()).toast(item.getMessage());
            binding.listSearchItem.setVisibility(View.GONE);
        }
    }

    @Override
    public void enableLoadingBar(Context context, boolean enable) {
        ((MainActivity)getActivity()).enableLoadingBar(context, enable);
        /*try {
            ((MainActivity)getActivity()).enableLoadingBar(context, true);
        }catch (NullPointerException e){
            e.printStackTrace();
        }*/
    }

    @Override
    public void onError(String reason) {
        /*((MainActivity)getActivity()).toast(reason);
        binding.txtPopular.setVisibility(View.GONE);
        binding.txtViewAll.setVisibility(View.GONE);*/
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

}
