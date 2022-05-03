package com.travel.guide.apicalls.model;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class SearchPropertyResBean {

    @SerializedName("data")
    private List<Data> data;

    @SerializedName("status")
    private boolean status;

    @SerializedName("message")
    private String message;

    public List<Data> getData(){
        return data;
    }

    public boolean isStatus(){
        return status;
    }

    public String getMessage(){
        return message;
    }

    public class Data{

        @SerializedName("id")
        private int id;

        @SerializedName("business_name")
        private String name;

        public int getId(){
            return id;
        }

        public String getName(){
            return name;
        }

    }
}
