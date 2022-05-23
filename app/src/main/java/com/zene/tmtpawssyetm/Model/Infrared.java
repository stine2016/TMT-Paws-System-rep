package com.zene.tmtpawssyetm.Model;
//temperature_table
public class Infrared {
    String counter;
    float temp;

    public Infrared(String counter, float temp) {
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

    public float getTemp() {
        return temp;
    }

    public void setTemp(long temp) {
        this.temp = temp;
    }

    public static class PointValues {
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

    public static class ScreenItems {
        private int image;
        private String title;
        private String description;

        public int getImage() {
            return image;
        }

        public void setImage(int image) {
            this.image = image;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }
    }
}
