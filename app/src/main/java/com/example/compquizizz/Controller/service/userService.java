package com.example.compquizizz.Controller.service;

import com.example.compquizizz.Model.user;

import java.util.List;

public interface userService {

    public String registerUser(user User);
    public String updateUser(user User);
    public String deleteUser(user User);
    public String loginUser(String email,String password);
    public List<user> getallUser();
    public user getuserByName(String uName);
    public user getuserUID(String uName);
}
