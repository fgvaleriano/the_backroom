package edu.tangingina.thebackroom.controller;

import javafx.scene.control.ComboBox;
import javafx.scene.control.Control;
import javafx.scene.control.TextInputControl;
import javafx.scene.layout.HBox;

public class FormFieldGroup {
    /*
        Main use is for input field validation

     */

    private HBox view;
    private Control inputs;

    public FormFieldGroup(HBox view, Control inputs) {
        this.view = view;
        this.inputs = inputs;
    }

    public HBox getView() {
        return view;
    }

    public Control getInputs() {
        return inputs;
    }

    public String getUserInput(){
        if (inputs instanceof TextInputControl textInput) {
            String value = textInput.getText();
            if(value != null){
                return value.trim();
            }
        }

        if (inputs instanceof ComboBox<?> combo) {
            Object value = combo.getValue();
            if(value != null){
                return value.toString().trim();
            }
        }
        return "";
    }

    public boolean isEmpty() {
        if (inputs instanceof TextInputControl textInput) {
            return textInput.getText().trim().isEmpty() || textInput.getText() == null;
        }

        if (inputs instanceof ComboBox<?> combo) {
            return combo.getValue() == null;
        }

        return false;
    }

    public void showError() {
        inputs.getStyleClass().add("input-field-error");
    }

    public void clearError() {
        inputs.getStyleClass().remove("input-field-error");
    }
}
