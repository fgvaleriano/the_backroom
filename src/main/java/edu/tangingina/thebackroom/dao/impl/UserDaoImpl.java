package edu.tangingina.thebackroom.dao.impl;

import edu.tangingina.thebackroom.TheBackroom;
import edu.tangingina.thebackroom.dao.UserDao;
import edu.tangingina.thebackroom.model.Users;
import edu.tangingina.thebackroom.util.DatabaseManager;
import edu.tangingina.thebackroom.util.Utility;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDaoImpl implements UserDao {
    Utility util = new Utility();

    @Override
    public void login(String username, String pass) throws Exception {
        String query = "SELECT * from users where username = ?";

        try{
            PreparedStatement stm = DatabaseManager.conn.prepareStatement(query);
            stm.setString(1, username);
            ResultSet rs = stm.executeQuery();

            if(rs.next()){
                String hashedFromDb = rs.getString("password");
                if(util.verifyPass(hashedFromDb, pass)){
                    TheBackroom.currUser = new Users(rs.getString("username"), rs.getString("role"));
                }else{
                    throw new Exception("Invalid username or password");
                }
            }else{
                throw new Exception("Invalid username or password");
            }
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    @Override
    public void signUp(String username, String pass) throws Exception {
        String query = "INSERT INTO users(username, password) VALUES(?, ?)";

        try{
            PreparedStatement stm = DatabaseManager.conn.prepareStatement(query);
            stm.setString(1, username);
            stm.setString(2, pass);

            stm.executeUpdate();
            TheBackroom.currUser = new Users(username, "MEMBER");

        }catch (SQLException e){
            if(e.getErrorCode() == 1062){
                throw new Exception("Username already exists.");
            }else{
                throw new Exception("Something went wrong. Please try again");
            }

        }
    }

    @Override
    public void getUser(String username) throws Exception {
        String query = "SELECT * from users where username = ?";

        try{
            PreparedStatement stm = DatabaseManager.conn.prepareStatement(query);
            stm.setString(1, username);
            ResultSet rs = stm.executeQuery();

            if(rs.next()){
                TheBackroom.currUser = new Users(rs.getString("username"), rs.getString("role"));
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void guestLogin() throws Exception {
        String query = "SELECT * from users where role = 'GUEST' limit 1;";

        try{
            PreparedStatement stm = DatabaseManager.conn.prepareStatement(query);
            ResultSet rs = stm.executeQuery();
            if(rs.next()){
                TheBackroom.currUser = new Users(rs.getString("username"), rs.getString("role"));
            }
        }catch (Exception e){
            throw new Exception("Invalid username or password");
        }
    }


}
