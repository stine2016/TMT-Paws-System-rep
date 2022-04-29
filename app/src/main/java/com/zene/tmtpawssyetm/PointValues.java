package com.zene.tmtpawssyetm;

public class PointValues {
    float Caltemp;
    long timestamp;

    public PointValues(float Caltemp, long timestamp){
        this.Caltemp = Caltemp;
        this.timestamp = timestamp;
    }

    public PointValues(){
    }

    public float getCaltemp() {
        return Caltemp;
    }

    public void setCaltemp(float Caltemp) {
        this.Caltemp = Caltemp;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}
