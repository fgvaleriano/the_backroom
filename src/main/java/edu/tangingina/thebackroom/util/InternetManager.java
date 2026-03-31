package edu.tangingina.thebackroom.util;

import java.awt.*;
import java.net.URI;

public class InternetManager {

    public void openWebsite(String url){
        try {
            if(Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)){
                Desktop.getDesktop().browse(new URI(url));
            }else{
                System.out.println("Desktop is not supported");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
