package com.travel.guide;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.databinding.DataBindingUtil;

import com.travel.guide.databinding.ActivityReferAndEarnBinding;
import com.travel.guide.adapters.FaqsAdapter;
import com.travel.guide.utils.SharedPreferenceData;

public class ReferandEarnActivity extends BaseActivity implements FaqsAdapter.ItemClickListener {

    ActivityReferAndEarnBinding binding;
    SharedPreferenceData userData;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_refer_and_earn);
        userData = new SharedPreferenceData(this);
        binding.txtReferralLink.setText(userData.getREFERRAL_CODE());

    }

    @Override
    public void OnQuestionClicked(int Position) {

    }

    public void onClick(View view) {
        switch (view.getId()){
            case R.id.imgBack:
                finish();
                break;

            case R.id.txtCopy:
                ClipboardManager manager = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
                ClipData clipData = ClipData.newPlainText("text", binding.txtReferralLink.getText());
                manager.setPrimaryClip(clipData);
                toast("Copied");
                break;

            case R.id.consReferFriendsNow:
                Intent intent = new Intent(android.content.Intent.ACTION_SEND);

                String shareBody = "Hey there! Sign-up on the TravelGuide app using my referral code & book your room. You can also avail some coupon codes TravelGuide Cashback. So, book now before the offer expires! \n" +
                        "\n" +
                        "Download the app from play store or use code"+userData.getREFERRAL_CODE()+"  while signing up.";

                /*String shareBody = "I am using MeraRoom app for booking room for study purpose, If you also want to do prebooking for room" +
                        ", can also get benefits from this app. \n\n Use my referral code for register :\n\n"+userData.getREFERRAL_CODE();*/
                intent.setType("text/plain");
                intent.putExtra(android.content.Intent.EXTRA_SUBJECT, getString(R.string.referral_earning));
                intent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
                startActivity(Intent.createChooser(intent, getString(R.string.share_using)));
                break;

            default:
                break;
        }
    }
}
