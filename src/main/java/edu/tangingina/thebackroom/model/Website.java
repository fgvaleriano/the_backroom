package edu.tangingina.thebackroom.model;

public class Website {
    private int id;
    private String webisteName;
    private String websiteURL;

    public Website(int id, String name, String URL){
        this.id = id;
        this.webisteName = name;
        this.websiteURL = URL;

    }

    public Integer getWebsiteID(){
        return id;
    }

    public String getWebisteName(){
        return webisteName;
    }

    public String getWebsiteURL() {
        return websiteURL;
    }

    public void setWebsiteID(int id){this.id = id;}
}
