package edu.tangingina.thebackroom.model;

public class Users {
    private  String username;
    private String role;

    public Users(String username, String role){
        this.username = username;
        this.role = role;
    }

    public String getUsername(){
        return username;
    }

    public String getRole(){
        return role;
    }
}
