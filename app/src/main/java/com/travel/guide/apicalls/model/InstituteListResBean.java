package com.travel.guide.apicalls.model;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class InstituteListResBean {

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

		@SerializedName("updated_at")
		private String updatedAt;

		@SerializedName("city")
		private String city;

		@SerializedName("institutecopies")
		private List<InstitutecopiesItem> institutecopies;

		@SerializedName("name")
		private String name;

		@SerializedName("created_at")
		private String createdAt;

		@SerializedName("id")
		private int id;

		@SerializedName("state")
		private String state;

		public String getImage(){
			return image;
		}

		public String getUpdatedAt(){
			return updatedAt;
		}

		public String getCity(){
			return city;
		}

		public List<InstitutecopiesItem> getInstitutecopies(){
			return institutecopies;
		}

		public String getName(){
			return name;
		}

		public String getCreatedAt(){
			return createdAt;
		}

		public int getId(){
			return id;
		}

		public String getState(){
			return state;
		}

		public class InstitutecopiesItem{

			@SerializedName("branch_name")
			private String branchName;

			@SerializedName("id")
			private int id;

			@SerializedName("institute_id")
			private String instituteId;

			public String getBranchName(){
				return branchName;
			}

			public int getId(){
				return id;
			}

			public String getInstituteId(){
				return instituteId;
			}
		}
	}
}