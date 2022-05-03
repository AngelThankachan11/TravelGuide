package com.travel.guide.apicalls.model;

import java.io.Serializable;
import java.util.List;
import com.google.gson.annotations.SerializedName;

public class ExploreMoreResBean implements Serializable {

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

		@SerializedName("image")
		private String image;

		@SerializedName("title")
		private String title;

		@SerializedName("description")
		private String description;

		@SerializedName("updated_at")
		private String updatedAt;

		@SerializedName("created_at")
		private String createdAt;

		@SerializedName("id")
		private String id;

		public String getImage(){
			return image;
		}

		public String getUpdatedAt(){
			return updatedAt;
		}

		public String getCreatedAt(){
			return createdAt;
		}

		public String getId(){
			return id;
		}

		public String getTitle(){
			return title;
		}

		public String getDescription(){
			return description;
		}
	}
}