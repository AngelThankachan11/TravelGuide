package com.travel.guide;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.travel.guide.databinding.ActivityWishlistBinding;
import com.travel.guide.adapters.WishlistAdapter;
import com.travel.guide.apicalls.model.WishlistResBean;
import com.travel.guide.apicalls.presenter.WishlistPresenter;
import com.travel.guide.apicalls.viewclass.IWishlistView;
import com.travel.guide.utils.SharedPreferenceData;

import java.util.ArrayList;
import java.util.List;

public class WishlistActivity extends BaseActivity implements WishlistAdapter.ItemClickListener, IWishlistView, View.OnClickListener {

    ActivityWishlistBinding binding;
    WishlistAdapter wishlistAdapter;

    List<WishlistResBean.DataItem> propertyList = new ArrayList<>();
    WishlistPresenter presenter;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_wishlist);
        presenter = new WishlistPresenter();
        presenter.setView(this);

        wishlistAdapter = new WishlistAdapter(this, propertyList, this);
        GridLayoutManager gridLayoutManager2 = new GridLayoutManager(this, 1, LinearLayoutManager.VERTICAL, false);
        binding.recyProperty.setLayoutManager(gridLayoutManager2);
        binding.recyProperty.setItemAnimator(new DefaultItemAnimator());
        binding.recyProperty.setAdapter(wishlistAdapter);

        presenter.getMyWishlist(this, (new SharedPreferenceData(this)).getACCESS_TOKEN());

        binding.imgBack.setOnClickListener(this);
    }

    @Override
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
    public void OnItemClicked(int position) {
        Intent intent = new Intent(this, PropertyDetailsActivity.class);
        intent.putExtra("propertyId", ""+propertyList.get(position).getId());
        intent.putExtra("couponId", "");
        startActivity(intent);
    }

    @Override
    public void onWishListSuccess(WishlistResBean item) {
        propertyList.clear();
        if (item.isStatus()){
            binding.txtNoData.setVisibility(View.GONE);
            propertyList.addAll(item.getData());
        }else {
            binding.txtNoData.setVisibility(View.VISIBLE);
        }
        wishlistAdapter.notifyDataSetChanged();
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
