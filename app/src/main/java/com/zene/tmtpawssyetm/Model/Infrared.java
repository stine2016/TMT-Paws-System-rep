package com.zene.tmtpawssyetm.Model;

public class Infrared {
    String counter;
    long temp;

    public Infrared(String counter, long temp) {
        this.counter = counter;
        this.temp = temp;
    }

    public Infrared(){
    }

    public String getCounter() {
        return counter;
    }

    public void setCounter(String counter) {
        this.counter = counter;
    }

    public long getTemp() {
        return temp;
    }

    public void setTemp(long temp) {
        this.temp = temp;
    }
}
