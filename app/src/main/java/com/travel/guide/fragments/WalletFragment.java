package com.travel.guide.fragments;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.travel.guide.databinding.FragmentWalletBinding;
import com.travel.guide.BannerDetailsActivity;
import com.travel.guide.MainActivity;
import com.travel.guide.R;
import com.travel.guide.adapters.WalletHistoryAdapter;
import com.travel.guide.apicalls.api.ApiConstants;
import com.travel.guide.apicalls.model.ExploreMoreResBean;
import com.travel.guide.apicalls.model.WalletResBean;
import com.travel.guide.apicalls.presenter.WalletPresenter;
import com.travel.guide.apicalls.viewclass.IWalletView;
import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class WalletFragment extends Fragment implements View.OnClickListener, IWalletView {

    FragmentWalletBinding binding;
    WalletHistoryAdapter walletHistoryAdapter;
    List<WalletResBean.Data.TransactionItem> walletData = new ArrayList<>();
    WalletPresenter presenter;

    String type="";
    private List<ExploreMoreResBean.DataItem> exploreItemList = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup container, Bundle savedInstanceState){
        super.onCreateView(layoutInflater, container, savedInstanceState);
        binding = DataBindingUtil.inflate(layoutInflater, R.layout.fragment_wallet, container, false);
        presenter = new WalletPresenter();
        presenter.setView(this);

        walletHistoryAdapter = new WalletHistoryAdapter(getActivity(), walletData);
        GridLayoutManager gridLayoutManager2 = new GridLayoutManager(getContext(), 1, LinearLayoutManager.VERTICAL, false);
        binding.recyWalletHistory.setLayoutManager(gridLayoutManager2);
        binding.recyWalletHistory.setItemAnimator(new DefaultItemAnimator());
        binding.recyWalletHistory.setAdapter(walletHistoryAdapter);

        binding.imgBack.setOnClickListener(this);
        binding.imgFilter.setOnClickListener(this);
        binding.imgExplore1.setOnClickListener(this);
        binding.imgExplore2.setOnClickListener(this);
        presenter.CallWalletData(getActivity(), "", ((MainActivity)getActivity()).userData.getACCESS_TOKEN());

        return binding.getRoot();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.imgBack:
                ((MainActivity)getActivity()).ChangeBottobar(((MainActivity)getActivity()).binding.consHome, new HomeFragment());
                break;

            case R.id.imgFilter:
                showPriceRangeDialog(getActivity());
                break;
            case R.id.imgExplore1:
                Intent intent = new Intent(getActivity(), BannerDetailsActivity.class);
                intent.putExtra("exploreData", (Serializable) exploreItemList.get(0));
                intent.putExtra("from", "explore");
                startActivity(intent);
                break;
            case R.id.imgExplore2:
                Intent intent1 = new Intent(getActivity(), BannerDetailsActivity.class);
                intent1.putExtra("exploreData", (Serializable) exploreItemList.get(1));
                intent1.putExtra("from", "explore");
                startActivity(intent1);
                break;
            default:
                break;
        }
    }

    public void showPriceRangeDialog(Activity activity){

        TextView txtApply;
        ImageView imgCross;

        Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.dialog_wallet);

        CheckBox chkCredit = dialog.findViewById(R.id.chkCredit);
        CheckBox chkDebit = dialog.findViewById(R.id.chkDebit);
        txtApply = dialog.findViewById(R.id.txtApply);
        imgCross = dialog.findViewById(R.id.imgCross);

        if (type.equalsIgnoreCase("CR")){
            chkCredit.setChecked(true);
        }else if (type.equalsIgnoreCase("DR")){
            chkDebit.setChecked(true);
        }

        chkCredit.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked){
                    chkDebit.setChecked(false);
                    type = "CR";
                }else {
                    type = "";
                }
            }
        });

        chkDebit.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    chkCredit.setChecked(false);
                    type = "DR";
                }else {
                    type = "";
                }
            }
        });

        txtApply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                if (!type.equalsIgnoreCase("")) {
                    presenter.CallWalletData(getActivity(), type, ((MainActivity) getActivity()).userData.getACCESS_TOKEN());
                }
            }
        });

        imgCross.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        dialog.show();
        Window window = dialog.getWindow();
        window.setLayout(ConstraintLayout.LayoutParams.MATCH_PARENT, ConstraintLayout.LayoutParams.WRAP_CONTENT);

    }

    @Override
    public void onWalletHistorySuccess(WalletResBean item) {
        walletData.clear();
        if (item.isStatus()){
            binding.txtWalletAmount.setText("\u20B9 "+item.getData().getWalletAmount());
            walletData.addAll(item.getData().getTransaction());
        }else {
            ((MainActivity)getActivity()).toast("No wallet history found");
        }
        walletHistoryAdapter.notifyDataSetChanged();
        presenter.CallExploreMore(getActivity(), ((MainActivity)getActivity()).userData.getACCESS_TOKEN());
    }

    @Override
    public void onExploreSuccess(ExploreMoreResBean item) {
        if (item.isStatus()){
            exploreItemList.addAll(item.getData());
            Picasso.get().load(ApiConstants.BASE_IMAGE_URL+item.getData().get(0).getImage()).into(binding.imgExplore1);
            Picasso.get().load(ApiConstants.BASE_IMAGE_URL+item.getData().get(1).getImage()).into(binding.imgExplore2);
        }else {
            ((MainActivity)getActivity()).toast("Explore more banner not found");
        }
    }

    @Override
    public void enableLoadingBar(Context context, boolean enable) {
        ((MainActivity)getActivity()).enableLoadingBar(context, enable);
    }

    @Override
    public void onError(String reason) {
        //((MainActivity)getActivity()).toast(reason);
    }
}
