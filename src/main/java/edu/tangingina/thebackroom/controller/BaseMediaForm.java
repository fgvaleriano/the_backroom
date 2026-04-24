package edu.tangingina.thebackroom.controller;

import edu.tangingina.thebackroom.util.FontLoader;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.geometry.*;

public abstract class BaseMediaForm {
    /*
        Builds the shared UI for media types such as:
            - title/name
            - synopsis
            - release year/date
            - website
            - widget

        Also handles shared input validation
     */
    protected VBox view = new VBox(10);
    protected VBox formColumn = new VBox(40);

    public VBox getView() {
        return view;
    }

    public BaseMediaForm() {
        view.setAlignment(Pos.TOP_LEFT);
        view.setFillWidth(false);
    }

    public VBox formColumn () {
        formColumn.setAlignment(Pos.TOP_LEFT);
        return formColumn;
    }
}