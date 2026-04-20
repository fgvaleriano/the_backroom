package edu.tangingina.thebackroom.dao.impl;

import edu.tangingina.thebackroom.dao.WebsiteDao;
import edu.tangingina.thebackroom.model.Category;
import edu.tangingina.thebackroom.model.Website;
import edu.tangingina.thebackroom.util.DatabaseManager;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;

public class WebsiteDaoImpl implements WebsiteDao {
    @Override
    public Integer addWesbite(Website website) {
        String query = "Insert into website(name) values(?)";
        int id = 0;

        try{
            PreparedStatement stm = DatabaseManager.conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            stm.setString(1, website.getWebisteName());
            stm.executeUpdate();
            ResultSet rs = stm.getGeneratedKeys();

            if(rs.next()){
                id = rs.getInt(1);
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }

        return id;
    }

    @Override
    public HashMap<String, Website> getAllWebsite() {
        String query = "Select * from website";
        HashMap<String, Website> websiteList = new HashMap<>();

        try{
            PreparedStatement stm = DatabaseManager.conn.prepareStatement(query);
            ResultSet rs = stm.executeQuery();

            while(rs.next()){
                Website website = new Website(rs.getInt("website_id"), rs.getString("name"), null);
                websiteList.put(rs.getString("name"), website);

            }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return websiteList;
    }

    @Override
    public void getWebsiteCategory() {

    }
}
