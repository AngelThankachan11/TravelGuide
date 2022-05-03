package com.travel.guide.apicalls.model;

import com.google.gson.annotations.SerializedName;

public class RequestCallbackResBean {

	@SerializedName("message")
	private String message;

	@SerializedName("status")
	private boolean status;

	public String getMessage(){
		return message;
	}

	public boolean isStatus(){
		return status;
	}
}