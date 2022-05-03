package com.travel.guide.apicalls.model;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class MyBookingsResBean {

	@SerializedName("bookings")
	private List<BookingsItem> bookings;

	@SerializedName("status")
	private boolean status;

	public List<BookingsItem> getBookings(){
		return bookings;
	}

	public boolean isStatus(){
		return status;
	}

	public class BookingsItem{

		@SerializedName("room_id")
		private String roomId;

		@SerializedName("transaction_id")
		private String transactionId;

		@SerializedName("unique_id")
		private String uniqueId;

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

		public String getUniqueId(){
			return uniqueId;
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

		public class Hostelowner{

			@SerializedName("profile_image")
			private String profileImage;

			@SerializedName("address")
			private String address;

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

			public String getAddress(){
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

		public class ServiceProperty{

			@SerializedName("business_name")
			private String businessName;

			@SerializedName("manager_name")
			private String managerName;

			@SerializedName("image")
			private String image;

			@SerializedName("address")
			private String address;

			@SerializedName("hostelowner_id")
			private String hostelownerId;

			@SerializedName("latitude")
			private String latitude;

			@SerializedName("id")
			private int id;

			@SerializedName("longitude")
			private String longitude;

			@SerializedName("business_type")
			private String business_type;

			public String getBusinessName(){
				return businessName;
			}

			public String getManagerName(){
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

			public int getId(){
				return id;
			}

			public String getLongitude(){
				return longitude;
			}

			public String getBusinessType(){
				return business_type;
			}
		}
	}
}