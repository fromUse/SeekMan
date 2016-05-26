package com.seekman.library.bean;

/**
 * Created by chen-gui on 16-5-26.
 */
public class City  {

    private String id = null;
    private String city_name = null;
    private String city_type = null;

    public City(String id, String city_type, String city_name) {
        this.id = id;
        this.city_type = city_type;
        this.city_name = city_name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCity_type() {
        return city_type;
    }

    public void setCity_type(String city_type) {
        this.city_type = city_type;
    }

    public String getCity_name() {
        return city_name;
    }

    public void setCity_name(String city_name) {
        this.city_name = city_name;
    }

    @Override
    public String toString() {
        return "City{" +
                "id='" + id + '\'' +
                ", city_name='" + city_name + '\'' +
                ", city_type='" + city_type + '\'' +
                '}';
    }
}
