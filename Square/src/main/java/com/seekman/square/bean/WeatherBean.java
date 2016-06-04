package com.seekman.square.bean;

import com.seekman.square.bean.Results;

import java.util.ArrayList;
public class WeatherBean {
    private String data;
    private int error;
    private ArrayList<Results> results;
    private boolean status;

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public int getError() {
        return error;
    }

    public void setError(int error) {
        this.error = error;
    }

    public ArrayList<Results> getResults() {
        return results;
    }

    public void setResults(ArrayList<Results> results) {
        this.results = results;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

}
