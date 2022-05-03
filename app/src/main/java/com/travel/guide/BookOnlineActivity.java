package com.travel.guide;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.text.Html;
import android.view.View;
import android.view.Window;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.viewpager.widget.ViewPager;

import com.travel.guide.databinding.ActivityBookOnlineBinding;
import com.travel.guide.adapters.RoomTypeCardAdapter;
import com.travel.guide.adapters.ViewPagerAdapter;
import com.travel.guide.apicalls.api.ApiConstants;
import com.travel.guide.apicalls.model.BannerResBean;
import com.travel.guide.apicalls.model.CouponAppliedResBean;
import com.travel.guide.apicalls.model.RoomBookingResBean;
import com.travel.guide.apicalls.model.RoomDetailsResBean;
import com.travel.guide.apicalls.presenter.BookOnlinePresenter;
import com.travel.guide.apicalls.viewclass.IBookOnlineView;
import com.travel.guide.utils.SharedPreferenceData;
import com.google.android.material.snackbar.Snackbar;
import com.razorpay.Checkout;
import com.razorpay.PaymentData;
import com.razorpay.PaymentResultWithDataListener;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

public class BookOnlineActivity extends BaseActivity implements PaymentResultWithDataListener, IBookOnlineView, RoomTypeCardAdapter.ItemClickListener, ViewPagerAdapter.ItemClickListener {

    ActivityBookOnlineBinding binding;
    String selectedRoomPrice, getSelectedRoomId, getSelectedRoomTypeId;
    RoomTypeCardAdapter roomTypeCardAdapter;
    final Calendar myCalendar = Calendar.getInstance();
    SharedPreferenceData userData;
    BookOnlinePresenter presenter;

