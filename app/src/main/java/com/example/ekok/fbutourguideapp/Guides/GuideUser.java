package com.example.ekok.fbutourguideapp.Guides;

import java.io.Serializable;

/**
 * Created by mbytsang on 7/7/16.
 */
public class GuideUser implements Serializable{

    //Basic
    //Store profile picture
    public String legalName;
    public String location;
    public String description;
    public String languages;

    //Contact
    public String phonePrimary;
    public String phoneSecondary;
    public String email;
    public String contactAdditional;

    //Payment
    public String method;
    public String currency;
    public String timelyPay;
    public String packageDeals;
}
