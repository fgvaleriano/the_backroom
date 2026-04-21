package edu.tangingina.thebackroom.controller;

import edu.tangingina.thebackroom.util.FontLoader;
import javafx.scene.control.*;
import javafx.scene.layout.*;

public class FormFieldFactory {
    /*
        Creates the basic structure of textfields and textareas so that we won't have to
        keep on coding the same thing for input fields
     */

    public static VBox createTextField (String labelText, Integer size) {
        Label inputLabel = new Label(labelText);
        TextField inputField = new TextField();
        inputLabel.setFont(FontLoader.regular(15));
        inputField.setFont(FontLoader.regular(15));

        inputField.getStyleClass().add("input-field");
        inputLabel.getStyleClass().add("input-label");

        VBox holder = new VBox(6, inputLabel, inputField);
        holder.setMaxWidth(size);

        return holder;
    }

    public static VBox createYearPicker(String labelText, Integer size) {
        Label inputLabel = new Label(labelText);
        ComboBox<Integer> yearPicker = new ComboBox<>();
        inputLabel.setFont(FontLoader.regular(15));

        inputLabel.getStyleClass().add("input-label");

        for (int i = 1800; i <= 2100; i++) {
            yearPicker.getItems().add(i);
        }

        VBox holder = new VBox(6, inputLabel, yearPicker);
        yearPicker.setMaxWidth(size);

        return holder;
    }

    public static VBox createTextArea (String labelText, Integer size) {
        Label inputLabel = new Label(labelText);
        TextArea inputArea = new TextArea();
        inputLabel.setFont(FontLoader.regular(15));
        inputArea.setFont(FontLoader.regular(15));

        inputArea.setPrefRowCount(5);
        inputArea.setWrapText(true);

        inputArea.getStyleClass().add("input-field");
        inputLabel.getStyleClass().add("input-label");

        VBox holder = new VBox(6, inputLabel, inputArea);
        holder.setMaxWidth(size);

        return holder;
    }
}

class FormFieldGroup {
    private VBox container;
    private TextInputControl input;

    public FormFieldGroup(VBox container, TextInputControl input) {
        this.container = container;
        this.input = input;
    }

    public VBox getContainer() {
        return container;
    }

    public TextInputControl getInput() {
        return input;
    }
}
