package edu.tangingina.thebackroom;

import edu.tangingina.thebackroom.util.ConfigManager;
import edu.tangingina.thebackroom.util.ConfigSetupWindow;
import javafx.application.Application;

public class Launcher {
    public static void main(String [] args){
        ConfigManager check = new ConfigManager();

        //We check first if the app configuration exists, if not this means we need to intiialize everything first;
        if (!check.checkConfig()) {
            Application.launch(ConfigSetupWindow.class, args);
        } else {
            Application.launch(TheBackroom.class, args);
        }
    }
}

