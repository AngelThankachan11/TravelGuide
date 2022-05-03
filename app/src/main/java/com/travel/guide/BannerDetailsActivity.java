package com.travel.guide;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.travel.guide.databinding.ActivityBannerDetailsBinding;
import com.travel.guide.apicalls.api.ApiConstants;
import com.travel.guide.apicalls.model.ExploreMoreResBean;
import com.travel.guide.apicalls.model.HomeDataResBean;
import com.squareup.picasso.Picasso;

public class BannerDetailsActivity extends AppCompatActivity {

    ActivityBannerDetailsBinding binding;
    HomeDataResBean.BannerItem bannerData;
    ExploreMoreResBean.DataItem exploreData;
    String from, image, title, description;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_banner_details);

        from = getIntent().getStringExtra("from");
        if (from.equalsIgnoreCase("banner")){
            bannerData = (HomeDataResBean.BannerItem) getIntent().getSerializableExtra("bannerData");
            image = bannerData.getImage();
            title = bannerData.getTitle();
            description = bannerData.getDescription();

            Picasso.get().load(ApiConstants.BASE_IMAGE_URL+image).into(binding.imgBanner);
            binding.txtTitle.setText(title);
            binding.txtDescription.setText(description);
        }else {
            exploreData = (ExploreMoreResBean.DataItem) getIntent().getSerializableExtra("exploreData");
            image = exploreData.getImage();
            title = exploreData.getTitle();
            description = exploreData.getDescription();

            Picasso.get().load(ApiConstants.BASE_IMAGE_URL+image).into(binding.imgBanner);
            binding.txtTitle.setText(title);
            binding.txtDescription.setText(description);
        }
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
}
