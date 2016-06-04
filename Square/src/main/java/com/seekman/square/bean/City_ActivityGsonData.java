package com.seekman.square.bean;

public class City_ActivityGsonData {
	
	private Integer id;
	private String att_Title;
	private String att_Time;
	private String att_Address;
	
	private String att_Content;

	private  String user_username;
	private String city_name;
	private String theme_name;
	private String  picture_name;

	public City_ActivityGsonData(Integer id, String att_Title, String att_Time, String att_Address, String att_Content, String city_name, String user_username, String theme_name, String picture_name) {
		this.id = id;
		this.att_Title = att_Title;
		this.att_Time = att_Time;
		this.att_Address = att_Address;
		this.att_Content = att_Content;
		this.city_name = city_name;
		this.user_username = user_username;
		this.theme_name = theme_name;
		this.picture_name = picture_name;
	}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getAtt_Title() {
		return att_Title;
	}

	public void setAtt_Title(String att_Title) {
		this.att_Title = att_Title;
	}

	public String getAtt_Time() {
		return att_Time;
	}

	public void setAtt_Time(String att_Time) {
		this.att_Time = att_Time;
	}

	public String getAtt_Address() {
		return att_Address;
	}

	public void setAtt_Address(String att_Address) {
		this.att_Address = att_Address;
	}

	public String getAtt_Content() {
		return att_Content;
	}

	public void setAtt_Content(String att_Content) {
		this.att_Content = att_Content;
	}

	public String getUser_username() {
		return user_username;
	}

	public void setUser_username(String user_username) {
		this.user_username = user_username;
	}

	public String getCity_name() {
		return city_name;
	}

	public void setCity_name(String city_name) {
		this.city_name = city_name;
	}

	public String getTheme_name() {
		return theme_name;
	}

	public void setTheme_name(String theme_name) {
		this.theme_name = theme_name;
	}

	public String getPicture_name() {
		return picture_name;
	}

	public void setPicture_name(String picture_name) {
		this.picture_name = picture_name;
	}

	

}
