package edu.tangingina.thebackroom.controller.dashboard;

import javafx.geometry.*;
import javafx.geometry.Pos;
import javafx.scene.layout.*;
import javafx.scene.control.*;

public class DashboardShell extends BorderPane {
    /*
        Shell for the dashboard, everything is called here
     */

    private VBox contentArea;
    private NavbarComponent navBar;
    private ScrollPane scrollPane;

    public DashboardShell() {
        //this.setTop(new NavbarComponent());             //navigation bar will always be on top

        //navigation bar thing
        navBar = new NavbarComponent();
        StackPane navWrapper = new StackPane(navBar);
        navWrapper.setPadding(new Insets(0, 0, 10, 0));
        this.setTop(navWrapper);

        //main content holder but not scrollable
        contentArea = new VBox();
        contentArea.setAlignment(Pos.TOP_CENTER);
        contentArea.getStyleClass().add("main-content-bkg");

        //contain area is contained in the scrollpane
        scrollPane = new ScrollPane(contentArea);
        scrollPane.setFitToWidth(true);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrollPane.getStyleClass().add("dashboard-scroll");

        this.setCenter(scrollPane);

        setView(new DashboardHomeView());
    }

    //for the modular switcher
    public void setView(BaseView view) {
        contentArea.getChildren().clear();
        contentArea.getChildren().add(view.getView());
    }
}
