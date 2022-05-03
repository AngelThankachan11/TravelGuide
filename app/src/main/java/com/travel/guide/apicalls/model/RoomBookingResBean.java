package com.travel.guide.apicalls.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class RoomBookingResBean implements Serializable {

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

		@SerializedName("payment_type")
		private String paymentType;

		@SerializedName("unique_id")
		private int uniqueId;

		@SerializedName("hostelowner_id")
		private int hostelownerId;

		@SerializedName("updated_at")
		private String updatedAt;

		@SerializedName("user_id")
		private int userId;

		@SerializedName("booking_date")
		private String bookingDate;

		@SerializedName("payment_status")
		private String paymentStatus;

		@SerializedName("created_at")
		private String createdAt;

		@SerializedName("id")
		private int id;

		@SerializedName("service_property_id")
		private String servicePropertyId;

		@SerializedName("user_name")
		private String userName;

		@SerializedName("property_name")
		private String propertyName;

		@SerializedName("owner_mobile")
		private String ownerMobile;

		@SerializedName("room_type_name")
		private String roomTypeName;

		@SerializedName("icon")
		private String icon;

		@SerializedName("defaultIcon")
		private String defaltIcon;

		@SerializedName("total_amount")
		private String totalAmount;

		@SerializedName("price")
		private String price;

		public String getRoomId(){
			return roomId;
		}

		public String getTransactionId(){
			return transactionId;
		}

		public String getPaymentType(){
			return paymentType;
		}

		public int getUniqueId(){
			return uniqueId;
		}

		public int getHostelownerId(){
			return hostelownerId;
		}

		public String getUpdatedAt(){
			return updatedAt;
		}

		public int getUserId(){
			return userId;
		}

		public String getBookingDate(){
			return bookingDate;
		}

		public String getPaymentStatus(){
			return paymentStatus;
		}

		public String getCreatedAt(){
			return createdAt;
		}

		public int getId(){
			return id;
		}

		public String getServicePropertyId(){
			return servicePropertyId;
		}

		public String getUserName(){
			return userName;
		}

		public String getPropertyName(){
			return propertyName;
		}

		public String getOwnerMobile(){
			return ownerMobile;
		}

		public String getRoomTypeName(){
			return roomTypeName;
		}

		public String getIcon(){
			return icon;
		}

		public String getDefaltIcon(){
			return defaltIcon;
		}

		public String getPrice(){
			return price;
		}

		public String getTotalAmount(){
			return totalAmount;
		}
	}
}