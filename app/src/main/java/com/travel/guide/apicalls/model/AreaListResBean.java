package com.travel.guide.apicalls.model;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class AreaListResBean {

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

		@SerializedName("area")
		private String area;

		@SerializedName("id")
		private String id;

		public String getArea(){
			return area;
		}

		public String getId(){
			return id;
		}
	}
}