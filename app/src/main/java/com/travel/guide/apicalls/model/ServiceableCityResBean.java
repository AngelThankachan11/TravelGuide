package com.travel.guide.apicalls.model;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class ServiceableCityResBean {

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

	public class DataItem{

		@SerializedName("name")
		private String name;

		@SerializedName("id")
		private String id;

		@SerializedName("city_id")
		private String cityId;

		@SerializedName("state_name")
		private String stateName;

		@SerializedName("is_selected")
		private boolean isSelected;

		@SerializedName("image")
		private String image;

		public String getName(){
			return name;
		}

		public String getId(){
			return id;
		}

		public String getCityId(){
			return cityId;
		}

		public boolean getIsSelected(){
			return isSelected;
		}

		public void setIsSelected(boolean isSelected){
			this.isSelected = isSelected;
		}

		public String getStateName(){
			return stateName;
		}

		public String getImage(){
			return image;
		}
	}
}