package com.travel.guide;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;

import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.travel.guide.databinding.ActivityPropertyListBinding;
import com.travel.guide.adapters.OfferPropertyListAdapter;
import com.travel.guide.adapters.PopularPropertyListAdapter;
import com.travel.guide.adapters.SelectNeedsAdapter;
import com.travel.guide.apicalls.model.OfferResBean;
import com.travel.guide.apicalls.model.PropertyTypeResBean;
import com.travel.guide.apicalls.presenter.PropertyListPresenter;
import com.travel.guide.apicalls.viewclass.IPropertyListView;
import com.travel.guide.utils.SharedPreferenceData;

import java.util.ArrayList;
import java.util.List;

public class PropertyListActivity extends BaseActivity implements OfferPropertyListAdapter.ItemClickListener, View.OnClickListener, IPropertyListView,
        SelectNeedsAdapter.PropertTypeClickListener {

    ActivityPropertyListBinding binding;

    List<OfferResBean.DataItem.GrabNowItem> propertyList = new ArrayList<>();
    List<OfferResBean.DataItem.GrabNowItem> tempPropertyList = new ArrayList<>();
    OfferPropertyListAdapter propertyListAdapter;
    PopularPropertyListAdapter popularPropertyListAdapter;
    String from, couponId;
    PropertyListPresenter presenter;
    SharedPreferenceData userData;
    Dialog dialog;
    List<PropertyTypeResBean.DataItem> propertyTypeList = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_property_list);
        presenter = new PropertyListPresenter();
        presenter.setView(this);
        userData = new SharedPreferenceData(this);

        propertyList = (List<OfferResBean.DataItem.GrabNowItem>) getIntent().getSerializableExtra("propertyData");
        couponId = getIntent().getStringExtra("couponId");
        tempPropertyList.addAll(propertyList);

        propertyListAdapter = new OfferPropertyListAdapter(this, tempPropertyList, this);
        GridLayoutManager gridLayoutManager2 = new GridLayoutManager(this, 1, LinearLayoutManager.VERTICAL, false);
        binding.recyProperty.setLayoutManager(gridLayoutManager2);
        binding.recyProperty.setItemAnimator(new DefaultItemAnimator());
        binding.recyProperty.setAdapter(propertyListAdapter);

        int resId = R.anim.layout_animation;
        LayoutAnimationController animation = AnimationUtils.loadLayoutAnimation(this, resId);
        binding.recyProperty.setLayoutAnimation(animation);

    }

    @Override
    public void OnItemClicked(int Position) {
        Intent intent = new Intent(this, PropertyDetailsActivity.class);
        intent.putExtra("propertyId", ""+tempPropertyList.get(Position).getId());
        intent.putExtra("couponId", couponId);
        startActivity(intent);
    }

    @Override
    public void OnPropertyTypeClicked(int position) {
        dialog.dismiss();
        String propertyTypeId = ""+propertyTypeList.get(position).getId();

        tempPropertyList.clear();
        for (OfferResBean.DataItem.GrabNowItem dataItem : propertyList) {
            if (("" + dataItem.getBusinesscat().getId()).contains(propertyTypeId)) {
                tempPropertyList.add(dataItem);
            }
        }
        propertyListAdapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.imgBack:
                finish();
                break;
            case R.id.imgFilter:
                if (propertyTypeList.size()>0){
                    showSelectServiceDialog(this);
                }else {
                    presenter.getPropertyType(this, userData.getACCESS_TOKEN());
                }
                break;
            default:
                break;
        }
    }

    public void showSelectServiceDialog(Activity activity){
        dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.filter_dialog);

        RecyclerView recyPropertyType = dialog.findViewById(R.id.recyPropertyType);
        CardView cardCross = dialog.findViewById(R.id.cardCross);

        SelectNeedsAdapter selectNeedsAdapter = new SelectNeedsAdapter(this, propertyTypeList, this);
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
    public void onPropertyTypeSuccess(PropertyTypeResBean item) {
        if (item.isStatus()){
            propertyTypeList.addAll(item.getData());
            showSelectServiceDialog(this);
        }else {
            toast("No property types found");
        }
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void onError(String reason) {

    }
}


