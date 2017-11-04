package com.application.cars.cars.Model;

/**
 * Created by kailash on 04-11-2017.
 */

public class Car {
    private String caompanyName;
    private String model;
    private String RegNo;
    private long _userId;

    public Car() {

    }

    public Car(String caompanyName, String model, String regNo, long _userId) {
        this.caompanyName = caompanyName;
        this.model = model;
        RegNo = regNo;
        this._userId = _userId;
    }

    public String getCaompanyName() {
        return caompanyName;
    }

    public void setCaompanyName(String caompanyName) {
        this.caompanyName = caompanyName;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getRegNo() {
        return RegNo;
    }

    public void setRegNo(String regNo) {
        RegNo = regNo;
    }

    public long get_userId() {
        return _userId;
    }

    public void set_userId(long _userId) {
        this._userId = _userId;
    }
}
