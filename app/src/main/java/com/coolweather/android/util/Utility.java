package com.coolweather.android.util;

import android.text.TextUtils;

import com.coolweather.android.db.City;
import com.coolweather.android.db.County;
import com.coolweather.android.db.Province;
import com.coolweather.android.gson.Weather;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by 不闻不问不听不看不在乎~ on 2018/4/8.
 */

public class Utility {
    //省
    public static boolean handleProvinceResponse(String response){
         if(!TextUtils.isEmpty(response)){
             try{
                 JSONArray allProvince=new JSONArray(response);
                 for(int i=0;i<allProvince.length();i++){
                     JSONObject provinceObject=allProvince.getJSONObject(i);
                     Province province=new Province();
                     province.setProvinceName(provinceObject.getString("name"));
                     province.setProvinceCode(provinceObject.getInt("id"));
                     province.save();
                 }
                 return true;
             }catch(Exception E){
                 E.printStackTrace();
             }
         }
        return false;
    }
//市
    public static boolean handleCityResponse(String response,int provinceId){
        if(!TextUtils.isEmpty(response)){
            try{
                JSONArray allCity=new JSONArray(response);
                for(int i=0;i<allCity.length();i++){
                    JSONObject cityObject=allCity.getJSONObject(i);
                    City city=new City();
                    city.setCityName(cityObject.getString("name"));
                    city.setCityCode(cityObject.getInt("id"));
                    city.setProvinceId(provinceId);
                    city.save();
                }
                return true;
            }catch(Exception E){
                E.printStackTrace();
            }
        }return false;
    }
  //县
    public static boolean handleCountyResponse(String response,int cityId){
        if(!TextUtils.isEmpty(response)){
            try{
                JSONArray allCounty=new JSONArray(response);
                for(int i=0;i<allCounty.length();i++){
                    JSONObject countyObject=allCounty.getJSONObject(i);
                    County county=new County();
                    county.setCountyName(countyObject.getString("name"));
                    county.setWeatherId(countyObject.getString("weather_id"));
                    county.setCityId(cityId);
                    county.save();
                }
                return true;
            }catch(Exception E){
                E.printStackTrace();
            }
        }return false;
    }
//天气
    public static Weather handleWeatherResponse(String response){
        try{
            JSONObject jsonObject=new JSONObject(response);
            JSONArray jsonArray=jsonObject.getJSONArray("HeWeather");
            String weatherContent=jsonArray.getJSONObject(0).toString();
            return new Gson().fromJson(weatherContent,Weather.class);
        }catch(Exception E){
            E.printStackTrace();
        }
        return null;
    }
}
