package com.travel.guide.apicalls.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PropertyDetailsResBean {

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

		@SerializedName("business_name")
		private String businessName;

		@SerializedName("image")
		private String image;

		@SerializedName("amenities")
		private List<AmenitiesItem> amenities;

		@SerializedName("address")
		private String address;

		@SerializedName("hostelowner_id")
		private int hostelownerId;

		@SerializedName("businesscat_id")
		private int businesscatId;

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
		private String location;

		@SerializedName("id")
		private int id;

		@SerializedName("longitude")
		private String longitude;

		@SerializedName("status")
		private int status;

		@SerializedName("service_property_images")
		private List<ServicePropertyImagesItem> servicePropertyImages;

		@SerializedName("is_wishlist")
		private String isWishlist;

		public String getBusinessName(){
			return businessName;
		}

		public String getImage(){
			return image;
		}

		public List<AmenitiesItem> getAmenities(){
			return amenities;
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

		public String getLocation(){
			return location;
		}

		public int getId(){
			return id;
		}

		public String getLongitude(){
			return longitude;
		}

		public int getStatus(){
			return status;
		}

		public List<ServicePropertyImagesItem> getServicePropertyImages(){
			return servicePropertyImages;
		}

		public String getIsWishList(){
			return isWishlist;
		}

		public void setIsWishList(String isWishList){
			this.isWishlist = isWishList;
		}

	}

	public class ServicePropertyImagesItem{

		@SerializedName("image")
		private String image;

		@SerializedName("updated_at")
		private String updatedAt;

		@SerializedName("created_at")
		private String createdAt;

		@SerializedName("id")
		private int id;

		@SerializedName("service_property_id")
		private int servicePropertyId;

		public String getImage(){
			return image;
		}

		public String getUpdatedAt(){
			return updatedAt;
		}

		public String getCreatedAt(){
			return createdAt;
		}

		public int getId(){
			return id;
		}

		public int getServicePropertyId(){
			return servicePropertyId;
		}
	}

	public class AmenitiesItem{

		@SerializedName("name")
		private String name;

		@SerializedName("icon")
		private String icon;

		@SerializedName("id")
		private int id;

		public String getName(){
			return name;
		}

		public String getIcon(){
			return icon;
		}

		public int getId(){
			return id;
		}
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