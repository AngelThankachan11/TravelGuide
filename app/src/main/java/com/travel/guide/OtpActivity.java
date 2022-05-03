package com.travel.guide;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;

import com.travel.guide.databinding.ActivityOtpBinding;
import com.travel.guide.apicalls.model.VerifyOtpResBean;
import com.travel.guide.apicalls.presenter.VerifyOtpPresenter;
import com.travel.guide.apicalls.viewclass.IVerifyOtpView;
import com.travel.guide.utils.NetworkCheck;
import com.travel.guide.utils.SharedPreferenceData;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;

public class OtpActivity extends BaseActivity implements View.OnClickListener, IVerifyOtpView {

    ActivityOtpBinding binding;
    private EditText[] editTexts;

    String mobileNumber;
    VerifyOtpPresenter presenter;
    String deviceKey;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_otp);
        mobileNumber = getIntent().getStringExtra("mobileNumber");
        presenter = new VerifyOtpPresenter();
        presenter.setView(this);
        binding.txtOtpMsg.setText(getResources().getString(R.string.otp_msg)+mobileNumber.substring(Math.max(mobileNumber.length() - 2, 0)));

        editTexts = new EditText[]{binding.edtOtp1, binding.edtOtp2, binding.edtOtp3, binding.edtOtp4};

        binding.edtOtp1.addTextChangedListener(new PinTextWatcher(0));
        binding.edtOtp2.addTextChangedListener(new PinTextWatcher(1));
        binding.edtOtp3.addTextChangedListener(new PinTextWatcher(2));
        binding.edtOtp4.addTextChangedListener(new PinTextWatcher(3));

        binding.edtOtp1.setOnKeyListener(new PinOnKeyListener(0));
        binding.edtOtp2.setOnKeyListener(new PinOnKeyListener(1));
        binding.edtOtp3.setOnKeyListener(new PinOnKeyListener(2));
        binding.edtOtp4.setOnKeyListener(new PinOnKeyListener(3));

        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "Fetching FCM registration token failed", task.getException());
                            return;
                        }
                        // Get new FCM registration token
                        deviceKey = task.getResult();

                    }
                });

        binding.btnProceed.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnProceed:
                if (binding.edtOtp1.getText().toString().trim().isEmpty() || binding.edtOtp2.getText().toString().trim().isEmpty() ||
                        binding.edtOtp3.getText().toString().trim().isEmpty() || binding.edtOtp4.getText().toString().trim().isEmpty()){
                    toast("Please enter valid otp");
                }else if(NetworkCheck.isConnected(this)){
                    String otp = binding.edtOtp1.getText().toString()+binding.edtOtp2.getText().toString()+
                            binding.edtOtp3.getText().toString()+binding.edtOtp4.getText().toString();
                    presenter.VerifyOtpCall(OtpActivity.this, mobileNumber, deviceKey, otp);
                }else {
                    NetworkCheck.showNetworkFailureAlert(this);
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void onVerifySucess(VerifyOtpResBean item) {
        if (item.isStatus()){
            (new SharedPreferenceData(this)).SavedLoginData(item);
            Intent intent = new Intent( OtpActivity.this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }
        toast(item.getMessage());

       /* if (item.isStatus()){
            new SharedPreferenceData(this).SavedLoginData(item);
            if (item.getData().getIsAddress().equalsIgnoreCase("0")){
                Intent intent = new Intent(OtpActivity.this, MapActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }else {
                Intent intent = new Intent(OtpActivity.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        }
        toast(item.getMessage());*/
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void onError(String reason) {

    }

    public class PinTextWatcher implements TextWatcher {

        private int currentIndex;
        private boolean isFirst = false, isLast = false;
        private String newTypedString = "";

        PinTextWatcher(int currentIndex) {
            this.currentIndex = currentIndex;

            if (currentIndex == 0)
                this.isFirst = true;
            else if (currentIndex == editTexts.length - 1)
                this.isLast = true;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            newTypedString = s.subSequence(start, start + count).toString().trim();
        }

        @Override
        public void afterTextChanged(Editable s) {

            String text = newTypedString;

            /* Detect paste event and set first char */
            if (text.length() > 1)
                text = String.valueOf(text.charAt(0)); // TODO: We can fill out other EditTexts

            editTexts[currentIndex].removeTextChangedListener(this);
            editTexts[currentIndex].setText(text);
            editTexts[currentIndex].setSelection(text.length());
            editTexts[currentIndex].addTextChangedListener(this);

            if (text.length() == 1)
                moveToNext();
            else if (text.length() == 0)
                moveToPrevious();
        }

        private void moveToNext() {
            if (!isLast)
                editTexts[currentIndex + 1].requestFocus();

            if (isAllEditTextsFilled() && isLast) { // isLast is optional
                editTexts[currentIndex].clearFocus();
                hideKeyboard();
            }
        }

        private void moveToPrevious() {
            if (!isFirst)
                editTexts[currentIndex - 1].requestFocus();
        }

        private boolean isAllEditTextsFilled() {
            for (EditText editText : editTexts)
                if (editText.getText().toString().trim().length() == 0)
                    return false;
            return true;
        }

        private void hideKeyboard() {
            if (getCurrentFocus() != null) {
                InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
            }
        }

    }

    public class PinOnKeyListener implements View.OnKeyListener {

        private int currentIndex;

        PinOnKeyListener(int currentIndex) {
            this.currentIndex = currentIndex;
        }

        @Override
        public boolean onKey(View v, int keyCode, KeyEvent event) {
            if (keyCode == KeyEvent.KEYCODE_DEL && event.getAction() == KeyEvent.ACTION_UP) {
                if (editTexts[currentIndex].getText().toString().isEmpty() && currentIndex != 0)
                    editTexts[currentIndex - 1].requestFocus();
            }
            return false;
        }

    }
}
