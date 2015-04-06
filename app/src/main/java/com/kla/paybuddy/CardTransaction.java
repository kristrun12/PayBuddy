package com.kla.paybuddy;

import android.support.annotation.XmlRes;

import java.util.Date;

/**
 * Created by kla on 20.2.2015.
 */
public class CardTransaction
{
    private Date date = new Date();
    private int price;
    private String tokenitem;
    private int device_id;
    private String appPin;
    private String posPin;

    public String getVendor() {
        return vendor;
    }

    public void setVendor(String vendor) {
        this.vendor = vendor;
    }

    private String vendor;

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getDevice_id() {
        return device_id;
    }

    public void setDevice_id(int device_id) {
        this.device_id = device_id;
    }

    public String getAppPin() {
        return appPin;
    }

    public void setAppPin(String appPin) {
        this.appPin = appPin;
    }

    public String getPosPin() {
        return posPin;
    }

    public void setPosPin(String posPin) {
        this.posPin = posPin;
    }



    public String getTokenitem() {
        return tokenitem;
    }

    public void setTokenitem(String tokenitem) {
        this.tokenitem = tokenitem;
    }


    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

}
