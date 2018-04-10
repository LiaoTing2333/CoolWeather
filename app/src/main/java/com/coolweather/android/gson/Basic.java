package com.coolweather.android.gson;


import com.google.gson.annotations.SerializedName;

/**
 * Created by 不闻不问不听不看不在乎~ on 2018/4/9.
 */

public class Basic {
    @SerializedName("city")
    public String  cityname;

    @SerializedName("id")
    public String weatherId;

    public Update update;

    public class Update{
        @SerializedName("loc")
        public String updateTime;
    }
}
