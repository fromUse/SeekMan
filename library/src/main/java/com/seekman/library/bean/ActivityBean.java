package com.seekman.library.bean;

import java.util.List;

/**
 * Created by chen-gui on 16-5-26.
 */
public class ActivityBean {
    private String id = null;
    /** 活动id **/
    private String att_title = null;
    /** 活动标题 **/
    private String att_time = null;
    /** 活动进行时间 **/
    private String att_address = null;
    /** 活动地址 **/
    private List<ImageURL> images = null;
    /** 图片地址集合 **/
    private String att_content = null;
    /** 活动具体内容 **/
    private String ua_id = null;
    /**    **/
    private String city_id = null;
    /** 城市id **/
    private String theme_id = null;
    /** 活动主题id **/
    private String user_id = null;
    /** 用户id **/
    private String user_name = null;

    /** 用户名 **/

    private String theme_name = null;

    public ActivityBean(String id, String att_title, String att_time, String att_address, List<ImageURL> images, String att_content, String ua_id, String city_id, String theme_id, String user_id, String user_name, String theme_name) {
        this.id = id;
        this.att_title = att_title;
        this.att_time = att_time;
        this.att_address = att_address;
        this.images = images;
        this.att_content = att_content;
        this.ua_id = ua_id;
        this.city_id = city_id;
        this.theme_id = theme_id;
        this.user_id = user_id;
        this.user_name = user_name;
        this.theme_name = theme_name;
    }

    public ActivityBean() {
    }

    /** 主题名 **/


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAtt_title() {
        return att_title;
    }

    public void setAtt_title(String att_title) {
        this.att_title = att_title;
    }

    public String getAtt_time() {
        return att_time;
    }

    public void setAtt_time(String att_time) {
        this.att_time = att_time;
    }

    public String getAtt_address() {
        return att_address;
    }

    public void setAtt_address(String att_address) {
        this.att_address = att_address;
    }

    public List<ImageURL> getImages() {
        return images;
    }

    public void setImages(List<ImageURL> images) {
        this.images = images;
    }

    public String getAtt_content() {
        return att_content;
    }

    public void setAtt_content(String att_content) {
        this.att_content = att_content;
    }

    public String getUa_id() {
        return ua_id;
    }

    public void setUa_id(String ua_id) {
        this.ua_id = ua_id;
    }

    public String getCity_id() {
        return city_id;
    }

    public void setCity_id(String city_id) {
        this.city_id = city_id;
    }

    public String getTheme_id() {
        return theme_id;
    }

    public void setTheme_id(String theme_id) {
        this.theme_id = theme_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getTheme_name() {
        return theme_name;
    }

    public void setTheme_name(String theme_name) {
        this.theme_name = theme_name;
    }

    @Override
    public String toString() {
        return "ActivityBean{" +
                "id='" + id + '\'' +
                ", att_title='" + att_title + '\'' +
                ", att_time='" + att_time + '\'' +
                ", att_address='" + att_address + '\'' +
                ", images=" + images +
                ", att_content='" + att_content + '\'' +
                ", ua_id='" + ua_id + '\'' +
                ", city_id='" + city_id + '\'' +
                ", theme_id='" + theme_id + '\'' +
                ", user_id='" + user_id + '\'' +
                ", user_name='" + user_name + '\'' +
                ", theme_name='" + theme_name + '\'' +
                '}';
    }
}
