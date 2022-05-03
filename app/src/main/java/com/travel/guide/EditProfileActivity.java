package com.travel.guide;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.databinding.DataBindingUtil;

import com.travel.guide.databinding.ActivityEditprofileBinding;
import com.travel.guide.apicalls.api.ApiConstants;
import com.travel.guide.apicalls.model.EditProfileResBean;
import com.travel.guide.apicalls.presenter.EditProfilePresenter;
import com.travel.guide.apicalls.viewclass.IEditProfileView;
import com.travel.guide.utils.FileUtils;
import com.travel.guide.utils.NetworkCheck;
import com.travel.guide.utils.SharedPreferenceData;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.squareup.picasso.Picasso;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class EditProfileActivity extends BaseActivity implements IEditProfileView {

    SharedPreferenceData userData;
    ActivityEditprofileBinding binding;

    private boolean isimageFromGallery = false;
    public Uri uriProfile = null;
    EditProfilePresenter presenter;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_editprofile);
        userData = new SharedPreferenceData(this);
        presenter = new EditProfilePresenter();
        presenter.setView(this);

        Picasso.get().load(ApiConstants.BASE_IMAGE_URL+userData.getProfile_image()).into(binding.imgProfile);
        binding.edtUserName.setText(userData.getUser_name());
        binding.edtMobile.setText(userData.getMobile_no());
        binding.edtEmail.setText(userData.getEmail());

        binding.edtDay.setText(userData.getDobDay());
        binding.edtMonth.setText(userData.getDobMonth());
        binding.edtYear.setText(userData.getDobYear());

    }

    public void onClick(View view){
        switch (view.getId()){
            case R.id.imgBack:
                finish();
                break;
            case R.id.txtUpdate:
                /*if (uriProfile==null){
                    toast(getResources().getString(R.string.please_select_image));
                }else */if (binding.edtUserName.getText().toString().trim().isEmpty()){
                    toast(getResources().getString(R.string.please_enter_user_name));
                }else if (binding.edtMobile.getText().toString().trim().isEmpty() && binding.edtMobile.getText().length()>9){
                    toast(getResources().getString(R.string.please_enter_valid_mobile));
                }else if (binding.edtEmail.getText().toString().trim().isEmpty()){
                    toast(getResources().getString(R.string.please_enter_email));
                }else if (binding.edtDay.getText().toString().trim().isEmpty() && binding.edtMonth.getText().toString().trim().isEmpty() &&
                binding.edtYear.getText().toString().trim().isEmpty()){
                    toast(getResources().getString(R.string.please_enter_year));
                }else if (NetworkCheck.isConnected(this)){
                    MultipartBody.Part user_image = null;
                    if (uriProfile!=null) {
                        if (isimageFromGallery) {
                            String selectedPath = FileUtils.getPath(this, uriProfile);

                            File file = new File(selectedPath);
                            RequestBody requestFile = RequestBody.create(MediaType.parse("image/*"), file);
                            user_image = MultipartBody.Part.createFormData("image", file.getName(), requestFile);
                        } else {
                            String fileName = new File(uriProfile.getPath()).getName();
                            File actualFile = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), new File(uriProfile.getPath()).getName());
                            if (actualFile != null) {
                                user_image = MultipartBody.Part.createFormData("image", fileName, RequestBody.create(MediaType.parse("multipart/form-data"), actualFile));
                            } else {
                                user_image = MultipartBody.Part.createFormData("image", "", RequestBody.create(MediaType.parse("multipart/form-data"), ""));
                            }
                        }
                    }

                    presenter.editProfileInfo(this , binding.edtUserName.getText().toString(), binding.edtEmail.getText().toString(),
                            binding.edtMobile.getText().toString(), binding.edtDay.getText().toString(), binding.edtMonth.getText().toString(),
                            binding.edtYear.getText().toString(), user_image, new SharedPreferenceData(this).getACCESS_TOKEN());
                }else {
                    toast(getResources().getString(R.string.please_check_your_internet_connection));
                }
                break;
            case R.id.imgEditProfile:
                getImage();
                break;
            default:
                break;
        }
    }

    public void getImage() {
        final BottomSheetDialog dialog = new BottomSheetDialog(this);
        dialog.setCancelable(true);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.choose_photo_dialog);

        TextView textCamera = dialog.findViewById(R.id.textCamera);
        TextView textGallery = dialog.findViewById(R.id.textGallery);

        WindowManager.LayoutParams windowManager = new WindowManager.LayoutParams();
        windowManager.copyFrom(dialog.getWindow().getAttributes());
        windowManager.height = WindowManager.LayoutParams.WRAP_CONTENT;
        windowManager.width = WindowManager.LayoutParams.MATCH_PARENT;
        windowManager.gravity = Gravity.CENTER_HORIZONTAL;
        dialog.getWindow().setAttributes(windowManager);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        textGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                pickImageFromGallery(EditProfileActivity.this);
                dialog.dismiss();
            }
        });

        textCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                pickImageFromCamera(EditProfileActivity.this);
                dialog.dismiss();
            }
        });

        dialog.show();
        dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {

            @Override
            public void onCancel(DialogInterface dialog) {
                dialog.dismiss();
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case REQUEST_CAPTURE:
                    if (captureMediaFile != null) {
                        isimageFromGallery = false;
                        uriProfile = captureMediaFile;
                        binding.imgProfile.setImageURI(null);
                        binding.imgProfile.setImageURI(uriProfile);
                    }
                    break;

                case REQUEST_GALLERY:
                    if (data != null) {
                        isimageFromGallery = true;
                        uriProfile = data.getData();
                        binding.imgProfile.setImageURI(null);
                        binding.imgProfile.setImageURI(uriProfile);
                    }
                    break;
                default:
                    break;

            }
        }
    }

    @Override
    public void onEditProfileSuccess(EditProfileResBean item) {
        toast(item.getMessage());
        if (item.isStatus()){
            Intent returnIntent = new Intent();
            setResult(RESULT_CANCELED, returnIntent);
            finish();
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
