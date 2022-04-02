package com.zene.tmtpawssyetm;

public class UserInfo {

    private String email;

    private String Password;

    private String esp32;

    private String serialnumber;

    public UserInfo(){

    }
    public String getEmail() {
        return email;
    }

    public void setEmail(String email){
        this.email = email;

    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String Password){
        this.Password = Password;

    }
    public String getEsp32() {
        return esp32;
    }

    public void setEsp32(String esp32){
        this.esp32 = esp32;

    }
    public String getSerialnumber() {
        return serialnumber;
    }

    public void setSerialnumber(String serialnumber){
        this.serialnumber = serialnumber;

    }
}
