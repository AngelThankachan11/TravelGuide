package com.travel.guide.apicalls.model;

import com.google.gson.annotations.SerializedName;

public class LoginResBean {

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

		@SerializedName("verify_otp_status")
		private int verifyOtpStatus;

		@SerializedName("mobile")
		private String mobile;

		@SerializedName("created_at")
		private String createdAt;

		@SerializedName("email_verified_at")
		private Object emailVerifiedAt;

		@SerializedName("profile_image")
		private String profileImage;

		@SerializedName("refer_id")
		private Object referId;

		@SerializedName("register_otp")
		private int registerOtp;

		@SerializedName("updated_at")
		private String updatedAt;

		@SerializedName("name")
		private String name;

		@SerializedName("id")
		private int id;

		@SerializedName("referal_id")
		private String referalId;

		@SerializedName("email")
		private String email;

		@SerializedName("status")
		private int status;

		public int getVerifyOtpStatus(){
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

		public String getProfileImage(){
			return profileImage;
		}

		public Object getReferId(){
			return referId;
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

		public String getReferalId(){
			return referalId;
		}

		public String getEmail(){
			return email;
		}

		public int getStatus(){
			return status;
		}
	}
}