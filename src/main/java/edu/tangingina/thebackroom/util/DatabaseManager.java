package edu.tangingina.thebackroom.util;

import com.mysql.cj.protocol.Resultset;
import edu.tangingina.thebackroom.TheBackroom;

import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Properties;

public class DatabaseManager {

    /*
    ==========================================
        Local Database - Dont use Aiven

        -For Media Type: Use ENUM
    ===========================================
     */
    public static Connection conn;
    public static String username;
    public static String password;
    public static String url;
    Properties prop;

    public DatabaseManager(){
        //intialize the connection to a default user account on the database
        prop = new Properties();

        try{
            prop.load(getClass().getResourceAsStream("/edu/tangingina/thebackroom/config.properties"));

            if(TheBackroom.onlineDatabase){
                url = "jdbc:mysql://the-backroom-gabaslander-a0df.f.aivencloud.com:16090/defaultdb?ssl-mode=REQUIRED";
                username = prop.getProperty("default_user_username");
                password = prop.getProperty("default_user_password");
            }else{
                url = "jdbc:mysql://localhost/thebackroom_db";
                username = prop.getProperty("local_default_user_username");
                password = prop.getProperty("local_default_user_password");
                username = prop.getProperty("local_mod_username");
                password = prop.getProperty("local_mod_password");
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public void getConnection() throws Exception{
        try{
            conn = DriverManager.getConnection(url, username, password);
        } catch (Exception e) {
            throw new Exception("Failed connection. Please connect to the internet.");
        }
    }

    public void updateConnection(){
        if(TheBackroom.currUser.getRole().equals("MODERATOR")){
            if(TheBackroom.onlineDatabase){
                username = prop.getProperty("mod_username");
                password = prop.getProperty("mod_password");
            }else{
                username = prop.getProperty("local_mod_username");
                password = prop.getProperty("local_mod_password");
            }
            System.out.println("User is currently moderator logging in as moderator mysql account");
        }
    }

    public void resetConnection(){
        if(TheBackroom.currUser.getRole().equals("MODERATOR")){
            if(TheBackroom.onlineDatabase){
                username = prop.getProperty("default_user_username");
                password = prop.getProperty("default_user_password");
            }else{
                username = prop.getProperty("local_default_user_username");
                password = prop.getProperty("local_default_user_password");
            }
        }
    }

}
