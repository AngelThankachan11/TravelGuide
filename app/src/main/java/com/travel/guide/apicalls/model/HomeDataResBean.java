package com.travel.guide.apicalls.model;

import java.io.Serializable;
import java.util.List;
import com.google.gson.annotations.SerializedName;

public class HomeDataResBean implements Serializable {

	@SerializedName("amenities")
	private List<AmenitiesItem> amenities;

	@SerializedName("service_property")
	private List<ServicePropertyItem> serviceProperty;

	@SerializedName("banner")
	private List<BannerItem> banner;

	@SerializedName("message")
	private String message;

	@SerializedName("bookings")
	private List<BookingsItem> bookings;

	@SerializedName("category")
	private List<CategoryItem> category;

	@SerializedName("status")
	private boolean status;

	public List<AmenitiesItem> getAmenities(){
		return amenities;
	}

	public List<ServicePropertyItem> getServiceProperty(){
		return serviceProperty;
	}

	public List<BannerItem> getBanner(){
		return banner;
	}

	public String getMessage(){
		return message;
	}

	public List<BookingsItem> getBookings(){
		return bookings;
	}

	public List<CategoryItem> getCategory(){
		return category;
	}

	public boolean isStatus(){
		return status;
	}

	public class AmenitiesItem implements Serializable{

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

	public class ServicePropertyItem implements Serializable{

		@SerializedName("city")
		private String city;

		@SerializedName("latitude")
		private String latitude;

		@SerializedName("mobile_no")
		private String mobileNo;

		@SerializedName("description")
		private String description;

		@SerializedName("created_at")
		private String createdAt;

		@SerializedName("expiration_date")
		private String expirationDate;

		@SerializedName("is_wishlist")
		private String isWishlist;

		@SerializedName("businesscat")
		private Businesscat businesscat;

		@SerializedName("updated_at")
		private String updatedAt;

		@SerializedName("id")
		private int id;

		@SerializedName("state")
		private String state;

		@SerializedName("longitude")
		private String longitude;

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

		@SerializedName("payment_status")
		private String paymentStatus;

		@SerializedName("amanities")
		private String amanities;

		@SerializedName("manager_name")
		private String managerName;

		@SerializedName("max_price")
		private String maxPrice;

		@SerializedName("min_price")
		private String minPrice;

		@SerializedName("location")
		private Object location;

		@SerializedName("status")
		private String status;

		@SerializedName("service_property_images")
		private List<ServicePropertyImagesItem> servicePropertyImages;

