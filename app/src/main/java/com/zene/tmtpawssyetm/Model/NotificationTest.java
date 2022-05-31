package com.zene.tmtpawssyetm.Model;

public class NotificationTest {
    String notification;
    String date;
    String temp;

    public NotificationTest(){
    }

    public NotificationTest(String notification, String date, String temp) {
        this.notification = notification;
        this.date = date;
        this.temp = temp;
    }

    public String getNotification() {
        return notification;
    }

    public void setNotification(String notification) {
        this.notification = notification;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTemp() {
        return temp;
    }

    public void setTemp(String temp) {
        this.temp = temp;
    }
}
