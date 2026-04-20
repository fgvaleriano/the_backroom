package edu.tangingina.thebackroom.dao.impl;

import edu.tangingina.thebackroom.dao.PersonDao;
import edu.tangingina.thebackroom.model.Category;
import edu.tangingina.thebackroom.model.Person;
import edu.tangingina.thebackroom.util.DatabaseManager;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;

public class PersonDaoImpl implements PersonDao {
    @Override
    public Integer addPerson(Person person) {
        String query = "Insert into person(name) values(?)";
        int id = 0;

        try{
            PreparedStatement stm = DatabaseManager.conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            stm.setString(1, person.getPersonName());
            stm.executeUpdate();

            ResultSet rs = stm.getGeneratedKeys();

            if(rs.next()){
                id = rs.getInt(1);
            }
        }catch(Exception e){
            System.out.println(e.getMessage());
        }

        return id;
    }

    @Override
    public HashMap<String, Person> getAllPersons() {
        String query = "Select * from person";
        HashMap<String, Person> personList = new HashMap<>();

        try{
            PreparedStatement stm = DatabaseManager.conn.prepareStatement(query);
            ResultSet rs = stm.executeQuery();

            while(rs.next()){
                Person person = new Person(rs.getInt("person_id"), rs.getString("name"), null, 0);
                personList.put(rs.getString("name"), person);
            }
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
        return personList;
    }

    @Override
    public void getMediaPersonnel() {

    }
}
