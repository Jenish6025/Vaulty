package com.example.datavaulty;

import android.graphics.Bitmap;

import java.util.Date;

public class ContactModel {

    String ContactID, Name, PhoneNumber, ContectEmail;
    Bitmap Picture;
    String Calltype, CallDuration;
    Date Calldate;
    boolean IsSelected;

    public ContactModel(String contactID, String name, String phoneNumber, String contectEmail, Bitmap picture, String calltype, String callDuration, Date calldate, boolean isSelected) {
        ContactID = contactID;
        Name = name;
        PhoneNumber = phoneNumber;
        ContectEmail = contectEmail;
        Picture = picture;
        Calltype = calltype;
        CallDuration = callDuration;
        Calldate = calldate;
        IsSelected = isSelected;
    }

    public String getContactID() {
        return ContactID;
    }

    public void setContactID(String contactID) {
        ContactID = contactID;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getPhoneNumber() {
        return PhoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        PhoneNumber = phoneNumber;
    }

    public String getContectEmail() {
        return ContectEmail;
    }

    public void setContectEmail(String contectEmail) {
        ContectEmail = contectEmail;
    }

    public Bitmap getPicture() {
        return Picture;
    }

    public void setPicture(Bitmap picture) {
        Picture = picture;
    }

    public String getCalltype() {
        return Calltype;
    }

    public void setCalltype(String calltype) {
        Calltype = calltype;
    }

    public String getCallDuration() {
        return CallDuration;
    }

    public void setCallDuration(String callDuration) {
        CallDuration = callDuration;
    }

    public Date getCalldate() {
        return Calldate;
    }

    public void setCalldate(Date calldate) {
        Calldate = calldate;
    }

    public boolean isSelected() {
        return IsSelected;
    }

    public void setSelected(boolean selected) {
        IsSelected = selected;
    }
}
