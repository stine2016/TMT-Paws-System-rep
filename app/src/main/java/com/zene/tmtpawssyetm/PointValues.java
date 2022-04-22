package com.zene.tmtpawssyetm;

public class PointValues {
    float temperature;
    long timestamp;

    public PointValues(float temperature, long timestamp){
        this.temperature = temperature;
        this.timestamp = timestamp;
    }

    public PointValues(){
    }

    public float getTemperature() {
        return temperature;
    }

    public void setTemperature(float temperature) {
        this.temperature = temperature;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}
