package com.travel.guide.apicalls.model;

import com.google.gson.annotations.SerializedName;

public class VerifyOtpResBean {

	@SerializedName("access_token")
	private String accessToken;

	@SerializedName("token_type")
	private String tokenType;

	@SerializedName("message")
	private String message;

	@SerializedName("expires_in")
	private int expiresIn;

	@SerializedName("user")
	private User user;

	@SerializedName("status")
	private boolean status;

	public String getAccessToken(){
		return accessToken;
	}

	public String getTokenType(){
		return tokenType;
	}

	public String getMessage(){
		return message;
	}

	public int getExpiresIn(){
		return expiresIn;
	}

	public User getUser(){
		return user;
	}

	public boolean isStatus(){
		return status;
	}


	public class User{

		@SerializedName("country")
		private Object country;

		@SerializedName("pincode")
		private String pincode;

		@SerializedName("address")
		private String address;

		@SerializedName("city")
		private String city;

		@SerializedName("verify_otp_status")
		private int verifyOtpStatus;

		@SerializedName("mobile")
		private String mobile;

		@SerializedName("created_at")
		private String createdAt;

		@SerializedName("profile_image")
		private String profileImage;

		@SerializedName("register_otp")
		private int registerOtp;

		@SerializedName("updated_at")
		private String updatedAt;

		@SerializedName("name")
		private String name;

		@SerializedName("id")
		private int id;

		@SerializedName("state")
		private String state;

		@SerializedName("email")
		private String email;

		@SerializedName("status")
		private int status;

		@SerializedName("dob_day")
		private String dobDay;

		@SerializedName("dob_month")
		private String dobMonth;

		@SerializedName("dob_year")
		private String dobYear;

		@SerializedName("referal_id")
		private String referalId;

		public Object getCountry(){
			return country;
		}

		public String getPincode(){
			return pincode;
		}

		public String getAddress(){
			return address;
		}

		public String getCity(){
			return city;
		}

		public int getVerifyOtpStatus(){
			return verifyOtpStatus;
		}

		public String getMobile(){
			return mobile;
		}

		public String getCreatedAt(){
			return createdAt;
		}

		public String getProfileImage(){
			return profileImage;
		}

		public int getRegisterOtp(){
			return registerOtp;
		}

		public String getUpdatedAt(){
			return updatedAt;
		}

		public String getName(){
			return name;
		}

		public int getId(){
			return id;
		}

		public String getState(){
			return state;
		}

		public String getEmail(){
			return email;
		}

		public int getStatus(){
			return status;
		}


		public String getDobDay() {
			return dobDay;
		}

		public String getDobMonth() {
			return dobMonth;
		}

		public String getDobYear() {
			return dobYear;
		}

		public String getReferalId(){
			return referalId;
		}

	}
}