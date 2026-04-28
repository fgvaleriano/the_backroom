package edu.tangingina.thebackroom.controller.dashboard.Media_Details;

import java.awt.*;
import java.util.*;
import java.util.List;

public class ExternalLink {

    /*
        Handles opening access links in details page
     */

    public void openLinks(List<String> links) {
        if (links == null || links.isEmpty()) {
            System.out.println("No links available for this media");
            return;
        }

        for (String link : links) {
            open(link);
        }
    }

    private void open(String link) {
        try {
            if (!Desktop.isDesktopSupported()) {
                System.out.println("Desktop is not supported");
                return;
            }
        } catch (Exception e) {
            System.out.println("Error opening link " + link);
            System.out.println("Reason: " + e.getMessage());
        }
    }
}
