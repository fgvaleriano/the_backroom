package edu.tangingina.thebackroom;

import edu.tangingina.thebackroom.util.ConfigManager;
import javafx.application.Application;

public class Launcher {
    public static void main(String [] args){
        ConfigManager check = new ConfigManager();

        //We check first if the app configuration exists, if not this means we need to intiialize everything first;
        if(check.checkConfig()){
            Application.launch(TheBackroom.class, args);
        }else{
            check.initialize();
            Application.launch(TheBackroom.class, args);
        }
    }
}

