package edu.tangingina.thebackroom.util;

import javafx.geometry.*;
import javafx.scene.Node;
import javafx.scene.layout.*;

public abstract class BaseView {
    /*
        base structure for all pages in dashboard
     */

    protected VBox root;

    public BaseView() {
        root = new VBox(20);
        root.setPadding(new Insets(40));
        setupStyles();
        //buildLayout();
    }

    protected abstract void buildLayout();

    private void setupStyles() {

    }

    public Node getView() {
        return root;
    }
}
