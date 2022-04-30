package com.zene.tmtpawssyetm.Model;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.zene.tmtpawssyetm.UI.LoginActivityV2;
import com.zene.tmtpawssyetm.R;

public class Note {
    private String title;
    private String content;

    public Note(){}
    public Note(String title, String content){
        this.title = title;
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public static class TMTPawsUserData {

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

    public static class UserInfo {

        private String email;

        private String Password;

        private String name;

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
    }

    public static class walkthrough extends AppCompatActivity {

        public static ViewPager viewPager;
        SlideViewPagerAdapter adapter;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_walkthrough);

            viewPager=findViewById(R.id.viewpager);
            adapter=new SlideViewPagerAdapter(this);
            viewPager.setAdapter(adapter);
            if (isOpenAlread())
            {
                Intent intent=new Intent(walkthrough.this, LoginActivityV2.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK| Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
            else
            {
                SharedPreferences.Editor editor=getSharedPreferences("slide",MODE_PRIVATE).edit();
                editor.putBoolean("slide",true);
                editor.commit();
            }
        }
        private boolean isOpenAlread() {
            SharedPreferences sharedPreferences=getSharedPreferences("slide",MODE_PRIVATE);
            boolean result=sharedPreferences.getBoolean("slide",false);
            return result;
        }
    }
}
