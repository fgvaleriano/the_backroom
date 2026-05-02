package edu.tangingina.thebackroom.util;

import edu.tangingina.thebackroom.model.Media;
import edu.tangingina.thebackroom.model.Website;

import java.awt.*;
import java.net.URI;
import java.net.URL;

public class InternetManager {

    public void openWebsite(Media media){
        try {
            if(Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)){
                if(media.getOnlineAccess() != null || !media.getOnlineAccess().isEmpty()){
                    for(Website w : media.getOnlineAccess()){
                        String url = w.getWebsiteURL();

                        if(validateUrl(url)){
                            Desktop.getDesktop().browse(new URI(url));
                        }
                    }
                }
            }else{
                //throw here an error message:
                System.out.println("Desktop is not supported");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean validateUrl(String url){
        if(url.isEmpty() || url == null){
            return false;
        }

        try{
            URL u = new URL(url);
            u.toURI();
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
