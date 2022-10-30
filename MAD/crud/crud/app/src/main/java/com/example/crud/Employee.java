package com.example.crud;

public class Employee {

    private String name;
    private String phoneNumber;
    private String Deatil;

    public Employee(){}

    public Employee(String name, String phoneNumber, String deatil) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.Deatil = deatil;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getDeatil() {
        return Deatil;
    }

    public void setDeatil(String deatil) {
        Deatil = deatil;
    }
}
