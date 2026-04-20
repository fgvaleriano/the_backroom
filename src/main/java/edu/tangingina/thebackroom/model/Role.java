package edu.tangingina.thebackroom.model;

public class Role {
    int id;
    String name;

    public Role(int id, String name){
        this.id = id;
        this.name = name;
    }

    public Integer getRoleID(){
        return id;
    }

    public String getRoleName(){
        return name;
    }
}
