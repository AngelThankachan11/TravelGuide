package com.travel.guide;

import androidx.appcompat.app.AlertDialog;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import com.travel.guide.databinding.ActivityMainBinding;
import com.travel.guide.adapters.NavAdapter;
import com.travel.guide.apicalls.model.OfflinePaynowResBean;
import com.travel.guide.apicalls.presenter.HomeActivityPresenter;
import com.travel.guide.apicalls.viewclass.IHomeActivityView;
import com.travel.guide.fragments.BookingFragment;
import com.travel.guide.fragments.HomeFragment;
import com.travel.guide.fragments.SearchFragment;
import com.travel.guide.fragments.SupportFragment;
import com.travel.guide.fragments.WalletFragment;
import com.travel.guide.utils.AppUtils;
import com.travel.guide.utils.SharedPreferenceData;
import com.razorpay.Checkout;
import com.razorpay.PaymentData;
import com.razorpay.PaymentResultWithDataListener;

import org.json.JSONObject;

public class MainActivity extends BaseActivity implements NavAdapter.ItemClickListener, PaymentResultWithDataListener, IHomeActivityView {

    public ActivityMainBinding binding;
    /*RecyclerView rvItemList;
    TextView txtUserName;
    ImageView imgUser;*/
    boolean doubleBackToExitPressedOnce = false;
    public SharedPreferenceData userData;
    String bookingIdForOfflinePay;
    String city, addressLine;

    private String[] navItems = {"My Profile", "My Wallet", "Terms & Conditions", "FAQ", "Logout"};
    private int[] navImage = {R.drawable.ic_table, R.drawable.ic_wallet, R.drawable.ic_parking, R.drawable.ic_support,  R.drawable.ic_logout};

    HomeActivityPresenter presenter;
    boolean isNeedToOpenHomeFragment= false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        userData = new SharedPreferenceData(this);
        presenter = new HomeActivityPresenter();
        presenter.setView(this);
        city = getIntent().getStringExtra("city");
        addressLine = getIntent().getStringExtra("addressline");

        /*View header = binding.sideNavView.getHeaderView(0);
        rvItemList = header.findViewById(R.id.rvItemList);
        txtUserName = header.findViewById(R.id.txtUserName);
        imgUser = header.findViewById(R.id.imgUser);*/

        /*NavAdapter navAdapter = new NavAdapter(MainActivity.this, navItems, navImage, this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(MainActivity.this, LinearLayoutManager.VERTICAL, false);
        rvItemList.setLayoutManager(layoutManager);
        rvItemList.setItemAnimator(new DefaultItemAnimator());
        rvItemList.setAdapter(navAdapter);*/

        Fragment fragment = new HomeFragment();
        Bundle bundle = new Bundle();
        bundle.putString("city", city);
        bundle.putString("addresLine", addressLine);
        fragment.setArguments(bundle);
        AppUtils.goNextFragmentReplace(MainActivity.this, fragment);
    }

    public void onClick(View view){
        switch (view.getId()){
            case R.id.consSearch:
                ChangeBottobar(binding.consSearch, new SearchFragment());
                break;
            case R.id.consBookings:
                ChangeBottobar(binding.consBookings, new BookingFragment());
                break;
            case R.id.consHome:
                ChangeBottobar(binding.consHome, new HomeFragment());
                break;
            case R.id.consWallet:
                ChangeBottobar(binding.consWallet, new WalletFragment());
                break;
            case R.id.consSupport:
                ChangeBottobar(binding.consSupport, new SupportFragment());
                break;
        }
    }

    public void RAZORPAY(String price, String bookingIdForOfflinePay) {
        this.bookingIdForOfflinePay = bookingIdForOfflinePay;
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
    public void onPaymentSuccess(String s, PaymentData paymentData) {
        presenter.CallOfflineBookingPay(this, bookingIdForOfflinePay, paymentData.getPaymentId(), userData.getACCESS_TOKEN());
    }

    @Override
    public void onPaymentError(int i, String s, PaymentData paymentData) {
        toast(s);
    }

    public void ChangeBottobar(ConstraintLayout lay, Fragment fragment){
        binding.consSearch.setBackgroundResource(0);
        binding.consBookings.setBackgroundResource(0);
        binding.consHome.setBackgroundResource(0);
        binding.consWallet.setBackgroundResource(0);
        binding.consSupport.setBackgroundResource(0);

        lay.setBackground(getResources().getDrawable(R.drawable.rounded_corner_gray20));
        AppUtils.goNextFragmentReplace(MainActivity.this, fragment);
    }

    /*public void openDrawer() {
        binding.drawerLayout.openDrawer(Gravity.LEFT);
    }

    public void closeDrawer() {
        binding.drawerLayout.closeDrawer(Gravity.LEFT);
    }*/

    @Override
    public void onNavItemClick(int Position) {
        if (Position==0){
            startActivity(new Intent(this, ProfileActivity.class));
        }else {

        }
    }

    @Override
    public void onBackPressed() {
        AppUtils.hideSoftKeyboard(this);
        int backStackEntryCount = getSupportFragmentManager().getBackStackEntryCount();
        if (backStackEntryCount > 0) {
            binding.consBottomBar.setVisibility(View.VISIBLE);
            getSupportFragmentManager().popBackStackImmediate();
            if (backStackEntryCount == 1) {
                if (!getSupportFragmentManager().getFragments().get(0).equals(new HomeFragment())) {
                    AppUtils.goNextFragmentReplace(MainActivity.this, new HomeFragment());
                }
                Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.frame_container);
                    /*if (!(fragment instanceof HomeFragment)) {
                        isNeedToOpenHomeFragment = true;
                    } else {
                        isNeedToOpenHomeFragment = false;
                    }*/
                //super.onBackPressed();
            } else {
                //super.onBackPressed();
            }
        } else {
            Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.frame_container);
            if (!(fragment instanceof HomeFragment)) {
                isNeedToOpenHomeFragment = true;
            } else {
                isNeedToOpenHomeFragment = false;
            }
            if (isNeedToOpenHomeFragment) {
                isNeedToOpenHomeFragment = false;
                ChangeBottobar(binding.consHome, new HomeFragment());
            } else {
                if (doubleBackToExitPressedOnce) {

                    new AlertDialog.Builder(this)
                            .setIcon(R.drawable.app_logo)
                            .setTitle(getResources().getString(R.string.exit))
                            .setCancelable(false)
                            .setMessage(getResources().getString(R.string.msg_exit))
                            .setPositiveButton(getResources().getString(R.string.yes), new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    //turnGPSOff();
                                    MainActivity.this.finish();
                                }
                            })
                            .setNegativeButton(getResources().getString(R.string.no), null)
                            .show();
                }

                this.doubleBackToExitPressedOnce = true;
                new Handler().postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        doubleBackToExitPressedOnce = false;
                    }
                }, 2000);
            }

        }
    }

    @Override
    public void onOfflinePaynowSuccess(OfflinePaynowResBean item) {
        if (item.isStatus()){
            Intent refresh = new Intent(this, MainActivity.class);
            startActivity(refresh);
            this.finish();
        }else {
            toast(item.getMessage());
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