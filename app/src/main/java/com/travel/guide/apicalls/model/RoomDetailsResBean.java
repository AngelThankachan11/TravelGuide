package com.travel.guide.apicalls.model;

import java.io.Serializable;
import java.util.List;
import com.google.gson.annotations.SerializedName;

public class RoomDetailsResBean implements Serializable {

	@SerializedName("wallet")
	private Wallet wallet;

	@SerializedName("data")
	private List<DataItem> data;

	@SerializedName("status")
	private boolean status;

	public Wallet getWallet(){
		return wallet;
	}

	public List<DataItem> getData(){
		return data;
	}

	public boolean isStatus(){
		return status;
	}

	public class DataItem implements Serializable{

		@SerializedName("room_id")
		private String roomId;

		@SerializedName("owner_id")
		private String ownerId;

		@SerializedName("icon")
		private String icon;

		@SerializedName("created_at")
		private String createdAt;

		@SerializedName("default_icon")
		private String defaultIcon;

		@SerializedName("service_room_type_images")
		private List<ServiceRoomTypeImagesItem> serviceRoomTypeImages;

		@SerializedName("updated_at")
		private String updatedAt;

		@SerializedName("price")
		private String price;

		@SerializedName("name")
		private String name;

		@SerializedName("id")
		private int id;

		@SerializedName("service_property_id")
		private String servicePropertyId;

		@SerializedName("room_type")
		private String roomType;

		@SerializedName("status")
		private String status;

		@SerializedName("is_selected")
		private boolean isSelected;

		public String getRoomId(){
			return roomId;
		}

		public String getOwnerId(){
			return ownerId;
		}

		public String getIcon(){
			return icon;
		}

		public String getCreatedAt(){
			return createdAt;
		}

		public String getDefaultIcon(){
			return defaultIcon;
		}

		public List<ServiceRoomTypeImagesItem> getServiceRoomTypeImages(){
			return serviceRoomTypeImages;
		}

		public String getUpdatedAt(){
			return updatedAt;
		}

		public String getPrice(){
			return price;
		}

		public String getName(){
			return name;
		}

		public int getId(){
			return id;
		}

		public String getServicePropertyId(){
			return servicePropertyId;
		}

		public String getRoomType(){
			return roomType;
		}

		public String getStatus(){
			return status;
		}

		public boolean getIsSelected(){
			return isSelected;
		}

		public void setSelected(boolean isSelected){
			this.isSelected = isSelected;
		}

		public class ServiceRoomTypeImagesItem implements Serializable{

			@SerializedName("image")
			private String image;

			@SerializedName("service_room_type_id")
			private String serviceRoomTypeId;

			@SerializedName("id")
			private int id;

			public String getImage(){
				return image;
			}

			public String getServiceRoomTypeId(){
				return serviceRoomTypeId;
			}

			public int getId(){
				return id;
			}
		}
	}

	public class Wallet implements Serializable{

		@SerializedName("wallet_amount")
		private String walletAmount;

		public String getWalletAmount(){
			return walletAmount;
		}
	}
}