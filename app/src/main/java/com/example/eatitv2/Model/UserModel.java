package com.example.eatitv2.Model;

public class UserModel {
    private String uid,name,adress,phone;

    public UserModel() {
    }

    public UserModel(String uid, String name, String adress, String phone) {
        this.uid = uid;
        this.name = name;
        this.adress = adress;
        this.phone = phone;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
