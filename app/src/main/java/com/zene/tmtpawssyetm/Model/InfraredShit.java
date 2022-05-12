package com.zene.tmtpawssyetm.Model;

public class InfraredShit {
    String counter;
    float temp;

    public InfraredShit(){
    }

    public InfraredShit(String counter, float temp) {
        this.counter = counter;
        this.temp = temp;
    }

    public String getCounter() {
        return counter;
    }

    public void setCounter(String counter) {
        this.counter = counter;
    }

    public float getTemp() {
        return temp;
    }

    public void setTemp(float temp) {
        this.temp = temp;
    }
}
