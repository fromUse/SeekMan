package com.seekman.library.bean;

/**
 * Created by chen-gui on 16-5-26.
 */
public class ImageURL{
    private String user_id = null;
    private String user_name = null;
    private String image_url = null;

    public ImageURL(String user_id, String image_url, String user_name) {
        this.user_id = user_id;
        this.image_url = image_url;
        this.user_name = user_name;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    @Override
    public String toString() {
        return "ImageURL{" +
                "user_id='" + user_id + '\'' +
                ", user_name='" + user_name + '\'' +
                ", image_url='" + image_url + '\'' +
                '}';
    }
}
