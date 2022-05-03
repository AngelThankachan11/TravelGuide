package com.travel.guide.apicalls.model;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class NotificationResBean {

	@SerializedName("notification")
	private List<NotificationItem> notification;

	@SerializedName("status")
	private boolean status;

	public List<NotificationItem> getNotification(){
		return notification;
	}

	public boolean isStatus(){
		return status;
	}

	public class NotificationItem{

		@SerializedName("updated_at")
		private String updatedAt;

		@SerializedName("user_id")
		private String userId;

		@SerializedName("description")
		private String description;

		@SerializedName("created_at")
		private String createdAt;

		@SerializedName("id")
		private int id;

		@SerializedName("title")
		private String title;

		@SerializedName("image")
		private String image;

		public String getUpdatedAt(){
			return updatedAt;
		}

		public String getUserId(){
			return userId;
		}

		public String getDescription(){
			return description;
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

		public String getImage(){
			return image;
		}
	}
}