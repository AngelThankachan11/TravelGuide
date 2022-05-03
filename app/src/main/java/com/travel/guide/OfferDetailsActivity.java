package com.travel.guide;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.travel.guide.databinding.ActivityBannerDetailsBinding;
import com.travel.guide.apicalls.api.ApiConstants;
import com.travel.guide.apicalls.model.ExploreMoreResBean;
import com.travel.guide.apicalls.model.OfferResBean;
import com.squareup.picasso.Picasso;

import java.io.Serializable;

public class OfferDetailsActivity extends AppCompatActivity {

    ActivityBannerDetailsBinding binding;
    OfferResBean.DataItem offerData;
    ExploreMoreResBean.DataItem exploreData;
    String from, image, title, description;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_banner_details);

        offerData = (OfferResBean.DataItem) getIntent().getSerializableExtra("offerData");
        image = offerData.getBanner();
        title = offerData.getTitle();
        description = offerData.getDescription();

        Picasso.get().load(ApiConstants.BASE_IMAGE_URL+image).into(binding.imgBanner);
        binding.txtTitle.setText(title);
        binding.txtDescription.setText(description);

        binding.txtGrabNow.setVisibility(View.VISIBLE);
    }

    public void onClick(View view){
        switch (view.getId()){
            case R.id.imgBack:
                finish();
                break;
            case R.id.txtGrabNow:
                Intent intent = new Intent(this, PropertyListActivity.class);
                intent.putExtra("propertyData", (Serializable) offerData.getGrabNow());
                intent.putExtra("couponId", ""+offerData.getCouponCode());
                startActivity(intent);
                break;
            default:
                break;
        }
    }
}

