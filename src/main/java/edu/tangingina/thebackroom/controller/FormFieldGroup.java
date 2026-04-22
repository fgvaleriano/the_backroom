package edu.tangingina.thebackroom.controller;

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

    public boolean isEmpty() {
        if (inputs instanceof TextInputControl textInput) {
            return textInput.getText().trim().isEmpty() || textInput.getText() == null;
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
