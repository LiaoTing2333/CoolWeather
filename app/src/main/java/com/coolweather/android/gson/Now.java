package com.coolweather.android.gson;

import com.google.gson.annotations.SerializedName;

/**
 * Created by 不闻不问不听不看不在乎~ on 2018/4/9.
 */

public class Now {
    @SerializedName("tmp")
    public String temperature;

    @SerializedName("cond")
    public More more;

    public class More{
        @SerializedName("txt")
        public String info;
    }
}
