package edu.tangingina.thebackroom.util;

import java.sql.Connection;
import java.sql.DriverManager;

public class DatabaseManager {
    public static String username = "***REMOVED***";
    public static String password = "***REMOVED***";
    public static String url = "jdbc:mysql://the-backroom-gabaslander-a0df.f.aivencloud.com:16090/defaultdb?ssl-mode=REQUIRED";

    public void getConnection() throws Exception{
        try{
            Connection conn = DriverManager.getConnection(url, username, password);
            System.out.println("Hi, the bluetooth device has been connected as scucesfully...");
        } catch (Exception e) {
            throw new Exception("Failed connection. Please connect to the internet.");
        }
    }

    //Put this in the cmd, to do our usual stuff sa cmd:
    //mysql --user ***REMOVED*** --password=***REMOVED*** --host the-backroom-gabaslander-a0df.f.aivencloud.com --port 16090 defaultdb
    //use defaultdb


}
