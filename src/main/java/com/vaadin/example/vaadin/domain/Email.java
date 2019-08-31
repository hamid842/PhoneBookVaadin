package com.vaadin.example.vaadin.domain;

import com.vaadin.example.vaadin.enumeration.EmailType;

public class Email {
    private String email ;
    private EmailType emailType ;

    public Email() {
    }

    public Email(String email, EmailType emailType) {
        this.email = email;
        this.emailType = emailType;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public EmailType getEmailType() {
        return emailType;
    }

    public void setEmailType(EmailType emailType) {
        this.emailType = emailType;
    }
}
