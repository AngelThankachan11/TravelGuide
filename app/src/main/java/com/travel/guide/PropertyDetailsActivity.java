package com.travel.guide;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.travel.guide.databinding.ActivityHostelDetailsBinding;
import com.travel.guide.adapters.CategoryOfHostelDetailsAdapter;
import com.travel.guide.adapters.ReviewAdapter;
import com.travel.guide.adapters.RoomTypeCardAdapter;
import com.travel.guide.apicalls.api.ApiConstants;
import com.travel.guide.apicalls.model.AddWishListResBean;
import com.travel.guide.apicalls.model.CouponAppliedResBean;
import com.travel.guide.apicalls.model.MessDetailsResBean;
import com.travel.guide.apicalls.model.PropertyDetailsResBean;
import com.travel.guide.apicalls.model.RoomBookingResBean;
import com.travel.guide.apicalls.model.RoomDetailsResBean;
import com.travel.guide.apicalls.presenter.PropertyDetailsPresenter;
import com.travel.guide.apicalls.viewclass.IPropertyDetailsView;
import com.travel.guide.utils.SharedPreferenceData;
import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class PropertyDetailsActivity extends BaseActivity implements CategoryOfHostelDetailsAdapter.ItemClickListener, View.OnClickListener
        , IPropertyDetailsView, RoomTypeCardAdapter.ItemClickListener {

    ActivityHostelDetailsBinding binding;
    List<String> categoryName = new ArrayList<>();
    List<Integer> categoryImage = new ArrayList<>();
    private CategoryOfHostelDetailsAdapter categoryAdapter;
    private ReviewAdapter reviewAdapter;
    String propertyId;
    PropertyDetailsPresenter presenter;
    SharedPreferenceData userData;
    String from="RoomDetails";

    PropertyDetailsResBean.Data propertyDetails;
    List<PropertyDetailsResBean.AmenitiesItem> ameniteisList = new ArrayList<>();
    List<RoomDetailsResBean.DataItem> roomDetails = new ArrayList<>();
    List<MessDetailsResBean.DataItem> messDetails = new ArrayList<>();
    String walletAmount;
    RecyclerView recyRoomType;
    TextView txtPrice, txtDate, edtMobile, txtOfferDescription;
    CardView cardBookNow, cardOffer;
    TextView txtVisitingDate;
    EditText edtName;
    String selectedRoomPrice, getSelectedRoomId, getSelectedRoomTypeId, lat, lng, propertyName;
    int LAUNCH_SECOND_ACTIVITY = 1;

    RoomTypeCardAdapter roomTypeCardAdapter;
    final Calendar myCalendar = Calendar.getInstance();
    String couponAmount="0", couponCode="";

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_hostel_details);
        presenter = new PropertyDetailsPresenter();
        presenter.setView(this);
        userData = new SharedPreferenceData(this);
        propertyId = getIntent().getStringExtra("propertyId");
        couponCode = getIntent().getStringExtra("couponId");

        //LoadData();

        categoryAdapter = new CategoryOfHostelDetailsAdapter(this, ameniteisList, this);
        GridLayoutManager gridLayoutManager1 = new GridLayoutManager(this, 1, LinearLayoutManager.HORIZONTAL, false);
        binding.recyCategories.setLayoutManager(gridLayoutManager1);
        binding.recyCategories.setItemAnimator(new DefaultItemAnimator());
        binding.recyCategories.setAdapter(categoryAdapter);

        reviewAdapter = new ReviewAdapter(this, categoryName);
        GridLayoutManager gridLayoutManager2 = new GridLayoutManager(this, 1, LinearLayoutManager.VERTICAL, false);
        binding.recyReview.setLayoutManager(gridLayoutManager2);
        binding.recyReview.setItemAnimator(new DefaultItemAnimator());
        binding.recyReview.setAdapter(reviewAdapter);

        presenter.getPropertyDetails(this, propertyId, userData.getACCESS_TOKEN());

        binding.imgBack.setOnClickListener(this);
        binding.txtReferEarn.setOnClickListener(this);

    }

    @Override
    public void OnItemClicked(int Position) {

    }

    @Override
    public void OnRoomTypeCardClicked(int Position, boolean isSelected) {
        for (int i=0; i<roomDetails.size(); i++){
            roomDetails.get(i).setSelected(false);
        }
        if (isSelected){
            roomDetails.get(Position).setSelected(true);
            txtPrice.setText("Price - \u20B9 "+roomDetails.get(Position).getPrice()+"/ Month");
            selectedRoomPrice = roomDetails.get(Position).getPrice();
            getSelectedRoomId = ""+roomDetails.get(Position).getId();
            getSelectedRoomTypeId = ""+roomDetails.get(Position).getRoomType();
            diductCouponAmount();
        }
        recyRoomType.post(new Runnable() {
            @Override
            public void run() {
                roomTypeCardAdapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.imgBack:
                finish();
                break;

            case R.id.txtReferEarn:
                startActivity(new Intent(this, ReferandEarnActivity.class));
                break;

            case R.id.consRoomDetails:
                if (roomDetails.size()>0){
                    Intent intent = new Intent(this, RoomDetailsActivity.class);
                    intent.putExtra("roomDetails", (Serializable) roomDetails);
                    startActivity(intent);
                }else {
                    presenter.getRoomDetails(this, propertyId, userData.getACCESS_TOKEN());
                }
                break;

            case R.id.consMessDetails:
                if (messDetails.size()>0){
                    Intent intent = new Intent(this, MessDetailsActivity.class);
                    intent.putExtra("messDetails", (Serializable) messDetails);
                    startActivity(intent);
                }else {
                    presenter.getMessDetails(this, propertyId, userData.getACCESS_TOKEN());
                }
                break;

            case R.id.cardBookOnline:
                from = "BookOnline";
                if (roomDetails.size()>0){
                    Intent intent = new Intent(PropertyDetailsActivity.this, BookOnlineActivity.class);
                    intent.putExtra("propertyId", propertyId);
                    intent.putExtra("roomDetails", (Serializable) roomDetails);
                    intent.putExtra("walletAmount", walletAmount);
                    intent.putExtra("from", from);
                    intent.putExtra("couponCode", couponCode);
                    startActivity(intent);
                }else {
                    presenter.getRoomDetails(this, propertyId, userData.getACCESS_TOKEN());
                }
                break;

            case R.id.cardBookOffline:
                from = "BookOffline";
                if (roomDetails.size()>0){
                    showBookOfflineDialog(this);
                }else {
                    presenter.getRoomDetails(this, propertyId, userData.getACCESS_TOKEN());
                }
                break;

            case R.id.imgLocation:
                Intent intent = new Intent(this, MapMarkerActivity.class);
                intent.putExtra("lat", lat);
                intent.putExtra("lng", lng);
                intent.putExtra("propertyName", propertyName);
                startActivity(intent);
                break;

            case R.id.imgWishlist:
                presenter.addToWishList(this, propertyId, userData.getACCESS_TOKEN());
                break;

            default:
                break;
        }
    }

    @Override
    public void onPropertyDetailsSuccess(PropertyDetailsResBean item) {
        if (item.isStatus()){
            propertyDetails= item.getData();
            Picasso.get().load(ApiConstants.BASE_IMAGE_URL+item.getData().getImage()).into(binding.imgProperty);
            binding.txtPropertyName.setText(item.getData().getBusinessName()+" ("+item.getData().getBusinesscat().getName()+")");
            binding.txtPropertyAddress.setText(item.getData().getAddress());
            binding.txtPriceRange1.setText("Price - \u20B9"+item.getData().getMinPrice() +"-"+ item.getData().getMaxPrice());
            binding.txtDescription.setText(item.getData().getDescription());

            ameniteisList.addAll(item.getData().getAmenities());
            categoryAdapter.notifyDataSetChanged();
            lat = item.getData().getLatitude();
            lng = item.getData().getLongitude();
            propertyName = item.getData().getBusinessName();
            if (item.getData().getIsWishList().equalsIgnoreCase("1")){
                binding.imgWishlist.setImageDrawable(getResources().getDrawable(R.drawable.ic_wishlisted));
            }else {
                binding.imgWishlist.setImageDrawable(getResources().getDrawable(R.drawable.ic_wishlist));
            }

        }else {
            toast("PropertyDetails not found");
        }
    }

    @Override
    public void onRoomDetailsSuccess(RoomDetailsResBean item) {
        roomDetails.clear();
        if (item.isStatus()){
            roomDetails.addAll(item.getData());
            walletAmount = item.getWallet().getWalletAmount();
            roomDetails.get(0).setSelected(true);
            for (int i=1; i<roomDetails.size(); i++){
                roomDetails.get(i).setSelected(false);
            }
            if (from.equalsIgnoreCase("BookOnline")){
                Intent intent = new Intent(PropertyDetailsActivity.this, BookOnlineActivity.class);
                intent.putExtra("propertyId", propertyId);
                intent.putExtra("roomDetails", (Serializable) roomDetails);
                intent.putExtra("walletAmount", walletAmount);
                intent.putExtra("from", from);
                intent.putExtra("couponCode", couponCode);
                startActivity(intent);
            }else if (from.equalsIgnoreCase("BookOffline")){
                showBookOfflineDialog(this);
            }else {
                Intent intent = new Intent(this, RoomDetailsActivity.class);
                intent.putExtra("roomDetails", (Serializable) roomDetails);
                startActivity(intent);
            }
        }else {
            toast("Room details not found");
        }
    }

    @Override
    public void onMessDetailsSuccess(MessDetailsResBean item) {
        if (item.isStatus()){
            messDetails.clear();
            messDetails.addAll(item.getData());
            Intent intent = new Intent(this, MessDetailsActivity.class);
            intent.putExtra("messDetails", (Serializable) messDetails);
            startActivity(intent);

        }else {
            toast("Room details not found");
        }
    }

    @Override
    public void onRoomBookingSuccess(RoomBookingResBean item) {
        if (item.isStatus()){
            //showBookingSuccessDialog(PropertyDetailsActivity.this, item.getData());
            Intent intent = new Intent(PropertyDetailsActivity.this, BookingConfimationActivity.class);
            intent.putExtra("successDate", (Serializable) item.getData());
            intent.putExtra("from", "Offline");
            startActivity(intent);
        }else {
            toast("Unable to book room");
        }
    }

    @Override
    public void addWishListSuccess(AddWishListResBean item) {
        if (item.isStatus()){
            if (propertyDetails.getIsWishList().equalsIgnoreCase("1")){
                propertyDetails.setIsWishList("0");
                binding.imgWishlist.setImageDrawable(getResources().getDrawable(R.drawable.ic_wishlist));
            }else {
                propertyDetails.setIsWishList("1");
                binding.imgWishlist.setImageDrawable(getResources().getDrawable(R.drawable.ic_wishlisted));
            }
        }
        //toast(item.getMessage());
    }

    @Override
    public void onCouponAppliedSuccess(CouponAppliedResBean item) {
        couponAmount = item.getMinusAmount();
        txtOfferDescription.setText("Get "+couponAmount+" off on your booking order\n with code - "+couponCode);
        diductCouponAmount();
    }

    public void LoadData(){
        categoryName.add("Wi-fi");
        categoryName.add("Food");
        categoryName.add("Table");
        categoryName.add("Air Cooled");
        categoryName.add("Bed");
        categoryName.add("Parking");

        categoryImage.add(R.drawable.ic_wifi);
        categoryImage.add(R.drawable.ic_food);
        categoryImage.add(R.drawable.ic_table);
        categoryImage.add(R.drawable.ic_aircooled);
        categoryImage.add(R.drawable.ic_bed);
        categoryImage.add(R.drawable.ic_laundary);
    }

    public void showBookOfflineDialog(Activity activity){
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog_book_offline);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        recyRoomType = dialog.findViewById(R.id.recyRoomType);
        txtPrice = dialog.findViewById(R.id.txtPrice);
        cardBookNow = dialog.findViewById(R.id.cardBookNow);
        txtVisitingDate = dialog.findViewById(R.id.txtVisitingDate);
        edtName = dialog.findViewById(R.id.edtName);
        txtDate = dialog.findViewById(R.id.txtDate);
        edtMobile = dialog.findViewById(R.id.edtMobile);
        cardOffer = dialog.findViewById(R.id.cardOffer);

        txtOfferDescription = dialog.findViewById(R.id.txtOfferDescription);

        CardView cardVisitingDate = dialog.findViewById(R.id.cardVisitingDate);
        ImageView imgCross = dialog.findViewById(R.id.imgCross);
        int selectedPosition=0;

        for (int i=0; i<roomDetails.size(); i++) {
            if (roomDetails.get(i).getIsSelected()){
                selectedPosition=i;
            }
        }
        txtPrice.setText("Price - \u20B9 "+roomDetails.get(selectedPosition).getPrice()+"/ Month");
        selectedRoomPrice = roomDetails.get(selectedPosition).getPrice();
        getSelectedRoomId = ""+roomDetails.get(selectedPosition).getId();
        getSelectedRoomTypeId = ""+roomDetails.get(selectedPosition).getRoomType();

        if (!couponCode.equalsIgnoreCase("")){
            presenter.couponApplied(this, couponCode, selectedRoomPrice, userData.getACCESS_TOKEN());
        }

        edtName.setText(userData.getUser_name());
        edtMobile.setText(userData.getMobile_no());

        roomTypeCardAdapter = new RoomTypeCardAdapter(this, roomDetails, this);
        GridLayoutManager gridLayoutManager1 = new GridLayoutManager(this, 2, LinearLayoutManager.VERTICAL, false);
        recyRoomType.setLayoutManager(gridLayoutManager1);
        recyRoomType.setItemAnimator(new DefaultItemAnimator());
        recyRoomType.setAdapter(roomTypeCardAdapter);

        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                String myFormat = "yyyy-MM-dd"; //In which you need put here
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
                txtVisitingDate.setText(sdf.format(myCalendar.getTime()));
            }

        };

        cardVisitingDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(PropertyDetailsActivity.this, date, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                datePickerDialog.show();
            }
        });

        cardOffer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(PropertyDetailsActivity.this, CouponActivity.class);
                i.putExtra("amount", selectedRoomPrice);
                startActivityForResult(i, LAUNCH_SECOND_ACTIVITY);
            }
        });

        cardBookNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //showBookingSuccessDialog(activity);
                if (edtName.getText().toString().trim().isEmpty()){
                    toast("Please enter name");
                }else if (txtVisitingDate.getText().toString().trim().isEmpty() || txtVisitingDate.getText().toString()
                        .equalsIgnoreCase(activity.getResources().getString(R.string.visiting_date))){
                    toast("Please select visiting date");
                }else if (edtMobile.getText().toString().trim().isEmpty()){
                    toast("Please enter name");
                } else {
                    presenter.callRoomBooking(PropertyDetailsActivity.this, propertyId, edtName.getText().toString(), txtVisitingDate.getText().toString()
                            , edtMobile.getText().toString(), getSelectedRoomId, getSelectedRoomTypeId, from, "0", couponCode, couponAmount, ""
                            , userData.getACCESS_TOKEN());
                    dialog.dismiss();
                    //RAZORPAY(selectedRoomPrice);
                }
            }
        });

        imgCross.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.MATCH_PARENT;
        dialog.show();
        dialog.getWindow().setAttributes(lp);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == LAUNCH_SECOND_ACTIVITY) {
            if(resultCode == Activity.RESULT_OK){
                couponCode = data.getStringExtra("couponCode");
                couponAmount = data.getStringExtra("couponAmount");
                txtOfferDescription.setText("Get "+couponAmount+" off on your booking order\n with code - "+couponCode);
                diductCouponAmount();
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                // Write your code if there's no result
            }
        }
    }

    public void diductCouponAmount(){
        txtPrice.setText("Price - \u20B9 "+(Integer.parseInt(selectedRoomPrice) - Integer.parseInt(couponAmount))+"/ Month");
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
        //toast(reason);
    }
}
