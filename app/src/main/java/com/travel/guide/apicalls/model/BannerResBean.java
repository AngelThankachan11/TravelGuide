package com.travel.guide.apicalls.model;

import java.io.Serializable;
import java.util.List;
import com.google.gson.annotations.SerializedName;

public class BannerResBean implements Serializable {

	@SerializedName("banner")
	private List<BannerItem> banner;

	@SerializedName("status")
	private boolean status;

	public List<BannerItem> getBanner(){
		return banner;
	}

	public boolean isStatus(){
		return status;
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
}