package com.travel.guide;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.travel.guide.databinding.ActivityFaqsBinding;
import com.travel.guide.adapters.FaqsAdapter;
import com.travel.guide.apicalls.model.FaqResBean;
import com.travel.guide.apicalls.presenter.FaqPresenter;
import com.travel.guide.apicalls.viewclass.IFaqView;
import com.travel.guide.utils.SharedPreferenceData;

import java.util.ArrayList;
import java.util.List;

public class FAQsActivity extends BaseActivity implements FaqsAdapter.ItemClickListener, View.OnClickListener, IFaqView {

    ActivityFaqsBinding binding;
    FaqsAdapter faqsAdapter;
    List<FaqResBean.FaqItem> faqList = new ArrayList<>();
    FaqPresenter presenter;
    SharedPreferenceData userData;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_faqs);
        presenter = new FaqPresenter();
        presenter.setView(this);
        userData = new SharedPreferenceData(this);

        faqsAdapter = new FaqsAdapter(this, faqList, this);
        GridLayoutManager gridLayoutManager2 = new GridLayoutManager(this, 1, LinearLayoutManager.VERTICAL, false);
        binding.recyQuestions.setLayoutManager(gridLayoutManager2);
        binding.recyQuestions.setItemAnimator(new DefaultItemAnimator());
        binding.recyQuestions.setAdapter(faqsAdapter);

        presenter.CallFAQData(this, userData.getACCESS_TOKEN());

        binding.imgBack.setOnClickListener(this);

    }

    @Override
    public void OnQuestionClicked(int position) {

        boolean open = false;
        if (!faqList.get(position).getIsOpen())
            open = true;
        for (int i=0; i<faqList.size(); i++){
            faqList.get(i).setIsOpen(false);
        }
        if (open)
            faqList.get(position).setIsOpen(true);

        faqsAdapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.imgBack:
                finish();
                break;
            case R.id.condTermsConditions:
                startActivity(new Intent(this, TermsConditionsActivity.class));
                break;
            default:
                break;
        }
    }

    @Override
    public void onFaqSuccess(FaqResBean item) {
        faqList.clear();
        if (item.isStatus()){
            faqList.addAll(item.getFaq());
            for (int i=0; i<item.getFaq().size(); i++){
                faqList.get(i).setIsOpen(false);
            }
        }else {
            toast("Faq list not found");
        }
        faqsAdapter.notifyDataSetChanged();
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
