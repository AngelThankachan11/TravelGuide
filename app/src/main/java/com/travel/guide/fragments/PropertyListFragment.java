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

import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.travel.guide.databinding.FragmentPropertyListBinding;
import com.travel.guide.MainActivity;
import com.travel.guide.PropertyDetailsActivity;
import com.travel.guide.R;
import com.travel.guide.adapters.PopularPropertyListAdapter;
import com.travel.guide.adapters.PropertyListAdapter;
import com.travel.guide.adapters.SelectNeedsAdapter;
import com.travel.guide.apicalls.model.HomeDataResBean;
import com.travel.guide.apicalls.model.PropertyTypeResBean;
import com.travel.guide.apicalls.model.SearchByInsResBean;
import com.travel.guide.apicalls.presenter.PropertyListPresenter;
import com.travel.guide.apicalls.viewclass.IPropertyListView;

import java.util.ArrayList;
import java.util.List;

public class PropertyListFragment extends Fragment implements PropertyListAdapter.ItemClickListener, PopularPropertyListAdapter.PopularItemClickListener
        , View.OnClickListener, IPropertyListView, SelectNeedsAdapter.PropertTypeClickListener {

    FragmentPropertyListBinding binding;

    List<SearchByInsResBean.DataItem> propertyList = new ArrayList<>();
    List<SearchByInsResBean.DataItem> tempPropertyList = new ArrayList<>();
    List<HomeDataResBean.ServicePropertyItem> popularList = new ArrayList<>();
    List<HomeDataResBean.ServicePropertyItem> tempPopularList = new ArrayList<>();
    PropertyListAdapter propertyListAdapter;
    PopularPropertyListAdapter popularPropertyListAdapter;
    String from;
    PropertyListPresenter presenter;
    List<PropertyTypeResBean.DataItem> propertyTypeList = new ArrayList<>();
    Dialog dialog;

    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(layoutInflater, container, savedInstanceState);
        binding = DataBindingUtil.inflate(layoutInflater, R.layout.fragment_property_list, container, false);
        presenter = new PropertyListPresenter();
        presenter.setView(this);


        from = getArguments().getString("from");
        if (from.equalsIgnoreCase("SearchByIns")) {
            propertyList = (List<SearchByInsResBean.DataItem>) getArguments().getSerializable("propertyData");
            tempPropertyList.addAll(propertyList);

            propertyListAdapter = new PropertyListAdapter(getActivity(), tempPropertyList, this);
            GridLayoutManager gridLayoutManager2 = new GridLayoutManager(getContext(), 1, LinearLayoutManager.VERTICAL, false);
            binding.recyProperty.setLayoutManager(gridLayoutManager2);
            binding.recyProperty.setItemAnimator(new DefaultItemAnimator());
            binding.recyProperty.setAdapter(propertyListAdapter);
        }else {
            popularList = (List<HomeDataResBean.ServicePropertyItem>) getArguments().getSerializable("propertyData");
            tempPopularList.addAll(popularList);

            popularPropertyListAdapter = new PopularPropertyListAdapter(getActivity(), tempPopularList, this);
            GridLayoutManager gridLayoutManager2 = new GridLayoutManager(getContext(), 1, LinearLayoutManager.VERTICAL, false);
            binding.recyProperty.setLayoutManager(gridLayoutManager2);
            binding.recyProperty.setItemAnimator(new DefaultItemAnimator());
            binding.recyProperty.setAdapter(popularPropertyListAdapter);
        }


        int resId = R.anim.layout_animation;
        LayoutAnimationController animation = AnimationUtils.loadLayoutAnimation(getContext(), resId);
        binding.recyProperty.setLayoutAnimation(animation);

        binding.imgBack.setOnClickListener(this);
        binding.imgFilter.setOnClickListener(this);

        return binding.getRoot();
    }

    @Override
    public void OnItemClicked(int Position) {
        Intent intent = new Intent(getActivity(), PropertyDetailsActivity.class);
        intent.putExtra("propertyId", ""+tempPropertyList.get(Position).getId());
        intent.putExtra("couponId", "");
        getActivity().startActivity(intent);
    }

    @Override
    public void OnPopularItemClicked(int position) {
        Intent intent = new Intent(getActivity(), PropertyDetailsActivity.class);
        intent.putExtra("propertyId", ""+tempPopularList.get(position).getId());
        intent.putExtra("couponId", "");
        getActivity().startActivity(intent);
    }

    @Override
    public void OnPropertyTypeClicked(int position) {
        dialog.dismiss();
        String propertyTypeId = ""+propertyTypeList.get(position).getId();
        if (from.equalsIgnoreCase("SearchByIns")) {
            tempPropertyList.clear();
            for (SearchByInsResBean.DataItem dataItem : propertyList) {
                //or use .equal(text) with you want equal match
                //use .toLowerCase() for better matches
                if (("" + dataItem.getBusinesscat().getId()).contains(propertyTypeId)) {
                    tempPropertyList.add(dataItem);
                }
            }
            propertyListAdapter.notifyDataSetChanged();
        }else {
            tempPopularList.clear();
            for (HomeDataResBean.ServicePropertyItem dataItem : popularList) {
                //or use .equal(text) with you want equal match
                //use .toLowerCase() for better matches
                if (("" + dataItem.getBusinesscat().getId()).contains(propertyTypeId)) {
                    tempPopularList.add(dataItem);
                }
            }
            popularPropertyListAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.imgBack:
                getParentFragmentManager().popBackStack();
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

    @Override
    public void onPropertyTypeSuccess(PropertyTypeResBean item) {
        if (item.isStatus()){
            propertyTypeList.addAll(item.getData());
            showSelectServiceDialog(getActivity());
        }else {
            ((MainActivity)getActivity()).toast("No property types found");
        }
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
    public void enableLoadingBar(Context context, boolean enable) {
        ((MainActivity)getActivity()).enableLoadingBar(context, enable);
    }

    @Override
    public void onError(String reason) {

    }
}


