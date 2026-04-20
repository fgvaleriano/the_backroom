package edu.tangingina.thebackroom.controller;

import edu.tangingina.thebackroom.util.FontLoader;
import javafx.scene.control.*;
import javafx.scene.layout.*;

public class FormFieldFactory {
    /*
        Creates the basic structure of textfields and textareas so that we won't have to
        keep on coding the same thing for input fields
     */

    public static VBox createTextField (String labelText) {
        Label inputLabel = new Label(labelText);
        TextField inputField = new TextField();
        inputLabel.setFont(FontLoader.regular(15));
        inputField.setFont(FontLoader.regular(15));

        inputField.getStyleClass().add("input-field");
        inputLabel.getStyleClass().add("input-label");

        VBox holder = new VBox(6, inputLabel, inputField);
        holder.setMaxWidth(320);

        return holder;
    }

    public static VBox createTextArea (String labelText) {
        Label inputLabel = new Label(labelText);
        TextArea inputArea = new TextArea();
        inputLabel.setFont(FontLoader.regular(15));
        inputArea.setFont(FontLoader.regular(15));

        inputArea.setPrefRowCount(5);
        inputArea.setWrapText(true);

        inputArea.getStyleClass().add("input-field");
        inputLabel.getStyleClass().add("input-label");

        VBox holder = new VBox(6, inputLabel, inputArea);
        holder.setMaxWidth(420);

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
