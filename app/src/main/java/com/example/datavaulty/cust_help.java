package com.example.datavaulty;

public class cust_help {
    String Phone_no,Name;

    public cust_help(String phone_no, String name) {
        Phone_no = phone_no;
        Name = name;
    }

    public String getPhone_no() {
        return Phone_no;
    }

    public void setPhone_no(String phone_no) {
        Phone_no = phone_no;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }
}
