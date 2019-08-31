package com.vaadin.example.vaadin.domain;

import com.vaadin.example.vaadin.enumeration.PhoneNumberType;

public class PhoneNumber {
    private String phoneNumber ;
    private PhoneNumberType phoneNumberType ;

    public PhoneNumber() {
    }

    public PhoneNumber(String phoneNumber, PhoneNumberType phoneNumberType) {
        this.phoneNumber = phoneNumber;
        this.phoneNumberType = phoneNumberType;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public PhoneNumberType getPhoneNumberType() {
        return phoneNumberType;
    }

    public void setPhoneNumberType(PhoneNumberType phoneNumberType) {
        this.phoneNumberType = phoneNumberType;
    }
}
