package edu.tangingina.thebackroom.model;

public class Platform {
    private int id;
    private String platformName;

    public Platform(int id, String name){
        this.id = id;
        this.platformName = name;
    }

    public Integer getPlatformID(){
        return id;
    }

    public String getPlatformName(){
        return platformName;
    }

    public void setPlatformID(int id){this.id = id;}
}
