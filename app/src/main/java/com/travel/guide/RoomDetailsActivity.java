package com.travel.guide;

import android.os.Bundle;
import android.view.View;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.travel.guide.databinding.ActivityRoomDetailsBinding;
import com.travel.guide.adapters.RoomTypeAdapter;
import com.travel.guide.apicalls.model.RoomDetailsResBean;

import java.util.List;

public class RoomDetailsActivity extends BaseActivity {

    List<RoomDetailsResBean.DataItem> roomData;
    ActivityRoomDetailsBinding binding;
    RoomTypeAdapter roomTypeAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_room_details);
        roomData = (List<RoomDetailsResBean.DataItem>) getIntent().getSerializableExtra("roomDetails");

        roomTypeAdapter = new RoomTypeAdapter(this, roomData);
        GridLayoutManager gridLayoutManager1 = new GridLayoutManager(this, 1, LinearLayoutManager.VERTICAL, false);
        binding.recyRoomType.setLayoutManager(gridLayoutManager1);
        binding.recyRoomType.setItemAnimator(new DefaultItemAnimator());
        binding.recyRoomType.setAdapter(roomTypeAdapter);
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
