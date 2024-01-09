package com.example.compquizizz.Model;

public class user {
    String FirstName, Email,Password,Country,LastName,UserName;
    int age;
    //testcommit
    public user(String firstName, String email, String password, String country, String lastName, int age, String userName) {
        this.FirstName = firstName;
        this.Email = email;
        Password = password;
        this.Country = country;
        this.LastName = lastName;
        this.age = age;
        this.UserName=userName;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        this.UserName = userName;
    }

    public String getFirstName() {
        return FirstName;
    }

    public void setFirstName(String firstName) {
        this.FirstName = firstName;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        this.Email = email;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getCountry() {
        return Country;
    }

    public void setCountry(String country) {
        this.Country = country;
    }

    public String getLastName() {
        return LastName;
    }

    public void setLastName(String lastName) {
        this.LastName = lastName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
