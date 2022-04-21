package com.zene.tmtpawssyetm.Model;

public class Category {

    private String userName;

    public Category() {
    }

    public Category(String userName, String userSurname, String userNumber) {
        this.userName = userName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

}
