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
    public static String url = "jdbc:mysql://the-backroom-gabaslander-a0df.f.aivencloud.com:16090/defaultdb?ssl-mode=REQUIRED";
    Properties prop;

    public DatabaseManager(){
        //intialize the connection to a default user account on the database
        prop = new Properties();

        try{
            prop.load(getClass().getResourceAsStream("/edu/tangingina/thebackroom/config.properties"));
            username = prop.getProperty("default_user_username");
            password = prop.getProperty("default_user_password");
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public void getConnection() throws Exception{
        try{
            conn = DriverManager.getConnection(url, username, password);
            //System.out.println("Hi, the bluetooth device has been connected as scucesfully...");
        } catch (Exception e) {
            throw new Exception("Failed connection. Please connect to the internet.");
        }
    }

    public void updateConnection(){
        if(TheBackroom.currUser.getRole().equals("MODERATOR")){
            username = prop.getProperty("mod_username");
            password = prop.getProperty("mod_password");
            System.out.println("User is currently moderator logging in as moderator mysql account");
        }
    }

    public void resetConnection(){
        if(TheBackroom.currUser.getRole().equals("MODERATOR")){
            username = prop.getProperty("default_user_username");
            password = prop.getProperty("default_user_password");
            System.out.println("Changing the mysql account connection to app_user");
        }
    }


    //Put this in the cmd, to do our usual stuff sa cmd:
    //mysql --user ***REMOVED*** --password=***REMOVED*** --host the-backroom-gabaslander-a0df.f.aivencloud.com --port 16090 defaultdb
    //use defaultdb


}
