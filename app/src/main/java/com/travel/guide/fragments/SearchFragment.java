package com.travel.guide.fragments;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.travel.guide.databinding.FragmentSearchBinding;
import com.travel.guide.PropertyDetailsActivity;
import com.travel.guide.MainActivity;
import com.travel.guide.R;
import com.travel.guide.adapters.PriceRangeAdapter;
import com.travel.guide.adapters.RecommendedHostelAdapter;
import com.travel.guide.adapters.SelectNeedsAdapter;
import com.travel.guide.apicalls.model.PriceRangeResBean;
import com.travel.guide.apicalls.model.PropertyTypeResBean;
import com.travel.guide.apicalls.model.RecommendedResBean;
import com.travel.guide.apicalls.presenter.SearchPresenter;
import com.travel.guide.apicalls.viewclass.ISearchView;
import com.travel.guide.utils.AppUtils;

import java.util.ArrayList;
import java.util.List;

public class SearchFragment extends Fragment implements RecommendedHostelAdapter.ItemClickListener, View.OnClickListener, ISearchView
        , PriceRangeAdapter.PriceClickListener, SelectNeedsAdapter.PropertTypeClickListener {

    FragmentSearchBinding binding;
    RecommendedHostelAdapter recommendedHostelAdapter;
    List<RecommendedResBean.DataItem> recommendedList = new ArrayList<>();
    List<PriceRangeResBean.DataItem> priceRangeList = new ArrayList<>();
    SearchPresenter presenter;
    Dialog dialogPrice;
    String selectedPriceRangeId="";
    RecyclerView recyPriceRange;
    PriceRangeAdapter priceRangeAdapter;
    String propertyTypeId = "";
    Dialog dialog;
    List<PropertyTypeResBean.DataItem> propertyTypeList = new ArrayList<>();
    LayoutAnimationController animation;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        super.onCreateView(inflater, container, savedInstanceState);
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_search, container, false);
        presenter = new SearchPresenter();
        presenter.setView(this);
        if (getArguments()!=null) {
            if (getArguments().getString("propertyTypeId") != null)
                propertyTypeId = getArguments().getString("propertyTypeId");
        }

        recommendedHostelAdapter = new RecommendedHostelAdapter(getActivity(), recommendedList, this);
        GridLayoutManager gridLayoutManager2 = new GridLayoutManager(getContext(), 1, LinearLayoutManager.VERTICAL, false);
        binding.recyHostel.setLayoutManager(gridLayoutManager2);
        binding.recyHostel.setItemAnimator(new DefaultItemAnimator());
        binding.recyHostel.setAdapter(recommendedHostelAdapter);

        int resId = R.anim.layout_animation;
        animation = AnimationUtils.loadLayoutAnimation(getContext(), resId);

        //binding.relaySearch.setOnClickListener(this);
        binding.txtSearchByLocation.setOnClickListener(this);
        binding.imgBack.setOnClickListener(this);
        binding.imgSorting.setOnClickListener(this);
        binding.imgFilter.setOnClickListener(this);

        presenter.getRecommendedProperty(getActivity(), propertyTypeId, selectedPriceRangeId, ((MainActivity)getActivity()).userData.getACCESS_TOKEN());

        return binding.getRoot();
    }

    @Override
    public void OnItemClicked(int position) {
        Intent intent = new Intent(getActivity(), PropertyDetailsActivity.class);
        intent.putExtra("propertyId", ""+recommendedList.get(position).getId());
        intent.putExtra("couponId", "");
        startActivity(intent);
    }

    @Override
    public void OnPriceChecked(int position, boolean isChecked) {
        if (isChecked) {
            for (int i=0; i<priceRangeList.size(); i++){
                priceRangeList.get(i).setIsChecked(false);
            }
            priceRangeList.get(position).setIsChecked(true);
            selectedPriceRangeId = priceRangeList.get(position).getId();
        }else {
            selectedPriceRangeId="";
            priceRangeList.get(position).setIsChecked(false);
        }

        recyPriceRange.post(new Runnable() {
            @Override
            public void run() {
                priceRangeAdapter.notifyDataSetChanged();
            }
        });

    }

    @Override
    public void OnPropertyTypeClicked(int position) {
        //LoadDBookingData();
        dialog.dismiss();
        propertyTypeId = ""+propertyTypeList.get(position).getId();
        presenter.getRecommendedProperty(getActivity(), propertyTypeId, selectedPriceRangeId, ((MainActivity)getActivity()).userData.getACCESS_TOKEN());
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.relaySearch:
                Fragment fragment1 = new SearchByInstituteFragment();
                Bundle bundle1 = new Bundle();
                bundle1.putString("propertyTypeId", propertyTypeId);
                fragment1.setArguments(bundle1);
                AppUtils.goFragmentAddWithoutBackStack(getContext(), fragment1);
                break;

            case R.id.txtSearchByLocation:
                Fragment fragment = new SearchByLocationFragment();
                Bundle bundle = new Bundle();
                bundle.putString("propertyTypeId", propertyTypeId);
                bundle.putString("rangeId", "");
                fragment.setArguments(bundle);
                AppUtils.goFragmentAddWithoutBackStack(getContext(), fragment);
                break;

            case R.id.imgBack:
                ((MainActivity)getActivity()).ChangeBottobar(((MainActivity)getActivity()).binding.consHome, new HomeFragment());
                break;

            case R.id.imgSorting:
                if (priceRangeList.size()>0){
                    showPriceRangeDialog(getActivity());
                }else {
                    presenter.getPriceRange(getActivity(), ((MainActivity)getActivity()).userData.getACCESS_TOKEN());
                }
                break;

            case R.id.imgFilter:
                if (propertyTypeList.size()>0){
                    showSelectServiceDialog(getActivity());
                }else {
                    presenter.getPropertyType(getActivity(), ((MainActivity) getActivity()).userData.getACCESS_TOKEN());
                }
                break;
            default:
                break;
        }
    }

    public void showPriceRangeDialog(Activity activity){

        TextView txtSetRange;
        ImageView imgCross;

        dialogPrice = new Dialog(activity);
        dialogPrice.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogPrice.setCancelable(true);
        dialogPrice.setContentView(R.layout.dialog_price);


        recyPriceRange = dialogPrice.findViewById(R.id.recyPriceRange);
        txtSetRange = dialogPrice.findViewById(R.id.txtSet);
        imgCross = dialogPrice.findViewById(R.id.imgCross);

        priceRangeAdapter = new PriceRangeAdapter(getActivity(), priceRangeList, this);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 1, LinearLayoutManager.VERTICAL, false);
        recyPriceRange.setLayoutManager(gridLayoutManager);
        recyPriceRange.setItemAnimator(new DefaultItemAnimator());
        recyPriceRange.setAdapter(priceRangeAdapter);

        txtSetRange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogPrice.dismiss();
                presenter.getRecommendedProperty(getActivity(), propertyTypeId, selectedPriceRangeId, ((MainActivity)getActivity()).userData.getACCESS_TOKEN());
            }
        });

        imgCross.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogPrice.dismiss();
            }
        });

        dialogPrice.show();
        Window window = dialogPrice.getWindow();
        window.setLayout(ConstraintLayout.LayoutParams.MATCH_PARENT, ConstraintLayout.LayoutParams.WRAP_CONTENT);

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
    public void onRecommendedSuccess(RecommendedResBean item) {
        recommendedList.clear();
        if (item.isStatus()){
            recommendedList.addAll(item.getData());
        }else {
            ((MainActivity)getActivity()).toast("Recommended hostels list not found");
        }
        recommendedHostelAdapter.notifyDataSetChanged();
        binding.recyHostel.setLayoutAnimation(animation);
    }

    @Override
    public void onPriceRangeSuccess(PriceRangeResBean item) {
        if (item.isStatus()){
            priceRangeList.clear();
            priceRangeList.addAll(item.getData());
            showPriceRangeDialog(getActivity());
        }else {
            ((MainActivity)getActivity()).toast("Price range list not found");
        }
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
    public void enableLoadingBar(Context context, boolean enable) {
        ((MainActivity)getActivity()).enableLoadingBar(context,enable);
    }

    @Override
    public void onError(String reason) {
        ((MainActivity)getActivity()).toast(reason);
    }

}
