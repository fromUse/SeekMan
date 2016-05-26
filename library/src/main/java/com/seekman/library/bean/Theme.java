package com.seekman.library.bean;

/**
 * Created by chen-gui on 16-5-26.
 */
public class Theme {

    private String id = null;/**主题id**/
    private String theme_name = null;/**主题名称**/
    private String theme_img = null;/**图片url**/

    public Theme(String id, String theme_img, String theme_name) {
        this.id = id;
        this.theme_img = theme_img;
        this.theme_name = theme_name;
    }

    public Theme() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTheme_name() {
        return theme_name;
    }

    public void setTheme_name(String theme_name) {
        this.theme_name = theme_name;
    }

    public String getTheme_img() {
        return theme_img;
    }

    public void setTheme_img(String theme_img) {
        this.theme_img = theme_img;
    }

    @Override
    public String toString() {
        return "Theme{" +
                "id='" + id + '\'' +
                ", theme_name='" + theme_name + '\'' +
                ", theme_img='" + theme_img + '\'' +
                '}';
    }
}
