package com.zene.tmtpawssyetm.Model;

public class TableShit {
    float Caltemp;
    long Ts;

    public TableShit(){
    }

    public TableShit(float caltemp, long ts) {
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
