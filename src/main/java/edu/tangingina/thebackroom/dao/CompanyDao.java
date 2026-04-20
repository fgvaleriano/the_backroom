package edu.tangingina.thebackroom.dao;

import edu.tangingina.thebackroom.model.Company;
import edu.tangingina.thebackroom.model.Person;

import java.util.HashMap;

public interface CompanyDao {
    Integer addCompany(Company company);
    HashMap<String, Company> getAllCompany();
    void getMediaCompany();
}
