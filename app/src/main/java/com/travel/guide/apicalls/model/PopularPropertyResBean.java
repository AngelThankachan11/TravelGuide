package com.travel.guide.apicalls.model;

import java.io.Serializable;
import java.util.List;
import com.google.gson.annotations.SerializedName;

public class PopularPropertyResBean implements Serializable {

	@SerializedName("data")
	private List<DataItem> data;

	@SerializedName("status")
	private boolean status;

	public List<DataItem> getData(){
		return data;
	}

	public boolean isStatus(){
		return status;
	}

	public class DataItem implements Serializable {

		@SerializedName("business_name")
		private String businessName;

		@SerializedName("image")
		private String image;

		@SerializedName("address")
		private String address;

		@SerializedName("hostelowner_id")
		private int hostelownerId;

		@SerializedName("businesscat_id")
		private int businesscatId;

		@SerializedName("latitude")
		private String latitude;

		@SerializedName("payment_status")
		private String paymentStatus;

		@SerializedName("mobile_no")
		private String mobileNo;

		@SerializedName("description")
		private String description;

		@SerializedName("created_at")
		private String createdAt;

		@SerializedName("amanities")
		private String amanities;

		@SerializedName("manager_name")
		private String managerName;

		@SerializedName("businesscat")
		private Businesscat businesscat;

		@SerializedName("max_price")
		private String maxPrice;

		@SerializedName("min_price")
		private String minPrice;

		@SerializedName("updated_at")
		private String updatedAt;

		@SerializedName("location")
		private Object location;

		@SerializedName("id")
		private String id;

		@SerializedName("longitude")
		private String longitude;

		@SerializedName("status")
		private int status;

		public String getBusinessName(){
			return businessName;
		}

		public String getImage(){
			return image;
		}

		public String getAddress(){
			return address;
		}

		public int getHostelownerId(){
			return hostelownerId;
		}

		public int getBusinesscatId(){
			return businesscatId;
		}

		public String getLatitude(){
			return latitude;
		}

		public String getPaymentStatus(){
			return paymentStatus;
		}

		public String getMobileNo(){
			return mobileNo;
		}

		public String getDescription(){
			return description;
		}

		public String getCreatedAt(){
			return createdAt;
		}

		public String getAmanities(){
			return amanities;
		}

		public String getManagerName(){
			return managerName;
		}

		public Businesscat getBusinesscat(){
			return businesscat;
		}

		public String getMaxPrice(){
			return maxPrice;
		}

		public String getMinPrice(){
			return minPrice;
		}

		public String getUpdatedAt(){
			return updatedAt;
		}

		public Object getLocation(){
			return location;
		}

		public String getId(){
			return id;
		}

		public String getLongitude(){
			return longitude;
		}

		public int getStatus(){
			return status;
		}
	}

	public class Businesscat implements Serializable{

		@SerializedName("image")
		private String image;

		@SerializedName("updated_at")
		private String updatedAt;

		@SerializedName("name")
		private String name;

		@SerializedName("created_at")
		private String createdAt;

		@SerializedName("id")
		private int id;

		@SerializedName("status")
		private int status;

		public String getImage(){
			return image;
		}

		public String getUpdatedAt(){
			return updatedAt;
		}

		public String getName(){
			return name;
		}

		public String getCreatedAt(){
			return createdAt;
		}

		public int getId(){
			return id;
		}

		public int getStatus(){
			return status;
		}
	}
}