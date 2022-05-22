package com.zene.tmtpawssyetm.Model;

public class SensorData {
    float Caltemp;
    float Roomthermal;

    public SensorData(){

    }

    public SensorData(float Caltemp, float Roomthermal) {
        this.Caltemp = Caltemp;
        this.Roomthermal = Roomthermal;
    }

    public float getCaltemp() {
        return Caltemp;
    }

    public void setCaltemp(float Caltemp) {
        this.Caltemp = Caltemp;
    }

    public float getRoomthermal() {
        return Roomthermal;
    }

    public void setRoomthermal(float Roomthermal) {
        this.Roomthermal = Roomthermal;
    }
}
