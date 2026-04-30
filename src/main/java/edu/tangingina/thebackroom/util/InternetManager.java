package edu.tangingina.thebackroom.util;

import edu.tangingina.thebackroom.model.Media;
import edu.tangingina.thebackroom.model.Website;

import java.awt.*;
import java.net.URI;

public class InternetManager {

    public void openWebsite(Media media){
        try {
            if(Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)){
                for(Website w : media.getOnlineAccess()){
                    Desktop.getDesktop().browse(new URI(w.getWebsiteURL()));
                }
            }else{
                System.out.println("Desktop is not supported");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
