package com.travel.guide.apicalls.model;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class PriceRangeResBean {

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

		@SerializedName("updated_at")
		private String updatedAt;

		@SerializedName("end_price")
		private String endPrice;

		@SerializedName("created_at")
		private String createdAt;

		@SerializedName("start_price")
		private String startPrice;

		@SerializedName("id")
		private String id;

		@SerializedName("title")
		private String title;

		@SerializedName("isChecked")
		private boolean isChecked;

		public String getUpdatedAt(){
			return updatedAt;
		}

		public String getEndPrice(){
			return endPrice;
		}

		public String getCreatedAt(){
			return createdAt;
		}

		public String getStartPrice(){
			return startPrice;
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