package edu.tangingina.thebackroom.controller;

import edu.tangingina.thebackroom.util.FontLoader;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;

public class FormFieldFactory {
    /*
        Creates the basic structure of textfields and textareas so that we won't have to
        keep on coding the same thing for input fields
     */

    private static final double labelSectionWidth = 135;

    public static Label createLabel(String labelText) {
        Label label = new Label(labelText);
        label.setFont(FontLoader.bold(18));
        label.getStyleClass().add("input-label");

        label.setMinWidth(labelSectionWidth);
        label.setPrefWidth(labelSectionWidth);
        label.setMaxWidth(labelSectionWidth);

        label.setAlignment(Pos.CENTER_LEFT);
        label.setWrapText(true);
        label.setPrefHeight(60);
        return label;
    }

    public static FormFieldGroup createTextField (String labelText, Integer size) {
        Label inputLabel = createLabel(labelText);
        TextField inputField = new TextField();

        inputField.setFont(FontLoader.regular(18));
        inputField.getStyleClass().add("input-field");
        inputField.setPrefWidth(size);
        inputField.setMinWidth(Control.USE_PREF_SIZE);
        inputField.setMaxWidth(Control.USE_PREF_SIZE);

        HBox holder = new HBox(20, inputLabel, inputField);
        holder.setAlignment(Pos.CENTER_LEFT);

        return new FormFieldGroup(holder, inputField);
    }

    public static FormFieldGroup createYearPicker(String labelText, Integer size) {
        Label inputLabel = createLabel(labelText);
        ComboBox<Integer> yearPicker = new ComboBox<>();
        inputLabel.setFont(FontLoader.regular(18));

        inputLabel.getStyleClass().add("input-label");

        for (int i = 1800; i <= 2100; i++) {
            yearPicker.getItems().add(i);
        }

        yearPicker.setPrefWidth(size);
        yearPicker.setMinWidth(Control.USE_PREF_SIZE);
        yearPicker.setMaxWidth(Control.USE_PREF_SIZE);

        HBox holder = new HBox(20, inputLabel, yearPicker);
        holder.setAlignment(Pos.TOP_LEFT);
        yearPicker.setTranslateY(5);
        return new FormFieldGroup(holder, yearPicker);
    }


    public static FormFieldGroup createStatusPicker(String labelText, Integer size){
        Label inputLabel = createLabel(labelText);
        ComboBox<String> status = new ComboBox<>();
        inputLabel.setFont(FontLoader.regular(18));

        inputLabel.getStyleClass().add("input-label");
        status.getItems().addAll("Ongoing", "Completed", "Discontinued");

        status.setPrefWidth(size);
        status.setMinWidth(Control.USE_PREF_SIZE);
        status.setMaxWidth(Control.USE_PREF_SIZE);

        HBox holder = new HBox(20, inputLabel, status);
        holder.setAlignment(Pos.TOP_LEFT);
        status.setTranslateY(5);
        return new FormFieldGroup(holder, status);
    }
    public static FormFieldGroup createTextArea (String labelText, Integer size) {
        Label inputLabel = createLabel(labelText);
        TextArea inputArea = new TextArea();
        inputArea.setFont(FontLoader.regular(18));

        inputArea.setPrefWidth(size);
        inputArea.setWrapText(true);
        inputArea.getStyleClass().add("input-field");

        HBox holder = new HBox(20, inputLabel, inputArea);
        holder.setAlignment(Pos.TOP_LEFT);

        return new FormFieldGroup(holder, inputArea);
    }

    public static MultiValueField createMultiValueField(String labelText, Integer fieldWidth) {
        return new MultiValueField(labelText, fieldWidth);
    }

    public static AccessLinkField createAccessLinkField(String labelText) {
        return new AccessLinkField(labelText);
    }

    public static ImageFileField createImageFileField(String labelText, int fieldWidth) {
        return new ImageFileField(labelText, fieldWidth);
    }

    public static Double getLabelSectionWidth() {
        return labelSectionWidth;
    }

}

