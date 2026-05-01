package edu.tangingina.thebackroom.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.Properties;
import java.util.Scanner;

public class ConfigManager {

    public boolean checkConfig() {
        return new File("src/main/resources/edu/tangingina/thebackroom/config.properties").exists();
    }

    // Updated to take parameters instead of using Scanner
    public boolean initializeDatabase(String rootUser, String rootPass, String modPass, String userPass) {
        String url = "jdbc:mysql://localhost/";

        try {
            Connection conn = DriverManager.getConnection(url, rootUser, rootPass);

            String query1 = "DROP USER IF EXISTS 'app_moderator'@'localhost'";
            String query2 = "DROP USER IF EXISTS 'app_user'@'localhost'";

            PreparedStatement stm;

            //Drop the users first if they exist
            stm = conn.prepareStatement(query1);
            stm.executeUpdate();

            stm = conn.prepareStatement(query2);
            stm.executeUpdate();

            query1 = "CREATE USER 'app_moderator'@'localhost' IDENTIFIED BY ?";
            query2 = "CREATE USER 'app_user'@'localhost' IDENTIFIED BY ?";

            stm = conn.prepareStatement(query1);
            stm.setString(1, modPass);
            stm.executeUpdate();

            stm = conn.prepareStatement(query2);
            stm.setString(1, userPass);
            stm.executeUpdate();

            boolean importSuccess = importSql(rootUser, rootPass);
            if (importSuccess) {
                createAppConfig(modPass, userPass);
                return true;
            }
            return false;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private boolean importSql(String root, String rootPass) {
        File file = new File("thebackroom_db.sql");

        if (!file.exists()) {
            System.err.println("ERROR: The thebackroom_db.sql can not be found.");
            return false;
        }

        String command = String.format("mysql -u %s -p%s < \"%s\"", root, rootPass, file.getAbsolutePath());

        try {
            ProcessBuilder pb = new ProcessBuilder("cmd.exe", "/c", command);
            pb.redirectErrorStream(true);
            Process process = pb.start();
            int exitCode = process.waitFor();
            return exitCode == 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
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

        try (FileOutputStream out = new FileOutputStream(configFile)) {
            props.store(out, "The Backroom App Configuration");
        } catch (IOException e) {
            System.err.println("Could not write to config.properties.");
        }
    }

    public boolean testDbConnection(String rootUser, String rootPass) {
        String url = "jdbc:mysql://localhost/";
        try (Connection conn = DriverManager.getConnection(url, rootUser, rootPass)) {
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}