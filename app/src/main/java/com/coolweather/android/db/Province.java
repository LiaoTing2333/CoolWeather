package com.coolweather.android.db;

import org.litepal.crud.DataSupport;

/**
 * Created by 不闻不问不听不看不在乎~ on 2018/4/8.
 */

public class Province extends DataSupport {
    private int id;
    private String provinceName;
    private int provinceCode;
    public int getId(){
        return id;
    }
    public void setId(){
        this.id=id;
    }
    public String getProvinceName(){
        return provinceName;
    }
    public void setProvinceName(String provinceName){
        this.provinceName=provinceName;
    }
    public int getProvinceCode(){
        return provinceCode;
    }
    public void  setProvinceCode(int  provinceCode){
        this.provinceCode=provinceCode;
    }
}
