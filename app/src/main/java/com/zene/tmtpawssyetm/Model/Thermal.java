package com.zene.tmtpawssyetm.Model;

public class Thermal {
    String serialNumber;

    public Thermal(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public Thermal(){

    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }
}
