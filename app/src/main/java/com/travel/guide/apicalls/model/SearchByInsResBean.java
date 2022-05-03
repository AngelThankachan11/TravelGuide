package com.travel.guide.apicalls.model;

import java.io.Serializable;
import java.util.List;
import com.google.gson.annotations.SerializedName;

public class SearchByInsResBean implements Serializable {

	@SerializedName("data")
	private List<DataItem> data;

	@SerializedName("message")
	private String message;

	@SerializedName("status")
	private boolean status;

	public List<DataItem> getData(){
		return data;
	}

	public String getMessage(){
		return message;
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
		private String hostelownerId;

		@SerializedName("distance")
		private String distance;

		@SerializedName("city")
		private String city;

		@SerializedName("businesscat_id")
		private String businesscatId;

		@SerializedName("latitude")
		private String latitude;

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
		private int id;

		@SerializedName("state")
		private String state;

		@SerializedName("longitude")
		private String longitude;

		@SerializedName("status")
		private String status;

		public String getBusinessName(){
			return businessName;
		}

		public String getImage(){
			return image;
		}

		public String getAddress(){
			return address;
		}

		public String getHostelownerId(){
			return hostelownerId;
		}

		public String getDistance(){
			return distance;
		}

		public String getCity(){
			return city;
		}

		public String getBusinesscatId(){
			return businesscatId;
		}

		public String getLatitude(){
			return latitude;
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

		public int getId(){
			return id;
		}

		public String getState(){
			return state;
		}

		public String getLongitude(){
			return longitude;
		}

		public String getStatus(){
			return status;
		}

		public class Businesscat implements Serializable {

			@SerializedName("name")
			private String name;

			@SerializedName("id")
			private int id;

			public String getName(){
				return name;
			}

			public int getId(){
				return id;
			}
		}
	}
}