package com.travel.guide;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.databinding.DataBindingUtil;

import com.travel.guide.databinding.ActivityBookingConfirmBinding;
import com.travel.guide.apicalls.api.ApiConstants;
import com.travel.guide.apicalls.model.BookingDetailsResBean;
import com.travel.guide.apicalls.model.RoomBookingResBean;
import com.squareup.picasso.Picasso;

public class BookingConfimationActivity extends BaseActivity{

    RoomBookingResBean.Data bookingData;
    BookingDetailsResBean.Data bookingDetailsData;
    ActivityBookingConfirmBinding binding;
    String from;
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_booking_confirm);

        from = getIntent().getStringExtra("from");
        if (from.equalsIgnoreCase("BookingDetails")){
            bookingDetailsData = (BookingDetailsResBean.Data) getIntent().getSerializableExtra("bookingData");
            if (!from.equalsIgnoreCase("Online")){
                binding.txtTitle.setText("Booking Details");
            }

            binding.txtBookingId.setText(""+bookingDetailsData.getUniqueId());
            binding.txtVisitingDate.setText(bookingDetailsData.getBookingDate());
            binding.txtUserName.setText(bookingDetailsData.getUserName());
            binding.txtDate.setText(bookingDetailsData.getBookingDate());
            binding.txtBookingIdButton.setText("Get room with "+bookingDetailsData.getUniqueId()+" booking id");
            binding.txtPropertyName.setText(bookingDetailsData.getPropertyName());
            binding.txtMobile.setText(bookingDetailsData.getOwnerMobile());
            binding.txtRoomType.setText(bookingDetailsData.getRoomTypeName());
            Picasso.get().load(ApiConstants.BASE_IMAGE_URL+bookingDetailsData.getIcon()).into(binding.imgDefaultIcon);
            Picasso.get().load(ApiConstants.BASE_IMAGE_URL+bookingDetailsData.getDefaultIcon()).into(binding.imgIcon);
            binding.txtFirstMonthFee.setText("\u20B9 "+bookingDetailsData.getTotalAmount());
        }else {
            bookingData = (RoomBookingResBean.Data) getIntent().getSerializableExtra("successDate");
            if (!from.equalsIgnoreCase("Online")){
                binding.txtTitle.setText("Offline Booking Confirm");
            }

            binding.txtBookingId.setText(""+bookingData.getUniqueId());
            binding.txtVisitingDate.setText(bookingData.getBookingDate());
            binding.txtUserName.setText(bookingData.getUserName());
            binding.txtDate.setText(bookingData.getBookingDate());
            binding.txtBookingIdButton.setText("Get room with "+bookingData.getUniqueId()+" booking id");
            binding.txtPropertyName.setText(bookingData.getPropertyName());
            binding.txtMobile.setText(bookingData.getOwnerMobile());
            binding.txtRoomType.setText(bookingData.getRoomTypeName());
            Picasso.get().load(ApiConstants.BASE_IMAGE_URL+bookingData.getIcon()).into(binding.imgDefaultIcon);
            Picasso.get().load(ApiConstants.BASE_IMAGE_URL+bookingData.getDefaltIcon()).into(binding.imgIcon);
            binding.txtFirstMonthFee.setText("\u20B9 "+bookingData.getTotalAmount());
        }

    }

    public void onClick(View view){
        switch (view.getId()){
            case R.id.imgBack:
                if (from.equalsIgnoreCase("BookingDetails")){
                    finish();
                }else {
                    Intent i = new Intent(BookingConfimationActivity.this, MainActivity.class);
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(i);
                }
                break;
            default:
                break;
        }
    }
}
