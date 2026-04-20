package edu.tangingina.thebackroom.dao.impl;

import edu.tangingina.thebackroom.dao.CompanyDao;
import edu.tangingina.thebackroom.model.Company;
import edu.tangingina.thebackroom.util.DatabaseManager;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;

public class CompanyDaoImpl implements CompanyDao {
    @Override
    public Integer addCompany(Company company) {
        String query = "Insert into company(name) values(?)";
        int id = 0;

        try{
            PreparedStatement stm = DatabaseManager.conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            stm.setString(1, company.getCompanyName());
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
    public HashMap<String, Company> getAllCompany() {
        String query = "Select * from company";
        HashMap<String, Company> companyList = new HashMap<>();

        try{
            PreparedStatement stm = DatabaseManager.conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            ResultSet rs = stm.executeQuery();

            while(rs.next()){
                Company company = new Company(rs.getInt("company_id"), rs.getString("name"), null, 0);
                companyList.put(rs.getString("name"), company);
            }
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
        return companyList;
    }

    @Override
    public void getMediaCompany() {

    }
}
