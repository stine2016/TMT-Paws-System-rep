package com.zene.tmtpawssyetm.Model;

public class TableModel {
    float Caltemp;
    long Ts;

    public TableModel(){

    }

    public TableModel(float Caltemp, long Ts) {
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
