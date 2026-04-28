package edu.tangingina.thebackroom.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.Properties;
import java.util.Scanner;
import java.util.Stack;

public class ConfigManager {

    public boolean checkConfig(){
        return new File("config.properties").exists();
    }

    public void initialize(){
        Scanner scan = new Scanner (System.in);
        System.out.println("========================================");
        System.out.println("      The Backroom: Initilaization      ");
        System.out.println("========================================");

        System.out.println("Please enter a MySQL account with CREATE privileges (e.g., 'root')");

        System.out.print("Username: ");
        String user = scan.nextLine();

        System.out.print("Password: ");
        String pass = scan.nextLine();

        String url = "jdbc:mysql://localhost/";

        try{
            Connection conn = DriverManager.getConnection(url, user, pass);

            System.out.println("--- SETTING UP APP ACCOUNTS ---");
            System.out.print("Enter password for Moderator (Manager): ");
            String modPass = scan.nextLine();

            System.out.print("Enter password for Normal User: ");
            String userPass = scan.nextLine();

            try{
                String query1 = "CREATE USER 'app_moderator'@'localhost' IDENTIFIED BY ?";
                String query2 = "CREATE USER 'app_user'@'localhost' IDENTIFIED BY ?";

                PreparedStatement stm;

                stm = conn.prepareStatement(query1);
                stm.setString(1, modPass);
                stm.executeUpdate();

                stm = conn.prepareStatement(query2);
                stm.setString(1, userPass);
                stm.executeUpdate();

                importSql(user, pass);
                createAppConfig(modPass, userPass);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    private void importSql(String root, String rootPass){
        File file = new File("thebackroom_db.sql");

        if(!file.exists()){
            System.err.println("ERROR: The thebackroom_db.sql can not be found.");
            return;
        }

        String command = String.format("mysql -u %s -p%s < \"%s\"",
                root, rootPass, file.getAbsolutePath());

        try {
            System.out.println("Importing data structures... Please wait.");

            ProcessBuilder pb = new ProcessBuilder("cmd.exe", "/c", command);
            pb.redirectErrorStream(true);

            Process process = pb.start();
            int exitCode = process.waitFor();

            if (exitCode == 0) {
                System.out.println("Database import successful!");
            } else {
                System.err.println("Importing database failed.");
            }

        } catch (Exception e) {
            System.err.println("Critical error during SQL execution: " + e.getMessage());
        }
    }

    private void createAppConfig(String modPass, String userPass) {
        String path = "src/main/resources/edu/tangingina/thebackroom/config.properties";
        File configFile = new File(path);

        Properties props = new Properties();
        props.setProperty("local_default_user_username", "app_user");
        props.setProperty("local_default_user_password", userPass);
        props.setProperty("local_mod_username", "app_moderator");
        props.setProperty("local_mod_password", modPass);

        try {
            FileOutputStream out = new FileOutputStream(configFile);
        } catch (IOException e) {
            System.err.println("Could not write to config.properties.");
        }
    }


}
