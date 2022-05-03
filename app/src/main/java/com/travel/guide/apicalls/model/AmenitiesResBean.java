package com.travel.guide.apicalls.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AmenitiesResBean {

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

		@SerializedName("name")
		private String name;

		@SerializedName("icon")
		private String icon;

		@SerializedName("id")
		private int id;

		@SerializedName("isSelected")
		private boolean isSelected;

		public String getName(){
			return name;
		}

		public String getIcon(){
			return icon;
		}

		public int getId(){
			return id;
		}

		public boolean getIsSelected(){
			return isSelected;
		}

		public void setIsSelected(boolean isSelected){
			this.isSelected = isSelected;
		}
	}
}