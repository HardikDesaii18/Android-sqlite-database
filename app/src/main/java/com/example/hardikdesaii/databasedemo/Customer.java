package com.example.hardikdesaii.databasedemo;

/**
 * Created by HardikDesaii on 18/01/17.
 */

public class Customer {
    private String name, email, mobile, photo;

    public Customer(String name, String email, String mobile, String photo) {
        this.name = name;
        this.email = email;
        this.mobile = mobile;
        this.photo = photo;
    }

    public String getName() {
        return this.name;
    }

    public String getEmail() {
        return this.email;
    }

    public String getMobile() {
        return this.mobile;
    }

    public String getPhoto() {
        return this.photo;
    }
}
