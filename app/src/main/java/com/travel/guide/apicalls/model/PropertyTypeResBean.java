package com.travel.guide.apicalls.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PropertyTypeResBean {

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

		@SerializedName("image")
		private String image;

		@SerializedName("name")
		private String name;

		@SerializedName("id")
		private int id;

		@SerializedName("isSelected")
		private boolean isSelected;

		public String getImage(){
			return image;
		}

		public String getName(){
			return name;
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