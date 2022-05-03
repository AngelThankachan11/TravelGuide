package com.travel.guide.apicalls.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class WalletResBean {

	@SerializedName("data")
	private Data data;

	@SerializedName("message")
	private String message;

	@SerializedName("status")
	private boolean status;

	public Data getData(){
		return data;
	}

	public String getMessage(){
		return message;
	}

	public boolean isStatus(){
		return status;
	}

	public class Data{

		@SerializedName("updated_at")
		private String updatedAt;

		@SerializedName("user_id")
		private int userId;

		@SerializedName("id")
		private int id;

		@SerializedName("transaction")
		private List<TransactionItem> transaction;

		@SerializedName("wallet_amount")
		private int walletAmount;

		public String getUpdatedAt(){
			return updatedAt;
		}

		public int getUserId(){
			return userId;
		}

		public int getId(){
			return id;
		}

		public List<TransactionItem> getTransaction(){
			return transaction;
		}

		public int getWalletAmount(){
			return walletAmount;
		}

		public class TransactionItem{

			@SerializedName("transaction_id")
			private String transactionId;

			@SerializedName("amount")
			private String amount;

			@SerializedName("created_at")
			private String createdAt;

			@SerializedName("id")
			private String id;

			@SerializedName("transaction_type")
			private String transactionType;

			public String getTransactionId(){
				return transactionId;
			}

			public String getAmount(){
				return amount;
			}

			public String getCreatedAt(){
				return createdAt;
			}

			public String getId(){
				return id;
			}

			public String getTransactionType(){
				return transactionType;
			}
		}
	}
}