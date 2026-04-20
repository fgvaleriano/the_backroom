package edu.tangingina.thebackroom.dao;

import edu.tangingina.thebackroom.model.Category;
import edu.tangingina.thebackroom.model.Person;
import java.util.HashMap;

public interface PersonDao {
    Integer addPerson(Person person);
    HashMap<String, Person> getAllPersons();
    void getMediaPersonnel();
}