    String propertyId, from;
    List<RoomDetailsResBean.DataItem> roomDetails = new ArrayList<>();
    String walletAmount;
    List<String> bannerImageList = new ArrayList<>();
    private ViewPagerAdapter viewPagerAdapter;
    private int dotscount;
    private ImageView[] dots;
    int currentPage = 0;
    Timer timer;
    final long DELAY_MS = 500;//delay in milliseconds before task is to be executed
    final long PERIOD_MS = 3000;
    DatePickerDialog.OnDateSetListener date;
    boolean isWalletUsed = false;
    int LAUNCH_SECOND_ACTIVITY = 1, usedWalletAmount=0;
    String couponCode="", couponAmount="0";
    String payableAmount;
    private List<BannerResBean.BannerItem> bannerItemList = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_book_online);
        userData = new SharedPreferenceData(this);
        presenter = new BookOnlinePresenter();
        presenter.setView(this);

        viewPagerAdapter = new ViewPagerAdapter(bannerImageList, getContext(), this);
        binding.viewPager.setAdapter(viewPagerAdapter);

        propertyId = getIntent().getStringExtra("propertyId");
        from = getIntent().getStringExtra("from");
        roomDetails = (List<RoomDetailsResBean.DataItem>)getIntent().getSerializableExtra("roomDetails");
        walletAmount = getIntent().getStringExtra("walletAmount");
        couponCode = getIntent().getStringExtra("couponCode");

        int selectedPosition=0;

        for (int i=0; i<roomDetails.size(); i++) {
            if (roomDetails.get(i).getIsSelected()){
                selectedPosition=i;
            }
        }

        binding.txtPrice.setText(Html.fromHtml("Price - \u20B9 <font color='#06A6AC'>"+roomDetails.get(selectedPosition).getPrice()+"</font>/ Month"));
        binding.txtPricePay.setText("\u20B9"+roomDetails.get(selectedPosition).getPrice());


        selectedRoomPrice = roomDetails.get(selectedPosition).getPrice();
        payableAmount = selectedRoomPrice;
        getSelectedRoomId = ""+roomDetails.get(selectedPosition).getId();
        getSelectedRoomTypeId = ""+roomDetails.get(selectedPosition).getRoomType();

        binding.edtName.setText(userData.getUser_name());
        binding.edtMobile.setText(userData.getMobile_no());
        binding.txtRoomType.setText(roomDetails.get(selectedPosition).getName());

        Picasso.get().load(ApiConstants.BASE_IMAGE_URL+roomDetails.get(selectedPosition).getDefaultIcon()).into(binding.imgIcon);
        Picasso.get().load(ApiConstants.BASE_IMAGE_URL+roomDetails.get(selectedPosition).getIcon()).into(binding.imgDefaultIcon);
        binding.txtWallet.setText(Html.fromHtml("Use your wallet Balalnce <font color='#FF0000'>\u20B9"+walletAmount+"</font>"));

        roomTypeCardAdapter = new RoomTypeCardAdapter(this, roomDetails, this);
        GridLayoutManager gridLayoutManager1 = new GridLayoutManager(this, 2, LinearLayoutManager.VERTICAL, false);
        binding.recyRoomType.setLayoutManager(gridLayoutManager1);
        binding.recyRoomType.setItemAnimator(new DefaultItemAnimator());
        binding.recyRoomType.setAdapter(roomTypeCardAdapter);

        date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                String myFormat = "yyyy-MM-dd"; //In which you need put here
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
                binding.txtVisitingDate.setText(sdf.format(myCalendar.getTime()));
            }

        };

        binding.chkWallet.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked){
                    isWalletUsed = true;
                    usedWalletAmount = Integer.parseInt(walletAmount);
                    diductCouponAmount();
                }else {
                    isWalletUsed = false;
                    usedWalletAmount = 0;
                    diductCouponAmount();
                }
            }
        });

        presenter.getBanners(this, "recent", userData.getACCESS_TOKEN());

    }

    @Override
    public void onBannerClick(int position) {
        Intent intent = new Intent(this, BannerDetailsActivity.class);
        intent.putExtra("bannerData", (Serializable) bannerItemList.get(position));
        intent.putExtra("from", "banner");
        startActivity(intent);
    }

    @Override
    public void OnRoomTypeCardClicked(int Position, boolean isSelected) {
        for (int i=0; i<roomDetails.size(); i++){
            roomDetails.get(i).setSelected(false);
        }
        if (isSelected){
            roomDetails.get(Position).setSelected(true);
            binding.txtPrice.setText(Html.fromHtml("Price - \u20B9 <font color='#06A6AC'>"+roomDetails.get(Position).getPrice()+"</font>/ Month"));
            binding.txtRoomType.setText(roomDetails.get(Position).getName());
            Picasso.get().load(ApiConstants.BASE_IMAGE_URL+roomDetails.get(Position).getDefaultIcon()).into(binding.imgIcon);
            Picasso.get().load(ApiConstants.BASE_IMAGE_URL+roomDetails.get(Position).getIcon()).into(binding.imgDefaultIcon);
            selectedRoomPrice = roomDetails.get(Position).getPrice();
            payableAmount = selectedRoomPrice;
            getSelectedRoomId = ""+roomDetails.get(Position).getId();
            getSelectedRoomTypeId = ""+roomDetails.get(Position).getRoomType();
            diductCouponAmount();
        }
        binding.recyRoomType.post(new Runnable() {
            @Override
            public void run() {
                roomTypeCardAdapter.notifyDataSetChanged();
            }
        });
    }

    public void onClick(View view){
        switch (view.getId()){
            case R.id.imgBack:
                finish();
                break;
            case R.id.cardBookNow:
                if (binding.edtName.getText().toString().trim().isEmpty()){
                    toast("Please enter name");
                }else if (binding.txtVisitingDate.getText().toString().trim().isEmpty() || binding.txtVisitingDate.getText().toString()
                        .equalsIgnoreCase(getResources().getString(R.string.visiting_date))){
                    toast("Please select visiting date");
                }else {
                    RAZORPAY(payableAmount);
                }
                break;

            case R.id.cardOneKiloMeter:
                DatePickerDialog datePicker = new DatePickerDialog(BookOnlineActivity.this, date, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH));
                datePicker.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                datePicker.show();
                break;

            case R.id.cardOffer:
                Intent i = new Intent(this, CouponActivity.class);
                i.putExtra("amount", selectedRoomPrice);
                startActivityForResult(i, LAUNCH_SECOND_ACTIVITY);
                break;
            default:
                break;
        }
    }

    private void RAZORPAY(String price) {

        double finalPay = Double.parseDouble(price) * 100;
        final Checkout co = new Checkout();

        String stamount = String.valueOf(finalPay);
        try {
            JSONObject options = new JSONObject();
            options.put("name", userData.getUser_name());
            options.put("description", getString(R.string.app_name));
            options.put("currency", "INR");
            options.put("amount", stamount);
            JSONObject preFill = new JSONObject();
            preFill.put("email", userData.getEmail());
            preFill.put("contact", userData.getMobile_no());
            options.put("prefill", preFill);
            co.setImage(R.drawable.app_logo);
            co.open(this, options);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == LAUNCH_SECOND_ACTIVITY) {
            if(resultCode == Activity.RESULT_OK){
                couponCode = data.getStringExtra("couponCode");
                couponAmount = data.getStringExtra("couponAmount");
                binding.txtOfferDescription.setText("Get "+couponAmount+" off on your booking order\n with code - "+couponCode);
                diductCouponAmount();
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                // Write your code if there's no result
            }
        }
    }

    public void diductCouponAmount(){
        payableAmount = ""+(Integer.parseInt(selectedRoomPrice) - Integer.parseInt(couponAmount) - usedWalletAmount);
        binding.txtPricePay.setText("\u20B9"+payableAmount);
    }

    @Override
    public void onPaymentSuccess(String s, PaymentData paymentData) {
        Snackbar snackbar = Snackbar.make(binding.consRoot, "Payment Successful: " + paymentData.getPaymentId(), Snackbar.LENGTH_LONG);
        snackbar.show();
        String walletKey = "0";
        if (isWalletUsed){
            walletKey = "1";
        }
        presenter.callRoomBooking(BookOnlineActivity.this, propertyId, binding.edtName.getText().toString(), binding.txtVisitingDate.getText().toString()
                , binding.edtMobile.getText().toString(), getSelectedRoomId, getSelectedRoomTypeId, from, walletKey, couponCode, couponAmount, paymentData.getPaymentId(), userData.getACCESS_TOKEN());
    }

    @Override
    public void onPaymentError(int i, String s, PaymentData paymentData) {
        toast(s);
    }

    @Override
    public void onBookOnlineSuccess(RoomBookingResBean item) {
        toast(item.getMessage());
        if (item.isStatus()){
            //showBookingSuccessDialog(this, item.getData());
            Intent intent = new Intent(BookOnlineActivity.this, BookingConfimationActivity.class);
            intent.putExtra("successDate", (Serializable) item.getData());
            intent.putExtra("from", "Online");
            startActivity(intent);
            finish();
        }
    }

    @Override
    public void onBannerSuccess(BannerResBean item) {
        if (item.isStatus()){
            bannerItemList.addAll(item.getBanner());
            for (int i=0; i<item.getBanner().size(); i++){
                bannerImageList.add(item.getBanner().get(i).getImage());
            }
            viewPagerAdapter.notifyDataSetChanged();
            LoadImageSlider();
        }else{
            toast("Banner not found for RecentlyVisited section");
        }
        if (!couponCode.equalsIgnoreCase("")){
            presenter.couponApplied(this, couponCode, selectedRoomPrice, userData.getACCESS_TOKEN());
        }
    }

    @Override
    public void onCouponAppliedSuccess(CouponAppliedResBean item) {
        couponAmount = item.getMinusAmount();
        binding.txtOfferDescription.setText("Get "+couponAmount+" off on your booking order\n with code - "+couponCode);
        diductCouponAmount();
    }

    public void LoadImageSlider(){
        dotscount = viewPagerAdapter.getCount();
        dots = new ImageView[dotscount];

        for(int i = 0; i < dotscount; i++) {

            dots[i] = new ImageView(getContext());
            dots[i].setImageDrawable(ContextCompat.getDrawable(getContext().getApplicationContext(), R.drawable.nonactive_dot));

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

            params.setMargins(8, 0, 8, 0);

            binding.SliderDots.addView(dots[i], params);
        }

        dots[0].setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.active_dot));

        binding.viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                try {
                    for (int i = 0; i < dotscount; i++) {
                        dots[i].setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.nonactive_dot));
                    }
                    dots[position].setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.active_dot));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        /*After setting the adapter use the timer */
        final Handler handler = new Handler();
        final Runnable Update = new Runnable() {
            public void run() {
                if (currentPage == (dotscount - 1)) {
                    currentPage = -1;
                }
                currentPage = currentPage + 1;
                binding.viewPager.setCurrentItem(currentPage);
            }
        };
        timer = new Timer(); // This will create a new Thread
        timer.schedule(new TimerTask() { // task to be scheduled
            @Override
            public void run() {
                handler.post(Update);
            }
        }, DELAY_MS, PERIOD_MS);
    }

    public void showBookingSuccessDialog(Activity activity, RoomBookingResBean.Data roomBookingData){

        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.dialog_booking_result);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        TextView txtCustomerName = dialog.findViewById(R.id.txtCustomerName);
        TextView txtRoomType = dialog.findViewById(R.id.txtRoomType);
        TextView txtBookingId = dialog.findViewById(R.id.txtBookingId);
        TextView txtVisitingDate = dialog.findViewById(R.id.txtVisitingDate);
        CardView cardOk = dialog.findViewById(R.id.cardOk);

        txtCustomerName.setText(roomBookingData.getUserName());
        txtBookingId.setText(""+roomBookingData.getId());
        txtVisitingDate.setText(roomBookingData.getBookingDate());

        cardOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                finish();
            }
        });

        dialog.show();

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
