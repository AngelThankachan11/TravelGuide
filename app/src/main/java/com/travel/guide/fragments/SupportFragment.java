package com.travel.guide.fragments;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.viewpager.widget.ViewPager;

import com.travel.guide.databinding.FragmentSupportBinding;
import com.travel.guide.BannerDetailsActivity;
import com.travel.guide.ChatActivity;
import com.travel.guide.MainActivity;
import com.travel.guide.R;
import com.travel.guide.adapters.SupportQueryAdapter;
import com.travel.guide.adapters.ViewPagerAdapter;
import com.travel.guide.apicalls.model.BannerResBean;
import com.travel.guide.apicalls.model.FaqResBean;
import com.travel.guide.apicalls.model.RequestCallbackResBean;
import com.travel.guide.apicalls.model.ServiceableCityResBean;
import com.travel.guide.apicalls.presenter.SupportPresenter;
import com.travel.guide.apicalls.viewclass.ISupportView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class SupportFragment extends Fragment implements SupportQueryAdapter.ItemClickListener, View.OnClickListener, ISupportView,
        ViewPagerAdapter.ItemClickListener {

    FragmentSupportBinding binding;
    List<FaqResBean.FaqItem> faqList = new ArrayList<>();
    SupportQueryAdapter supportQueryAdapter;

    List<String> cityArray = new ArrayList<>();
    List<ServiceableCityResBean.DataItem> cityList = new ArrayList<>();
    ArrayAdapter bindingStateAdapter, bindingCityAdapter;

    String selectedStateName, selectedCityId;
    List<String> bannerImageList = new ArrayList<>();
    private ViewPagerAdapter viewPagerAdapter;

    private int dotscount;
    private ImageView[] dots;
    int currentPage = 0;
    Timer timer;
    final long DELAY_MS = 500;//delay in milliseconds before task is to be executed
    final long PERIOD_MS = 3000;
    SupportPresenter presenter;
    private List<BannerResBean.BannerItem> bannerItemList = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(layoutInflater, container, savedInstanceState);
        binding = DataBindingUtil.inflate(layoutInflater, R.layout.fragment_support, container, false);
        presenter = new SupportPresenter();
        presenter.setView(this);

        supportQueryAdapter = new SupportQueryAdapter(getContext(), faqList, this);
        GridLayoutManager gridLayoutManager2 = new GridLayoutManager(getContext(), 1, LinearLayoutManager.VERTICAL, false);
        binding.recyPublicQuery.setLayoutManager(gridLayoutManager2);
        binding.recyPublicQuery.setItemAnimator(new DefaultItemAnimator());
        binding.recyPublicQuery.setAdapter(supportQueryAdapter);

        binding.imgBack.setOnClickListener(this);

        int resId = R.anim.layout_animation;
        LayoutAnimationController animation = AnimationUtils.loadLayoutAnimation(getContext(), resId);
        binding.recyPublicQuery.setLayoutAnimation(animation);

        viewPagerAdapter = new ViewPagerAdapter(bannerImageList, getContext(), this);
        binding.viewPager.setAdapter(viewPagerAdapter);

        presenter.CallFAQData(getActivity(), ((MainActivity)getActivity()).userData.getACCESS_TOKEN());

        binding.imgBack.setOnClickListener(this);
        binding.consCall.setOnClickListener(this);
        binding.consChat.setOnClickListener(this);


        return binding.getRoot();
    }

    public void showRequestCallbackDialog(Activity activity){
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog_request_callback);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        Spinner spinCity = dialog.findViewById(R.id.spinCity);
        ImageView imgCross = dialog.findViewById(R.id.imgCross);
        EditText edtMobile = dialog.findViewById(R.id.edtMobile);
        ConstraintLayout consRequest = dialog.findViewById(R.id.consRequest);

        bindingCityAdapter = new ArrayAdapter(getContext(),R.layout.spin_drop_down_view, cityArray){
            @Override
            public View getDropDownView(int position, View convertView, ViewGroup parent) {
                // TODO Auto-generated method stub

                View view = super.getView(position, convertView, parent);

                TextView text = (TextView)view.findViewById(R.id.txtSpinItem);
                text.setTextColor(getContext().getResources().getColor(R.color.app_color));

                return view;

            }};
        bindingCityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        spinCity.setAdapter(bindingCityAdapter);
        spinCity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                if (position>0)
                    selectedCityId = cityList.get(position-1).getCityId();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        consRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (edtMobile.getText().toString().trim().isEmpty()){
                    Toast.makeText(getContext(), "Please provide mobile number", Toast.LENGTH_LONG).show();
                }else if (selectedCityId==null){
                    Toast.makeText(getContext(), "Please select city", Toast.LENGTH_LONG).show();
                }else {
                    presenter.CallRequestCallback(getActivity(), edtMobile.getText().toString(), selectedCityId, ((MainActivity)getActivity()).userData.getACCESS_TOKEN());
                    dialog.dismiss();
                }
            }
        });

        imgCross.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        dialog.show();
        Window window = dialog.getWindow();
        window.setLayout(ConstraintLayout.LayoutParams.MATCH_PARENT, ConstraintLayout.LayoutParams.MATCH_PARENT);

    }


    @Override
    public void OnQuestionClicked(int position) {
        boolean open = false;
        if (!faqList.get(position).getIsOpen())
            open = true;
        for (int i=0; i<faqList.size(); i++){
            faqList.get(i).setIsOpen(false);
        }
        if (open)
            faqList.get(position).setIsOpen(true);

        supportQueryAdapter.notifyDataSetChanged();
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
            case R.id.consCall:
                if (cityList.size()>0) {
                    showRequestCallbackDialog(getActivity());
                }else {
                    presenter.CityCall(getActivity(), ((MainActivity) getActivity()).userData.getACCESS_TOKEN());
                }
                break;
            case R.id.consChat:
                getActivity().startActivity(new Intent(getActivity(), ChatActivity.class));
                break;
            default:
                break;
        }
    }

    @Override
    public void onFaqSuccess(FaqResBean item) {
        faqList.clear();
        if (item.isStatus()){
            faqList.addAll(item.getFaq());
        }else {
            ((MainActivity)getActivity()).toast("FAQ list not found");
        }
        supportQueryAdapter.notifyDataSetChanged();
        presenter.getBanners(getActivity(), "support", ((MainActivity)getActivity()).userData.getACCESS_TOKEN());
    }

    @Override
    public void onCitySucess(ServiceableCityResBean item) {
        cityList.clear();
        cityArray.clear();
        cityArray.add("Select city");
        if (item.isStatus()){
            cityList.addAll(item.getData());
            for (int i=0; i<item.getData().size(); i++) {
                cityArray.add(item.getData().get(i).getName());
            }
            showRequestCallbackDialog(getActivity());
        }else {
            ((MainActivity)getActivity()).toast("City list not found");
        }
        bindingCityAdapter.notifyDataSetChanged();
    }

    @Override
    public void onRequestCallbackSuccess(RequestCallbackResBean item) {
        /*if (item.isStatus()){
            getParentFragmentManager().popBackStack();
        }*/
        Toast.makeText(getContext(), item.getMessage(), Toast.LENGTH_LONG).show();
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
            ((MainActivity)getActivity()).toast("Banner not found for support section");
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
    public void enableLoadingBar(Context context, boolean enable) {
        ((MainActivity)getActivity()).enableLoadingBar(context, enable);
    }

    @Override
    public void onError(String reason) {
        ((MainActivity)getActivity()).toast(reason);
    }


}
