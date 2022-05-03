package com.travel.guide.apicalls.model;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class DistanceRangeResBean {

	@SerializedName("distance")
	private List<DistanceItem> distance;

	@SerializedName("status")
	private boolean status;

	public List<DistanceItem> getDistance(){
		return distance;
	}

	public boolean isStatus(){
		return status;
	}

	public class DistanceItem{

		@SerializedName("distance")
		private String distance;

		@SerializedName("updated_at")
		private String updatedAt;

		@SerializedName("created_at")
		private String createdAt;

		@SerializedName("id")
		private String id;

		@SerializedName("title")
		private String title;

		@SerializedName("isChecked")
		private boolean isChecked;

		public String getDistance(){
			return distance;
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

		public boolean getIsChecked(){
			return isChecked;
		}

		public void setIsChecked(boolean isChecked){
			this.isChecked = isChecked;
		}
	}
}