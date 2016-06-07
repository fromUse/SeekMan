package com.seekman.personal.fragment;

/**
 * Created by Administrator on 2016/5/31 0031.
 */
public class Person {
    public void setUrl(String url) {
        this.url = url;
    }

    private String url;

    public String getUrl() {
        return url;
    }

    private String user_Passwd;
    private String user_Name;
    private String user_Nickname;
    private String user_Sex;
    private String user_Age;
    private String user_Images;
    private String city_Name;


    public void setUser_Passwd(String user_Passwd) {
        this.user_Passwd = user_Passwd;
    }

    public void setUser_Name(String user_Name) {
        this.user_Name = user_Name;
    }

    public void setUser_Nickname(String user_Nickname) {
        this.user_Nickname = user_Nickname;
    }

    public void setUser_Sex(String user_Sex) {
        this.user_Sex = user_Sex;
    }

    public void setUser_Age(String user_Age) {
        this.user_Age = user_Age;
    }

    public void setUser_Images(String user_Images) {
        this.user_Images = user_Images;
    }

    public void setCity_Name(String city_Name) {
        this.city_Name = city_Name;
    }


    public String getUser_Passwd() {
        return user_Passwd;
    }

    public String getUser_Name() {
        return user_Name;
    }

    public String getUser_Nickname() {
        return user_Nickname;
    }

    public String getUser_Sex() {
        return user_Sex;
    }

    public String getUser_Age() {
        return user_Age;
    }

    public String getUser_Images() {
        return user_Images;
    }

    public String getCity_Name() {
        return city_Name;
    }
}
