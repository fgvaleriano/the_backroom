package edu.tangingina.thebackroom.controller;

import edu.tangingina.thebackroom.util.FontLoader;
import javafx.animation.PauseTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.util.*;

import java.util.ArrayList;
import java.util.List;

import static javafx.util.Duration.millis;

public class MultiValueField {
    /*
        Handles accessing input fields with multiple possible inputs

     */

    private VBox view;
    private TextField inputField;
    private FlowPane chipsPane, valuesPane;
    private List<String> values;
    private Button plusBtn;
    private HBox inputRow, valuesRow;

    public MultiValueField(String labelText, int fieldWidth) {
        Label label = FormFieldFactory.createLabel(labelText);

        inputField = new TextField();
        inputField.setFont(FontLoader.regular(18));
        inputField.getStyleClass().add("input-field");
        inputField.setPrefWidth(fieldWidth);
        inputField.setMinWidth(Control.USE_PREF_SIZE);
        inputField.setMaxWidth(Control.USE_PREF_SIZE);

        //plus button at the side of the input field
        plusBtn = createPlusBtn();

        valuesPane = new FlowPane();
        valuesPane.setHgap(10);
        valuesPane.setVgap(10);
        valuesPane.setAlignment(Pos.TOP_LEFT);

        values = new ArrayList<>();

        inputRow = new HBox(12, label, inputField, plusBtn);
        inputRow.setAlignment(Pos.CENTER_LEFT);


        Region spacer = new Region();
        spacer.setMinWidth(FormFieldFactory.getLabelSectionWidth());
        spacer.setPrefWidth(FormFieldFactory.getLabelSectionWidth());
        spacer.setMaxWidth(FormFieldFactory.getLabelSectionWidth());

        valuesRow = new  HBox(12, spacer, valuesPane);
        valuesRow.setAlignment(Pos.TOP_LEFT);

        view = new VBox(8, inputRow, valuesRow);
        view.setAlignment(Pos.TOP_LEFT);
        view.setPadding(new Insets(0));

        plusBtn.setOnAction(e -> addCurrentValue());
        inputField.setOnAction(e -> addCurrentValue());

    }

    //for the plus button at the side of the input field that allows moderator to
    //add more than one input for a field
    private Button createPlusBtn() {
        Button plsBtn = new Button();
        plsBtn.getStyleClass().add("image-button");
        Image img = new Image(getClass().getResourceAsStream(
                "/edu/tangingina/thebackroom/assets/plus_btn.png"));
        ImageView view = new ImageView(img);
        view.setPreserveRatio(true);
        view.setFitWidth(32);

        plsBtn.setGraphic(view);
        return plsBtn;
    }

    //adding current values inside main input field
    private void addCurrentValue() {
        String value = inputField.getText().trim();

        if (value.isEmpty()) {
            showError();
            return;
        }

        clearError();

        values.add(value);

        //chips are the floating pill boxes
        Button chip = new Button(value);
        chip.setFont(FontLoader.light(15));
        chip.getStyleClass().add("input-chip");

        chip.setOnAction(e -> {
            values.remove(value);
            valuesPane.getChildren().remove(chip);
        });

        valuesPane.getChildren().add(chip);
        inputField.clear();
    }

    public boolean isEmpty() {
        return values.isEmpty() && inputField.getText().trim().isEmpty();
    }

    public void showError() {
        if (!inputField.getStyleClass().add("input-field-error")) {
            inputField.getStyleClass().add("input-field-error");
        }
    }

    public void clearError() {
        inputField.getStyleClass().remove("input-field-error");
        valuesPane.getStyleClass().remove("input-field-error");
    }

    public VBox getView() {
        return view;
    }

    public List<String> getValues() {
        return values;
    }
}
