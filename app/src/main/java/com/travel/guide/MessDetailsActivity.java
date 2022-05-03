package com.travel.guide;

import android.os.Bundle;
import android.view.View;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.travel.guide.databinding.ActivityMessDetailsBinding;
import com.travel.guide.adapters.MessImageAdapter;
import com.travel.guide.apicalls.model.MessDetailsResBean;

import java.util.List;

public class MessDetailsActivity extends BaseActivity{

    List<MessDetailsResBean.DataItem> messData;
    ActivityMessDetailsBinding binding;
    MessImageAdapter messImageAdapter;
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_mess_details);
        messData = (List<MessDetailsResBean.DataItem>) getIntent().getSerializableExtra("messDetails");

        if (messData.get(0).getBreakFastFrom()!=null && messData.get(0).getBreakFastFrom()!=null) {
            binding.txtBreakfastTime.setText(messData.get(0).getBreakFastFrom() + "-" + messData.get(0).getBreakFastTo());
        }else {
            binding.consBreakfastTime.setVisibility(View.GONE);
        }
        if (messData.get(0).getLunchFrom()!=null && messData.get(0).getLunchTo()!=null) {
            binding.txtLunchTime.setText(messData.get(0).getLunchFrom() + "-" + messData.get(0).getLunchTo());
        }else {
        binding.consLunchTime.setVisibility(View.GONE);
        }
        if (messData.get(0).getTeaFrom()!=null && messData.get(0).getTeaTo()!=null) {
            binding.txtTeaTime.setText(messData.get(0).getTeaFrom() + "-" + messData.get(0).getTeaTo());
        }else {
            binding.consTeaTime.setVisibility(View.GONE);
        }
        if (messData.get(0).getDinnerFrom()!=null && messData.get(0).getDinnerTo()!=null) {
            binding.txtDinnerTime.setText(messData.get(0).getDinnerFrom() + "-" + messData.get(0).getDinnerTo());
        }else {
            binding.consDinnerTime.setVisibility(View.GONE);
        }
        if (messData.get(0).getMilkFrom()!=null && messData.get(0).getMilkTo()!=null) {
            binding.txtMilkTime.setText(messData.get(0).getMilkFrom() + "-" + messData.get(0).getMilkTo());
        }else {
            binding.consMilkTime.setVisibility(View.GONE);
        }

        messImageAdapter = new MessImageAdapter(this, messData.get(0).getMessImage());
        GridLayoutManager gridLayoutManager2 = new GridLayoutManager(this, 1, LinearLayoutManager.VERTICAL, false);
        binding.recyRoomImage.setLayoutManager(gridLayoutManager2);
        binding.recyRoomImage.setItemAnimator(new DefaultItemAnimator());
        binding.recyRoomImage.setAdapter(messImageAdapter);
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
