package com.seekman.library.bean;

/**
 * Created by chen-gui on 16-5-26.
 */
public class User {

    private  String id = null;/***用户id*/
    private  String user_phone = null;/**用户手机**/
    private  String user_nickname = null;/**用户真实姓名**/
    private  String user_name = null;/**用户名**/
    private  String user_sex = null;/**用户性别**/
    private  String user_age = null;/**用户年龄**/
    private  String user_images = null;/**用户头像**/
    private  String ua_id = null;/**   活动id  **/
    private  String city_id = null;/***用户所在城市*/

    public User(String id, String city_id, String ua_id, String user_images, String user_age, String user_name, String user_nickname, String user_phone, String user_sex) {
        this.id = id;
        this.city_id = city_id;
        this.ua_id = ua_id;
        this.user_images = user_images;
        this.user_age = user_age;
        this.user_name = user_name;
        this.user_nickname = user_nickname;
        this.user_phone = user_phone;
        this.user_sex = user_sex;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCity_id() {
        return city_id;
    }

    public void setCity_id(String city_id) {
        this.city_id = city_id;
    }

    public String getUa_id() {
        return ua_id;
    }

    public void setUa_id(String ua_id) {
        this.ua_id = ua_id;
    }

    public String getUser_images() {
        return user_images;
    }

    public void setUser_images(String user_images) {
        this.user_images = user_images;
    }

    public String getUser_age() {
        return user_age;
    }

    public void setUser_age(String user_age) {
        this.user_age = user_age;
    }

    public String getUser_sex() {
        return user_sex;
    }

    public void setUser_sex(String user_sex) {
        this.user_sex = user_sex;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getUser_nickname() {
        return user_nickname;
    }

    public void setUser_nickname(String user_nickname) {
        this.user_nickname = user_nickname;
    }

    public String getUser_phone() {
        return user_phone;
    }

    public void setUser_phone(String user_phone) {
        this.user_phone = user_phone;
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", user_phone='" + user_phone + '\'' +
                ", user_nickname='" + user_nickname + '\'' +
                ", user_name='" + user_name + '\'' +
                ", user_sex='" + user_sex + '\'' +
                ", user_age='" + user_age + '\'' +
                ", user_images='" + user_images + '\'' +
                ", ua_id='" + ua_id + '\'' +
                ", city_id='" + city_id + '\'' +
                '}';
    }
}
