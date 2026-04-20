package edu.tangingina.thebackroom.dao.impl;

import edu.tangingina.thebackroom.dao.GameModeDao;
import edu.tangingina.thebackroom.model.GameMode;
import edu.tangingina.thebackroom.util.DatabaseManager;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;

public class GameModeDaoImpl implements GameModeDao {
    @Override
    public Integer addGameMode(GameMode gameMode) {
        String query = "INSERT into mode(name) values(?)";
        int id = 0;

        try{
            PreparedStatement stm = DatabaseManager.conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            stm.setString(1, gameMode.getGameModeName());
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
    public HashMap<String, GameMode> getAllGameMode() {
        String query = "SELECT * from mode";
        HashMap<String, GameMode> gameModeList = new HashMap<>();

        try{
            PreparedStatement stm = DatabaseManager.conn.prepareStatement(query);
            ResultSet rs = stm.executeQuery();
            while(rs.next()){
                GameMode gameMode = new GameMode(rs.getInt("mode_id"), rs.getString("name"));
                gameModeList.put(rs.getString("name"), gameMode);
            }
        }catch (Exception e){

        }

        return gameModeList;
    }

    @Override
    public void getMediaGameMode() {

    }
}
