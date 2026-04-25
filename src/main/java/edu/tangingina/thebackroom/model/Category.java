package edu.tangingina.thebackroom.model;

public class Category {
    private int id;
    private String categoryName;

    public Category(int id, String name){
        this.id = id;
        this.categoryName = name;
    }

    public Integer getCategoryID(){
        return id;
    }

    public String getCategoryName(){
        return categoryName;
    }

    public void setCategoryID(int id){this.id = id;}
}
