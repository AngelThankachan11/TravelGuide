package com.travel.guide.apicalls.model;

import com.google.gson.annotations.SerializedName;

public class ProfileResBean {

	@SerializedName("data")
	private Data data;

	@SerializedName("status")
	private boolean status;

	public Data getData(){
		return data;
	}

	public boolean isStatus(){
		return status;
	}

	public class Data{

		@SerializedName("device_key")
		private Object deviceKey;

		@SerializedName("address")
		private String address;

		@SerializedName("city")
		private String city;

		@SerializedName("year")
		private String year;

		@SerializedName("verify_otp_status")
		private String verifyOtpStatus;

		@SerializedName("mobile")
		private String mobile;

		@SerializedName("created_at")
		private String createdAt;

		@SerializedName("email_verified_at")
		private Object emailVerifiedAt;

		@SerializedName("is_property_added")
		private String isPropertyAdded;

		@SerializedName("profile_image")
		private String profileImage;

		@SerializedName("refer_id")
		private Object referId;

		@SerializedName("month")
		private String month;

		@SerializedName("register_otp")
		private String registerOtp;

		@SerializedName("updated_at")
		private String updatedAt;

		@SerializedName("name")
		private String name;

		@SerializedName("id")
		private int id;

		@SerializedName("state")
		private String state;

		@SerializedName("referal_id")
		private String referalId;

		@SerializedName("day")
		private String day;

		@SerializedName("email")
		private String email;

		@SerializedName("status")
		private String status;

		public Object getDeviceKey(){
			return deviceKey;
		}

		public String getAddress(){
			return address;
		}

		public String getCity(){
			return city;
		}

		public String getYear(){
			return year;
		}

		public String getVerifyOtpStatus(){
			return verifyOtpStatus;
		}

		public String getMobile(){
			return mobile;
		}

		public String getCreatedAt(){
			return createdAt;
		}

		public Object getEmailVerifiedAt(){
			return emailVerifiedAt;
		}

		public String getIsPropertyAdded(){
			return isPropertyAdded;
		}

		public String getProfileImage(){
			return profileImage;
		}

		public Object getReferId(){
			return referId;
		}

		public String getMonth(){
			return month;
		}

		public String getRegisterOtp(){
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

		public String getReferalId(){
			return referalId;
		}

		public String getDay(){
			return day;
		}

		public String getEmail(){
			return email;
		}

		public String getStatus(){
			return status;
		}
	}
}