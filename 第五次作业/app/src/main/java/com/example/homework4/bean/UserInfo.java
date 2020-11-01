package com.example.homework4.bean;

public class UserInfo{
        public String username;
        public String update_time;
        public int type;
        public long password;
        public long rowid;
        public String phone;
        public String pwd;
        private String id;

        public UserInfo() {
            rowid = 0L;
            username = "";
            type = 0;
            password = 0L;
            update_time = "";
            phone = "";
            pwd = "";
        }
    }
