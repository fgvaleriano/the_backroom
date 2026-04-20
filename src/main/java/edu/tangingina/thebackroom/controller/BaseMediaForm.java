package edu.tangingina.thebackroom.controller;

import edu.tangingina.thebackroom.util.FontLoader;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.geometry.*;

public abstract class BaseMediaForm {
    protected VBox view = new VBox(15);

    public VBox getView() {
        return view;
    }
}



//public class BaseMediaForm {
    /*
        Builds the shared UI for media types such as:
            - title/name
            - synopsis
            - release year/date
            - website
            - widget

        Also handles shared input validation
     */
/*
    private static DatePicker releaseYearPicker;
    private static VBox view, titleField, synopsisField, widgetField, accessField;

    public VBox getView() {
        return view;
    }

    public BaseMediaForm(String selectedType) {
        view = new VBox(18);
        view.setAlignment(Pos.TOP_LEFT);
        view.setPadding(new Insets(25, 30, 25, 30));

        titleField = FormFieldFactory.createTextField("Title");
        synopsisField = FormFieldFactory.createTextField("Synopsis");
        //releaseYearPicker = FormFieldFactory.createDatePicker("Release");
        accessField = FormFieldFactory.createTextField("Access Link");
        widgetField = FormFieldFactory.createTextField("Widget");

        view.getChildren().addAll(titleField, synopsisField, accessField, widgetField);

    }

}
*/