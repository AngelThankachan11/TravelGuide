package com.travel.guide.apicalls.model;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class RecommendedResBean {

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

	public class DataItem{

		@SerializedName("business_name")
		private String businessName;

		@SerializedName("image")
		private String image;

		@SerializedName("address")
		private String address;

		@SerializedName("hostelowner_id")
		private String hostelownerId;

		@SerializedName("businesscat_id")
		private String businesscatId;

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
		private int id;

		@SerializedName("longitude")
		private String longitude;

		@SerializedName("status")
		private String status;

		@SerializedName("service_property_images")
		private List<Object> servicePropertyImages;

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

		public String getBusinesscatId(){
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

		public int getId(){
			return id;
		}

		public String getLongitude(){
			return longitude;
		}

		public String getStatus(){
			return status;
		}

		public List<Object> getServicePropertyImages(){
			return servicePropertyImages;
		}

		public class Businesscat{

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
			private String status;

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

			public String getStatus(){
				return status;
			}
		}
	}
}