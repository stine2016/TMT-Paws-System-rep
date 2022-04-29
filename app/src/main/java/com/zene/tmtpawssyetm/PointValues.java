package com.zene.tmtpawssyetm;

public class PointValues {
    float Caltemp;
    long Ts;

    public PointValues(){
    }

    public PointValues(float caltemp, long ts) {
        Caltemp = caltemp;
        Ts = ts;
    }

    public float getCaltemp() {
        return Caltemp;
    }

    public void setCaltemp(float caltemp) {
        Caltemp = caltemp;
    }

    public long getTs() {
        return Ts;
    }

    public void setTs(long ts) {
        Ts = ts;
    }
}
