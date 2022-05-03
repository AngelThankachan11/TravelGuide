package com.travel.guide;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.travel.guide.databinding.ActivityProfileBinding;
import com.travel.guide.adapters.ProfileBtnAdapter;
import com.travel.guide.apicalls.api.ApiConstants;
import com.travel.guide.apicalls.model.ProfileResBean;
import com.travel.guide.apicalls.presenter.ProfilePresenter;
import com.travel.guide.apicalls.viewclass.IProfileView;
import com.travel.guide.utils.SharedPreferenceData;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class ProfileActivity extends BaseActivity implements ProfileBtnAdapter.ItemClickListener, View.OnClickListener, IProfileView {

    ActivityProfileBinding binding;
    ProfileBtnAdapter profileBtnAdapter;
    List<String> profileBtnList = new ArrayList<>();
    List<Integer> profileBtnIconList = new ArrayList<>();
    SharedPreferenceData userData;
    int LAUNCH_SECOND_ACTIVITY = 1;
    ProfilePresenter presenter;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_profile);
        userData = new SharedPreferenceData(this);
        presenter = new ProfilePresenter();
        presenter.setView(this);

        LoadData();

        profileBtnAdapter = new ProfileBtnAdapter(this, profileBtnList, profileBtnIconList, this);
        GridLayoutManager gridLayoutManager2 = new GridLayoutManager(this, 1, LinearLayoutManager.VERTICAL, false);
        binding.recyProfileBtn.setLayoutManager(gridLayoutManager2);
        binding.recyProfileBtn.setItemAnimator(new DefaultItemAnimator());
        binding.recyProfileBtn.setAdapter(profileBtnAdapter);

        presenter.getProfileInfo(this, userData.getACCESS_TOKEN());

        binding.imgBack.setOnClickListener(this);
        binding.txtLogout.setOnClickListener(this);
    }

    public void LoadData(){
        //profileBtnList.add("Offers");
        profileBtnList.add("Wishlist");
        profileBtnList.add("Refer & Earn");
       /* profileBtnList.add("Recently Visited");
        profileBtnList.add("FAQs");
        profileBtnList.add("Terms and Conditions");*/

        //profileBtnIconList.add(R.drawable.ic_offers);
        profileBtnIconList.add(R.drawable.ic_wishlist);
        profileBtnIconList.add(R.drawable.ic_refer_earn_new);
        /*profileBtnIconList.add(R.drawable.ic_recently_visited);
        profileBtnIconList.add(R.drawable.ic_faq);
        profileBtnIconList.add(R.drawable.ic_term_condition);*/
    }

    @Override
    public void OnItemClicked(int Position) {
        /*if (Position==0){
            startActivity(new Intent(this, OfferActivity.class));
        }else */if (Position==0){
            startActivity(new Intent(this, WishlistActivity.class));
        }else if (Position==1){
            startActivity(new Intent(this, ReferandEarnActivity.class));
        }/*else if (Position==3){
            startActivity(new Intent(this, RecentlyVisitedActivity.class));
        }else if (Position==4){
            startActivity(new Intent(this, FAQsActivity.class));
        }else if (Position==5){
            startActivity(new Intent(this, TermsConditionsActivity.class));
        }*/
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.imgBack:
                finish();
                break;
            case R.id.txtLogout:
                (new SharedPreferenceData(this)).Logout();
                Intent intent = new Intent(this, LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
                break;
            case R.id.consEditProfile:
                Intent intent1 = new Intent(ProfileActivity.this, EditProfileActivity.class);
                startActivityForResult(intent1, LAUNCH_SECOND_ACTIVITY);
                break;
            default:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == LAUNCH_SECOND_ACTIVITY) {
            if (resultCode == Activity.RESULT_CANCELED) {
                presenter.getProfileInfo(this, userData.getACCESS_TOKEN());
            }
        }
    }

    @Override
    public void onProfileSuccess(ProfileResBean item) {
        if (item.isStatus()){
            Picasso.get().load(ApiConstants.BASE_IMAGE_URL+item.getData().getProfileImage()).into(binding.imgProfile);
            binding.txtUserName.setText(item.getData().getName());
            binding.txtPhone.setText(item.getData().getMobile());
            binding.txtEmail.setText(item.getData().getEmail());

            userData.SavedProfileData(item);
        }else {
            toast("Unable to get profile info");
        }
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
