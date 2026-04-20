package edu.tangingina.thebackroom.model;

public class Company {
    private int id;
    private String companyName;
    private String companyRole;
    private int companyRoleId;

    public Company(int id, String name, String role, int roleId){
        this.id = id;
        this.companyName = name;
        this.companyRole = role;
        this.companyRoleId = roleId;
    }

    public Integer getCompanyID(){
        return id;
    }

    public String getCompanyName(){
        return companyName;
    }

    public String getCompanyRole(){
        return companyRole;
    }

    public Integer getCompanyRoleId(){return companyRoleId;}
}
