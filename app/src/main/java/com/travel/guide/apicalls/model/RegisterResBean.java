package com.travel.guide.apicalls.model;

import com.google.gson.annotations.SerializedName;

public class RegisterResBean {

	@SerializedName("message")
	private String message;

	@SerializedName("status")
	private boolean status;

	@SerializedName("verify_otp_status")
	private String verifyOtpStatus;

	public String getMessage(){
		return message;
	}

	public boolean isStatus(){
		return status;
	}

	public String getVerifyOtpStatus(){
		return verifyOtpStatus;
	}
}