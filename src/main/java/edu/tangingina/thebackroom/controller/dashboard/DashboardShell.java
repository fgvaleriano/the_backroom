package edu.tangingina.thebackroom.controller.dashboard;

import javafx.geometry.Pos;
import javafx.scene.layout.*;
import javafx.scene.control.*;

public class DashboardShell extends BorderPane {
    /*
        Shell for the dashboard, everything is called here
     */

    private VBox contentArea;

    public DashboardShell() {
        this.setTop(new NavbarComponent());             //navigation bar will always be on top

        contentArea = new VBox();
        contentArea.setAlignment(Pos.TOP_CENTER);
        contentArea.getStyleClass().add("main-content-bkg");

        this.setCenter(contentArea);
    }

    //for the modular switcher
    /*public void setView(BaseView view) {
        contentArea.getChildren().clear();
        contentArea.getChildren().add(view.getView());
    }*/
}
