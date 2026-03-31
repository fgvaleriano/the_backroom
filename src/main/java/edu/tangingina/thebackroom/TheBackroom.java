package edu.tangingina.thebackroom;

import edu.tangingina.thebackroom.util.DatabaseManager;
import javafx.application.Application;
import javafx.stage.Stage;

public class TheBackroom extends Application {
    DatabaseManager dm = new DatabaseManager();

    @Override
    public void start(Stage primaryStage) {
        System.out.println("Hello World!!!");

        try{
            dm.getConnection();
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

}
