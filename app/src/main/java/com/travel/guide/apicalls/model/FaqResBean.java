package com.travel.guide.apicalls.model;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class FaqResBean {

	@SerializedName("faq")
	private List<FaqItem> faq;

	@SerializedName("status")
	private boolean status;

	public List<FaqItem> getFaq(){
		return faq;
	}

	public boolean isStatus(){
		return status;
	}

	public class FaqItem{

		@SerializedName("updated_at")
		private String updatedAt;

		@SerializedName("description")
		private String description;

		@SerializedName("created_at")
		private String createdAt;

		@SerializedName("id")
		private int id;

		@SerializedName("title")
		private String title;

		@SerializedName("is_open")
		private boolean isOpen;

		public String getUpdatedAt(){
			return updatedAt;
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

		public boolean getIsOpen(){
			return isOpen;
		}

		public void setIsOpen(boolean isOpen){
			this.isOpen = isOpen;
		}
	}
}