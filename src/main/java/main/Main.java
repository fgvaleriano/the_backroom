package main;

import controller.LoginController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;

import javafx.application.*;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.stage.*;


public class Main extends Application {
    Scene scene;
    StackPane root;

    public static void main(String[] args) {

        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {

        //showSignUp(primaryStage);
        //showAddArchive(primaryStage);
        showLogin(primaryStage);
    }

    public void showSignUp(Stage stage){
        try{
            //Note the fxml file is already the ui class file...so no need to create another ui class file

            FXMLLoader signUp = new FXMLLoader(getClass().getResource("/ui/SignUp.fxml"));
            //no need to set up the controller here since we already did that na sa scene builder...

            Parent root = signUp.load();
            Scene scene = new Scene(root);

            stage.setScene(scene);
            stage.show();
        }catch (Exception e)  {
            throw new RuntimeException(e);
        }

    }

    public void showAddArchive(Stage stage){
        try{
            //Note the fxml file is already the ui class file...so no need to create another ui class file

            FXMLLoader addArchive = new FXMLLoader(getClass().getResource("/ui/AddArchive.fxml"));
            //no need to set up the controller here since we already did that na sa scene builder...

            Parent root = addArchive.load();
            Scene scene = new Scene(root);

            stage.setScene(scene);
            stage.show();
        }catch (Exception e)  {
            throw new RuntimeException(e);
        }

    }

    public void showLogin(Stage stage){
        try {
            LoginController controller = new LoginController();
            root = controller.getLayout(stage);
            scene = new Scene(root);

            //for styling
            scene.getStylesheets().add(getClass().getResource("/ui/the_backroom_style.css").toExternalForm());

            stage.setScene(scene);
            stage.setTitle("The Backroom - Login");
            stage.setResizable(false);
            stage.setMaximized(true);
            stage.show();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

