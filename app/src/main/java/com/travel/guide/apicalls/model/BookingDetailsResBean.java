package com.travel.guide.apicalls.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class BookingDetailsResBean implements Serializable {

	@SerializedName("data")
	private Data data;

	@SerializedName("message")
	private String message;

	@SerializedName("status")
	private boolean status;

	public Data getData(){
		return data;
	}

	public String getMessage(){
		return message;
	}

	public boolean isStatus(){
		return status;
	}

	public class Data implements Serializable{

		@SerializedName("room_id")
		private String roomId;

		@SerializedName("transaction_id")
		private String transactionId;

		@SerializedName("coupon_amount")
		private String couponAmount;

		@SerializedName("room_type_name")
		private String roomTypeName;

		@SerializedName("unique_id")
		private String uniqueId;

		@SerializedName("coupon_code")
		private String couponCode;

		@SerializedName("hostelowner_id")
		private String hostelownerId;

		@SerializedName("user_name")
		private String userName;

		@SerializedName("booking_date")
		private String bookingDate;

		@SerializedName("payment_status")
		private String paymentStatus;

		@SerializedName("icon")
		private String icon;

		@SerializedName("created_at")
		private String createdAt;

		@SerializedName("owner_mobile")
		private String ownerMobile;

		@SerializedName("property_name")
		private String propertyName;

		@SerializedName("payment_type")
		private String paymentType;

		@SerializedName("updated_at")
		private String updatedAt;

		@SerializedName("user_id")
		private String userId;

		@SerializedName("total_amount")
		private String totalAmount;

		@SerializedName("defaultIcon")
		private String defaultIcon;

		@SerializedName("price")
		private String price;

		@SerializedName("id")
		private int id;

		@SerializedName("service_property_id")
		private String servicePropertyId;

		@SerializedName("status")
		private String status;

		public String getRoomId(){
			return roomId;
		}

		public String getTransactionId(){
			return transactionId;
		}

		public String getCouponAmount(){
			return couponAmount;
		}

		public String getRoomTypeName(){
			return roomTypeName;
		}

		public String getUniqueId(){
			return uniqueId;
		}

		public String getCouponCode(){
			return couponCode;
		}

		public String getHostelownerId(){
			return hostelownerId;
		}

		public String getUserName(){
			return userName;
		}

		public String getBookingDate(){
			return bookingDate;
		}

		public String getPaymentStatus(){
			return paymentStatus;
		}

		public String getIcon(){
			return icon;
		}

		public String getCreatedAt(){
			return createdAt;
		}

		public String getOwnerMobile(){
			return ownerMobile;
		}

		public String getPropertyName(){
			return propertyName;
		}

		public String getPaymentType(){
			return paymentType;
		}

		public String getUpdatedAt(){
			return updatedAt;
		}

		public String getUserId(){
			return userId;
		}

		public String getTotalAmount(){
			return totalAmount;
		}

		public String getDefaultIcon(){
			return defaultIcon;
		}

		public String getPrice(){
			return price;
		}

		public int getId(){
			return id;
		}

		public String getServicePropertyId(){
			return servicePropertyId;
		}

		public String getStatus(){
			return status;
		}
	}
}