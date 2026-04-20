package edu.tangingina.thebackroom.dao.impl;

import edu.tangingina.thebackroom.dao.PlatformDao;
import edu.tangingina.thebackroom.model.Category;
import edu.tangingina.thebackroom.model.Platform;
import edu.tangingina.thebackroom.util.DatabaseManager;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;

public class PlatformDaoImpl implements PlatformDao{
    @Override
    public Integer addPlatform(Platform platform) {
        String query = "INSERT into platform(name) values(?)";
        int id = 0;

        try{
            PreparedStatement stm = DatabaseManager.conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            stm.setString(1, platform.getPlatformName());
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
    public HashMap<String, Platform> getAllPlatform() {
        String query = "SELECT * from platform";
        HashMap<String, Platform> platformList = new HashMap<>();

        try{
            PreparedStatement stm = DatabaseManager.conn.prepareStatement(query);
            ResultSet rs = stm.executeQuery();
            while(rs.next()){
                Platform platform = new Platform(rs.getInt("platform_id"), rs.getString("name"));
                platformList.put(rs.getString("name"), platform);
            }
        }catch (Exception e){

        }

        return platformList;
    }

    @Override
    public void getMediaPlatform() {

    }
}
