package edu.tangingina.thebackroom.model;

public class Person {
    private int id;
    private String personName;
    private String personRole;
    private int personRoleId;

    public Person(int id, String name, String role, int roleId){
        this.id = id;
        this.personName = name;
        this.personRole = role;
        this.personRoleId = roleId;
    }

    public Integer getPersonID(){
        return id;
    }

    public String getPersonName(){
        return personName;
    }

    public String getPersonRole(){
        return personRole;
    }

    public Integer getPersonRoleId() {
        return personRoleId;
    }

    public void setPersonID(int id){this.id = id;}

    public void setPersonRoleID(int id){this.personRoleId = id;}
}
