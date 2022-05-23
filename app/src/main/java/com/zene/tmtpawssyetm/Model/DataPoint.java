package com.zene.tmtpawssyetm.Model;

public class DataPoint {
    float Caltemp;
    long Ts;

    public DataPoint() {
    }

    public DataPoint(float Caltemp, long Ts) {
        this.Caltemp = Caltemp;
        this.Ts = Ts;
    }

    public float getCaltemp() {
        return Caltemp;
    }

    public void setCaltemp(float Caltemp) {
        this.Caltemp = Caltemp;
    }

    public long getTs() {
        return Ts;
    }

    public void setTs(long Ts) {
        this.Ts = Ts;
    }
}
