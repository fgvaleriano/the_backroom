package edu.tangingina.thebackroom;

//import edu.tangingina.thebackroom.controller.AddArchive_v2;
import edu.tangingina.thebackroom.controller.AddArchive_v2;
import edu.tangingina.thebackroom.controller.HomePageController;
import edu.tangingina.thebackroom.controller.LoginController;
import edu.tangingina.thebackroom.dao.impl.UserDaoImpl;
import edu.tangingina.thebackroom.model.Users;
import edu.tangingina.thebackroom.util.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.sql.Connection;
import java.util.Scanner;

/*
===================================================
  (ノಠ益ಠ)ノ  The Fucking To do LIST!!!!!!!
==================================================
    [] UI ARCHITECTURE & FIXES
       - Fix FXML Location: Use leading slash "/edu/tangingina/..."
       - Controller Pathing: Ensure fx:controller includes full package name.
       - Resource Migration: Move all CSS and static icons to /resources.

    [] UI DESIGN & WORKFLOW
       - Scene Switching: Methods in TheBackroom.java to toggle between views.
       - Button Logic: Add onAction handlers to switch VBox visibility (Step 1, 2, 3).
       - Controller Linking: Match all @FXML variables with FXML fx:id.

    [] DATABASE ENGINE (AIVEN CLOUD)
       - Connection: Finalize DatabaseManager.java with SSL and Aiven URI.
       - Schema Setup: Create tables for Users, Archives, and Favorites.
       - DAO & Impl: Create interfaces and classes; Impl MUST throw Exceptions.

    [] HOME PAGE & SEARCH
       - Data Fetching: Query to get all media (Movies, Books, etc.) for the home view.
       - Search Filter: Implement "WHERE name LIKE %?%" logic in the DAO.
       - Category Filter: Filter results by Media Type or Genre.

    [] PERSONALIZATION & FAVORITES
       - Favorites Table: Schema (user_id, archive_id) to link items to users.
       - Recommendations: Query to suggest items based on the user's favorite genre.
       - Toggle Feature: Heart/Star button to add or remove items from Favorites.

    [] ERROR HANDLING & POLISH
       - UI Alerts: Use JavaFX Alerts to display errors thrown by the Impl.
       - Validation: Prevent saving if required fields (Name, Type) are empty.

    [] (ノಠ益ಠ)ノ MAKE EVRYTHING FUCKING WORK........... (ಥ﹏ಥ)(╥﹏╥)(╥﹏╥)(╥﹏╥)

 */
public class TheBackroom extends Application {
    public static Users currUser;
    public static Connection conn;
    public static String user;
    Scanner scan = new Scanner(System.in);

    Scene scene;
    StackPane root, homePage, addPage;

    DatabaseManager dm = new DatabaseManager();
    FileManager fm = new FileManager();
    InternetManager im = new InternetManager();
    Utility util = new Utility();

    @Override
    public void start(Stage primaryStage) {
        System.out.println("Hello World!!!");

        //showLogin(primaryStage);
        fontLoader();
        //showSignUp(primaryStage);
        //showHome(primaryStage);
        //showAddArchive(primaryStage);
        //fm.importCSV(primaryStage);
        //fm.importJSON(primaryStage);
        //fm.importImg(primaryStage);
        //im.openWebsite("https://www.youtube.com/watch?v=Yw7DQhx08ak&pp=ygUEYWhvZg%3D%3D");


        //Flow -> Open the app, and when they enter, put this error if not wifi....or down an database...
        /*while(true){
            try{
                dm.getConnection();
                System.out.println("Welcome to The Backroom!!!!");
                user = "Guest";
                break;
            }catch (Exception e){
                //put an error message here......
                System.out.println(e.getMessage());
            }
        }*/



        /*int choice = 0;
        UserDaoImpl userDao = new UserDaoImpl(); //this would be put in the login stuff, so yeah
        String username, pass;
        while(true){
            //if(user.equals("Guest")) System.out.println("Currently Log-In as " + dm.getUsername() +"\n");
            if(currUser != null && currUser.getRole().equals("MEMBER")) System.out.println("Currently Log-In as " + currUser.getUsername() + "\n");

            System.out.print("1. Login\n2. SignUp\n3.Exit\nChoice: ");
            choice = scan.nextInt();
            scan.nextLine();

            switch (choice){
                case 1:
                    System.out.print("Username: "); username = scan.nextLine();
                    System.out.print("Password: ") ; pass = scan.nextLine();
                    try{
                        userDao.login(username, pass);
                    }catch (Exception e){
                        System.out.println(e.getMessage());
                    }
                    break;

                case 2:
                    System.out.print("Username: "); username = scan.nextLine();
                    System.out.print("Password: ") ; pass = scan.nextLine();
                    try{
                        userDao.signUp(username, util.getHashPass(pass));
                    }catch (Exception e){
                        System.out.println(e.getMessage());
                    }
                    break;

            }

        }*/








    }

    public void showSignUp(Stage stage){
        try{
            //Note the fxml file is already the ui class file...so no need to create another ui class file

            FXMLLoader signUp = new FXMLLoader(getClass().getResource("/edu/tangingina/thebackroom/SignUp.fxml"));
            //no need to set up the controller here since we already did that na sa scene builder...

            Parent root = signUp.load();
            Scene scene = new Scene(root);

            stage.setScene(scene);

            //this would maximized everything including the windows tab (weird enough if resizable was put before show, it become full screen)
            stage.setMaximized(true);
            stage.show();
            stage.setResizable(false);
        }catch (Exception e)  {
            throw new RuntimeException(e);
        }

    }

    public void showAddArchive(Stage stage){
        try{
            //Note the fxml file is already the ui class file...so no need to create another ui class file

            FXMLLoader addArchive = new FXMLLoader(getClass().getResource("/edu/tangingina/thebackroom/AddArchive.fxml"));
            //no need to set up the controller here since we already did that na sa scene builder...

            Parent root = addArchive.load();
            Scene scene = new Scene(root);

            stage.setScene(scene);
            stage.show();
        }catch (Exception e)  {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

    }

    public void showLogin(Stage stage){
        try {
            LoginController controller = new LoginController();
            root = controller.getLayout(stage);
            scene = new Scene(root);

            //for styling
            scene.getStylesheets().add(getClass().getResource(
                    "/edu/tangingina/thebackroom/the_backroom_style.css").toExternalForm());

            stage.setScene(scene);
            stage.setTitle("The Backroom - Login");

            //.setResizable(true);
            //i think teh better if we do it like this:
            //stage.setMinWidth(1000);
            //stage.setMinHeight(600);

            stage.setMaximized(true);
            stage.show();
            stage.setResizable(false);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void showHome(Stage stage) {
        try {
            HomePageController home = new HomePageController();
            homePage = home.getLayout(stage);
            scene = new Scene(homePage);
            scene.getStylesheets().add(getClass().getResource(
                    "/edu/tangingina/thebackroom/the_backroom_style.css").toExternalForm());

            stage.setScene(scene);
            stage.setTitle("The Backroom");

            stage.setMaximized(true);
            stage.show();
            stage.setResizable(false);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void showAddArchive_v2(Stage stage) {
        try {
            AddArchive_v2 add = new AddArchive_v2();


        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void fontLoader() {
        FontLoader load = new  FontLoader();
        load.debug();
        return;
    }

}
