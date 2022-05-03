package com.travel.guide.apicalls.model;

import java.io.Serializable;
import java.util.List;
import com.google.gson.annotations.SerializedName;

public class MessDetailsResBean implements Serializable {

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

	public class DataItem implements Serializable{

		@SerializedName("lunch_to")
		private String lunchTo;

		@SerializedName("hostelowner_id")
		private String hostelownerId;

		@SerializedName("tea_to")
		private Object teaTo;

		@SerializedName("milk_to")
		private String milkTo;

		@SerializedName("created_at")
		private String createdAt;

		@SerializedName("milk_from")
		private String milkFrom;

		@SerializedName("lunch_from")
		private String lunchFrom;

		@SerializedName("dinner_from")
		private String dinnerFrom;

		@SerializedName("updated_at")
		private String updatedAt;

		@SerializedName("dinner_to")
		private String dinnerTo;

		@SerializedName("break_fast_to")
		private String breakFastTo;

		@SerializedName("break_fast_from")
		private String breakFastFrom;

		@SerializedName("tea_from")
		private Object teaFrom;

		@SerializedName("id")
		private int id;

		@SerializedName("mess_image")
		private List<MessImageItem> messImage;

		@SerializedName("service_property_id")
		private String servicePropertyId;

		@SerializedName("status")
		private String status;

		public String getLunchTo(){
			return lunchTo;
		}

		public String getHostelownerId(){
			return hostelownerId;
		}

		public Object getTeaTo(){
			return teaTo;
		}

		public String getMilkTo(){
			return milkTo;
		}

		public String getCreatedAt(){
			return createdAt;
		}

		public String getMilkFrom(){
			return milkFrom;
		}

		public String getLunchFrom(){
			return lunchFrom;
		}

		public String getDinnerFrom(){
			return dinnerFrom;
		}

		public String getUpdatedAt(){
			return updatedAt;
		}

		public String getDinnerTo(){
			return dinnerTo;
		}

		public String getBreakFastTo(){
			return breakFastTo;
		}

		public String getBreakFastFrom(){
			return breakFastFrom;
		}

		public Object getTeaFrom(){
			return teaFrom;
		}

		public int getId(){
			return id;
		}

		public List<MessImageItem> getMessImage(){
			return messImage;
		}

		public String getServicePropertyId(){
			return servicePropertyId;
		}

		public String getStatus(){
			return status;
		}

		public class MessImageItem implements Serializable{

			@SerializedName("image")
			private String image;

			@SerializedName("updated_at")
			private String updatedAt;

			@SerializedName("owner_id")
			private String ownerId;

			@SerializedName("mess_id")
			private String messId;

			@SerializedName("created_at")
			private String createdAt;

			@SerializedName("id")
			private int id;

			@SerializedName("service_property_id")
			private String servicePropertyId;

			@SerializedName("status")
			private String status;

			public String getImage(){
				return image;
			}

			public String getUpdatedAt(){
				return updatedAt;
			}

			public String getOwnerId(){
				return ownerId;
			}

			public String getMessId(){
				return messId;
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

			public String getStatus(){
				return status;
			}
		}
	}
}