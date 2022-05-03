package com.travel.guide.apicalls.model;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class CouponListResBean {

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

		@SerializedName("end_date")
		private String endDate;

		@SerializedName("coupon_code")
		private String couponCode;

		@SerializedName("cart_value")
		private String cartValue;

		@SerializedName("coupon_image")
		private String couponImage;

		@SerializedName("begin_date")
		private String beginDate;

		@SerializedName("coupon_uses")
		private String couponUses;

		@SerializedName("description")
		private String description;

		@SerializedName("created_at")
		private String createdAt;

		@SerializedName("coupon_name")
		private String couponName;

		@SerializedName("coupon_limit")
		private String couponLimit;

		@SerializedName("updated_at")
		private String updatedAt;

		@SerializedName("vendor_id")
		private String vendorId;

		@SerializedName("id")
		private int id;

		@SerializedName("coupon_discount")
		private String couponDiscount;

		@SerializedName("coupon_type")
		private String couponType;

		@SerializedName("status")
		private String status;

		public String getEndDate(){
			return endDate;
		}

		public String getCouponCode(){
			return couponCode;
		}

		public String getCartValue(){
			return cartValue;
		}

		public String getCouponImage(){
			return couponImage;
		}

		public String getBeginDate(){
			return beginDate;
		}

		public String getCouponUses(){
			return couponUses;
		}

		public String getDescription(){
			return description;
		}

		public String getCreatedAt(){
			return createdAt;
		}

		public String getCouponName(){
			return couponName;
		}

		public String getCouponLimit(){
			return couponLimit;
		}

		public String getUpdatedAt(){
			return updatedAt;
		}

		public String getVendorId(){
			return vendorId;
		}

		public int getId(){
			return id;
		}

		public String getCouponDiscount(){
			return couponDiscount;
		}

		public String getCouponType(){
			return couponType;
		}

		public String getStatus(){
			return status;
		}
	}
}