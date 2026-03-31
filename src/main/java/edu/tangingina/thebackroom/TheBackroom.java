package edu.tangingina.thebackroom;

import edu.tangingina.thebackroom.controller.HomePageController;
import edu.tangingina.thebackroom.controller.LoginController;
import edu.tangingina.thebackroom.util.DatabaseManager;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class TheBackroom extends Application {
    Scene scene;
    StackPane root, homePage;
    DatabaseManager dm = new DatabaseManager();

    @Override
    public void start(Stage primaryStage) {
        System.out.println("Hello World!!!");

        //showLogin(primaryStage);
        //showSignUp(primaryStage);
        //showHome(primaryStage);
        //showAddArchive(primaryStage);
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
            scene.getStylesheets().add(getClass().getResource("/edu/tangingina/thebackroom/the_backroom_style.css").toExternalForm());

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
            scene.getStylesheets().add(getClass().getResource("/edu/tangingina/thebackroom/the_backroom_style.css").toExternalForm());

            stage.setScene(scene);
            stage.setTitle("The Backroom");

            stage.setMaximized(true);
            stage.show();
            stage.setResizable(false);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
