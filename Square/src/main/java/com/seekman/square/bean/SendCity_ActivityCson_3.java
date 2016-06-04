package com.seekman.square.bean;


import java.util.List;


public class SendCity_ActivityCson_3 {
	private List<City_ActivityGsonData> list;
	private String status;
	public SendCity_ActivityCson_3(List<City_ActivityGsonData> list, String status,
			String msg) {
		super();
		this.list = list;
		this.status = status;
		this.msg = msg;
	}
	private String msg;
	public List<City_ActivityGsonData> getList() {
		return list;
	}
	public void setList(List<City_ActivityGsonData> list) {
		this.list = list;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}

}