		public String getCity(){
			return city;
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

		public String getExpirationDate(){
			return expirationDate;
		}

		public String getIsWishlist(){
			return isWishlist;
		}

		public Businesscat getBusinesscat(){
			return businesscat;
		}

		public String getUpdatedAt(){
			return updatedAt;
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

		public String getPaymentStatus(){
			return paymentStatus;
		}

		public String getAmanities(){
			return amanities;
		}

		public String getManagerName(){
			return managerName;
		}

		public String getMaxPrice(){
			return maxPrice;
		}

		public String getMinPrice(){
			return minPrice;
		}

		public Object getLocation(){
			return location;
		}

		public String getStatus(){
			return status;
		}

		public List<ServicePropertyImagesItem> getServicePropertyImages(){
			return servicePropertyImages;
		}

		public class ServicePropertyImagesItem implements Serializable{

			@SerializedName("image")
			private String image;

			@SerializedName("updated_at")
			private String updatedAt;

			@SerializedName("image_key")
			private String imageKey;

			@SerializedName("created_at")
			private String createdAt;

			@SerializedName("id")
			private int id;

			@SerializedName("service_property_id")
			private String servicePropertyId;

			public String getImage(){
				return image;
			}

			public String getUpdatedAt(){
				return updatedAt;
			}

			public String getImageKey(){
				return imageKey;
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

	public class BannerItem implements Serializable{

		@SerializedName("image")
		private String image;

		@SerializedName("updated_at")
		private String updatedAt;

		@SerializedName("created_at")
		private String createdAt;

		@SerializedName("id")
		private int id;

		@SerializedName("title")
		private String title;

		@SerializedName("type")
		private String type;

		@SerializedName("status")
		private String status;

		@SerializedName("description")
		private String description;

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

		public String getTitle(){
			return title;
		}

		public String getType(){
			return type;
		}

		public String getStatus(){
			return status;
		}

		public String getDescription(){
			return description;
		}
	}

	public class BookingsItem implements Serializable{

		@SerializedName("room_id")
		private String roomId;

		@SerializedName("transaction_id")
		private String transactionId;

		@SerializedName("coupon_amount")
		private String couponAmount;

		@SerializedName("unique_id")
		private String uniqueId;

		@SerializedName("coupon_code")
		private String couponCode;

		@SerializedName("hostelowner_id")
		private String hostelownerId;

		@SerializedName("service_property")
		private ServiceProperty serviceProperty;

		@SerializedName("user_name")
		private String userName;

		@SerializedName("booking_date")
		private String bookingDate;

		@SerializedName("payment_status")
		private String paymentStatus;

		@SerializedName("created_at")
		private String createdAt;

		@SerializedName("payment_type")
		private String paymentType;

		@SerializedName("updated_at")
		private String updatedAt;

		@SerializedName("user_id")
		private String userId;

		@SerializedName("total_amount")
		private String totalAmount;

		@SerializedName("id")
		private int id;

		@SerializedName("service_property_id")
		private String servicePropertyId;

		@SerializedName("status")
		private String status;

		@SerializedName("hostelowner")
		private Hostelowner hostelowner;

		public String getRoomId(){
			return roomId;
		}

		public String getTransactionId(){
			return transactionId;
		}

		public String getCouponAmount(){
			return couponAmount;
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

		public ServiceProperty getServiceProperty(){
			return serviceProperty;
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

		public String getCreatedAt(){
			return createdAt;
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

		public int getId(){
			return id;
		}

		public String getServicePropertyId(){
			return servicePropertyId;
		}

		public String getStatus(){
			return status;
		}

		public Hostelowner getHostelowner(){
			return hostelowner;
		}

		public class ServiceProperty implements Serializable{

			@SerializedName("business_name")
			private String businessName;

			@SerializedName("manager_name")
			private Object managerName;

			@SerializedName("image")
			private String image;

			@SerializedName("address")
			private String address;

			@SerializedName("hostelowner_id")
			private String hostelownerId;

			@SerializedName("latitude")
			private String latitude;

			@SerializedName("business_type")
			private String businessType;

			@SerializedName("id")
			private int id;

			@SerializedName("longitude")
			private String longitude;

			public String getBusinessName(){
				return businessName;
			}

			public Object getManagerName(){
				return managerName;
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

			public String getLatitude(){
				return latitude;
			}

			public String getBusinessType(){
				return businessType;
			}

			public int getId(){
				return id;
			}

			public String getLongitude(){
				return longitude;
			}
		}

		public class Hostelowner implements Serializable{

			@SerializedName("profile_image")
			private String profileImage;

			@SerializedName("address")
			private Object address;

			@SerializedName("name")
			private String name;

			@SerializedName("mobile")
			private String mobile;

			@SerializedName("id")
			private int id;

			@SerializedName("email")
			private String email;

			public String getProfileImage(){
				return profileImage;
			}

			public Object getAddress(){
				return address;
			}

			public String getName(){
				return name;
			}

			public String getMobile(){
				return mobile;
			}

			public int getId(){
				return id;
			}

			public String getEmail(){
				return email;
			}
		}
	}

	public class CategoryItem implements Serializable{

		@SerializedName("image")
		private String image;

		@SerializedName("name")
		private String name;

		@SerializedName("id")
		private int id;

		public String getImage(){
			return image;
		}

		public String getName(){
			return name;
		}

		public int getId(){
			return id;
		}
	}
}