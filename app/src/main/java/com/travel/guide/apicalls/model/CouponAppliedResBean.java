package com.travel.guide.apicalls.model;

import com.google.gson.annotations.SerializedName;

public class CouponAppliedResBean {

	@SerializedName("minus_amount")
	private String minusAmount;

	@SerializedName("total_amount")
	private int totalAmount;

	@SerializedName("message")
	private String message;

	@SerializedName("status")
	private boolean status;

	public String getMinusAmount(){
		return minusAmount;
	}

	public int getTotalAmount(){
		return totalAmount;
	}

	public String getMessage(){
		return message;
	}

	public boolean isStatus(){
		return status;
	}
}