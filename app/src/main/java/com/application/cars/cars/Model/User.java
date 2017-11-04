package com.application.cars.cars.Model;

/**
 * Created by kailash on 04-11-2017.
 */

public class User {
    private long userId;
    private String userName;

    public User(long userId, String userName) {
        this.userId = userId;
        this.userName = userName;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
