package edu.tangingina.thebackroom.dao.impl;

import edu.tangingina.thebackroom.dao.CategoryDao;
import edu.tangingina.thebackroom.model.Category;
import edu.tangingina.thebackroom.util.DatabaseManager;

import javax.xml.transform.Result;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

public class CategoryDaoImpl implements CategoryDao {
    @Override
    public Integer addCategory(Category category) {
        String query = "INSERT into category(name) values(?)";
        int id = 0;

        try{
            PreparedStatement stm = DatabaseManager.conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            stm.setString(1, category.getCategoryName());
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
    public HashMap<String, Category> getAllCategory() {
        String query = "SELECT * from category";
        HashMap<String,Category> categoryList = new HashMap<>();

        try{
            PreparedStatement stm = DatabaseManager.conn.prepareStatement(query);
            ResultSet rs = stm.executeQuery();
            while(rs.next()){
                Category category = new Category(rs.getInt("category_id"), rs.getString("name"));
                categoryList.put(rs.getString("name"), category);
            }
        }catch (Exception e){

        }

        return categoryList;

    }

    @Override
    public void getMediaCategory() {

    }
}
