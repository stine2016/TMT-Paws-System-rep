package com.zene.tmtpawssyetm;

public class TMTPawsUserData {

    private String email;

    private String phonenumber;

    private String Password;

    private String name;

    private String serialnumber;

    public TMTPawsUserData() {

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
    public String getName() {
        return name;
    }

    public void setName(String name){
        this.name = name;

    }

    public String getSerialnumber() {
        return serialnumber;
    }

    public void setSerialnumber(String serialnumber){
        this.serialnumber = serialnumber;

    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }
}