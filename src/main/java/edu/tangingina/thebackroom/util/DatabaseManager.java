package edu.tangingina.thebackroom.util;

import com.mysql.cj.protocol.Resultset;
import edu.tangingina.thebackroom.TheBackroom;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class DatabaseManager {
    public static Connection conn;
    public static String username = "***REMOVED***";
    public static String password = "***REMOVED***";
    public static String url = "jdbc:mysql://the-backroom-gabaslander-a0df.f.aivencloud.com:16090/defaultdb?ssl-mode=REQUIRED";

    public void getConnection() throws Exception{
        try{
            conn = DriverManager.getConnection(url, username, password);
            //System.out.println("Hi, the bluetooth device has been connected as scucesfully...");
        } catch (Exception e) {
            throw new Exception("Failed connection. Please connect to the internet.");
        }
    }

    public String getUsername() {
        String query = "select current_user();";
        String username = "";

        try{
            PreparedStatement stm = TheBackroom.conn.prepareStatement(query);
            ResultSet rs = stm.executeQuery();
            if(rs.next()){
                username = rs.getString(1);
            }

        }catch (Exception e){

        }
        return username;
    }



    //Put this in the cmd, to do our usual stuff sa cmd:
    //mysql --user ***REMOVED*** --password=***REMOVED*** --host the-backroom-gabaslander-a0df.f.aivencloud.com --port 16090 defaultdb
    //use defaultdb


}
