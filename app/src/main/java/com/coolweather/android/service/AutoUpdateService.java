package com.coolweather.android.service;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.os.SystemClock;
import android.preference.PreferenceManager;

import com.coolweather.android.WeatherActivity;
import com.coolweather.android.gson.Weather;
import com.coolweather.android.util.HttpUtil;
import com.coolweather.android.util.Utility;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by 不闻不问不听不看不在乎~ on 2018/4/10.
 */

public class AutoUpdateService extends Service {
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        updateWeather();
        updateBingPic();
        //创建定时任务
        AlarmManager alarmManager=(AlarmManager)getSystemService(ALARM_SERVICE);
        int anHour=8*60*60*1000;
        //获取从设备boot后经历的时间
        long triggerAtTime= SystemClock.elapsedRealtime()+anHour;
        Intent i=new Intent(this,AutoUpdateService.class);
        PendingIntent pi=PendingIntent.getService(this,0,i,0);
        alarmManager.cancel(pi);
        //设置闹钟
        alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP,triggerAtTime,pi);
        return super.onStartCommand(intent, flags, startId);
    }

    private void updateWeather(){
        SharedPreferences preferences= PreferenceManager
                .getDefaultSharedPreferences(this);
        String weatherString=preferences.getString("weather",null);
        if(weatherString!=null) {
            Weather weather = Utility.handleWeatherResponse(weatherString);
            String weatherId = weather.basic.weatherId;

            String weatherUrl="http://guolin.tech/api/weather?cityid="+
                    weatherId+"&key=248986ad16f346b1979bf4acde0e966b";
            HttpUtil.sendOkHttpRequest(weatherUrl, new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    e.printStackTrace();
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                     String responseText=response.body().string();
                    Weather weather1=Utility.handleWeatherResponse(responseText);
                    if(weather1!=null&&"ok".equals(weather1.status)){
                        SharedPreferences.Editor editor=PreferenceManager
                                .getDefaultSharedPreferences(AutoUpdateService.this).edit();
                        editor.putString("weather",null);
                        editor.apply();

                    }
                }
            });
        }
    }

    private void updateBingPic(){
        String requestBingPic="http://guolin.tech/api/bing_pic";
        HttpUtil.sendOkHttpRequest(requestBingPic, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                  String reponseText=response.body().string();
                SharedPreferences.Editor editor=PreferenceManager
                        .getDefaultSharedPreferences(AutoUpdateService.this).edit();
                editor.putString("bing_pic",null);
                editor.apply();
            }
        });
    }

}
