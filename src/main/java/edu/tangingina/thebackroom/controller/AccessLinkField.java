package edu.tangingina.thebackroom.controller;

import edu.tangingina.thebackroom.util.FontLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;

import java.util.*;

public class AccessLinkField {
    /*
        Deals with the double input field fo access links
            - handles website name
            - webiste link
            - plus btn

     */

    private VBox view;
    private TextField websiteNameField;
    private TextField websiteLinkField;
    private Button plusBtn;
    private Label label;
    private FlowPane valuesPane;
    private HBox valuesRow;

    private List<AccessLink> values;

    public AccessLinkField(String labelText) {
        label = FormFieldFactory.createLabel(labelText);

        Label webName = new Label("Website Name");
        Label webLink = new Label("Website Link");


        websiteNameField = new TextField();
        websiteNameField.setFont(FontLoader.regular(18));
        websiteNameField.getStyleClass().add("input-field");
        websiteNameField.setPromptText("Site Name");
        websiteNameField.setPrefWidth(120);
        websiteNameField.setMinWidth(Control.USE_PREF_SIZE);
        websiteNameField.setMaxWidth(Control.USE_PREF_SIZE);

        websiteLinkField = new TextField();
        websiteLinkField.setFont(FontLoader.light(18));
        websiteLinkField.getStyleClass().add("input-field");
        websiteLinkField.setPromptText("Site Link");
        websiteLinkField.setPrefWidth(275);
        websiteLinkField.setMinWidth(Control.USE_PREF_SIZE);
        websiteLinkField.setMaxWidth(Control.USE_PREF_SIZE);

        plusBtn = createPlusBtn();

        HBox inputRow = new HBox(18, label, websiteNameField, websiteLinkField, plusBtn);
        inputRow.setAlignment(Pos.CENTER_LEFT);

        valuesPane = new FlowPane();
        valuesPane.setHgap(18);
        valuesPane.setVgap(10);
        valuesPane.setAlignment(Pos.TOP_LEFT);

        values = new ArrayList<>();

        Region spacer = new Region();
        spacer.setMinWidth(FormFieldFactory.getLabelSectionWidth());
        spacer.setPrefWidth(FormFieldFactory.getLabelSectionWidth());
        spacer.setMaxWidth(FormFieldFactory.getLabelSectionWidth());

        valuesRow = new HBox(18, spacer, valuesPane);
        valuesRow.setAlignment(Pos.TOP_LEFT);

        valuesRow.setVisible(false);
        valuesRow.setManaged(false);

        view = new VBox(14, inputRow, valuesRow);
        view.setAlignment(Pos.TOP_LEFT);
        view.setPadding(new Insets(0));

        plusBtn.setOnAction(e -> addCurrentValue());

        websiteNameField.textProperty().addListener((obs, oldVal, newVal) -> clearError());
        websiteLinkField.textProperty().addListener((obs, oldVal, newVal) -> clearError());
    }

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

    public static class AccessLink {
        private String websiteName;
        private String websiteLink;

        public AccessLink(String websiteName, String websiteLink) {
            this.websiteName = websiteName;
            this.websiteLink = websiteLink;
        }

        public String getWebsiteName() {
            return websiteName;
        }

        public String getWebsiteLink() {
            return websiteLink;
        }
    }

    private void addCurrentValue() {
        String websiteName = websiteNameField.getText().trim();
        String websiteLink = websiteLinkField.getText().trim();

        if (websiteName.isEmpty() || websiteLink.isEmpty()) {
            showError();
            return;
        }

        if (alreadyExists(websiteName, websiteLink)) {
            showError();
            return;
        }

        clearError();

        AccessLink accessLink = new AccessLink(websiteName, websiteLink);
        values.add(accessLink);

        Button chip = new Button(websiteName);
        chip.setFont(FontLoader.light(15));
        chip.getStyleClass().add("input-chip");

        chip.setOnAction(e -> {
            values.remove(accessLink);
            valuesPane.getChildren().remove(chip);

            if (values.isEmpty()) {
                valuesRow.setVisible(false);
                valuesRow.setManaged(false);
            }
        });

        valuesPane.getChildren().add(chip);

        valuesRow.setVisible(true);
        valuesRow.setManaged(true);

        websiteNameField.clear();
        websiteLinkField.clear();
    }

    private boolean alreadyExists(String websiteName, String websiteLink) {
        for (AccessLink link : values) {
            if (
                    link.getWebsiteName().equalsIgnoreCase(websiteName)
                            && link.getWebsiteLink().equalsIgnoreCase(websiteLink)
            ) {
                return true;
            }
        }

        return false;
    }

    public void showError() {
        if (websiteNameField.getText().trim().isEmpty()) {
            if (!websiteNameField.getStyleClass().contains("input-field-error")) {
                websiteNameField.getStyleClass().add("input-field-error");
            }
        }

        if (websiteLinkField.getText().trim().isEmpty()) {
            if (!websiteLinkField.getStyleClass().contains("input-field-error")) {
                websiteLinkField.getStyleClass().add("input-field-error");
            }
        }
    }

    public void clearError() {
        websiteNameField.getStyleClass().remove("input-field-error");
        websiteLinkField.getStyleClass().remove("input-field-error");
    }

    public boolean isEmpty() {
        return values.isEmpty() && websiteNameField.getText().trim().isEmpty()
                && websiteLinkField.getText().trim().isEmpty();
    }

    public VBox getView() {
        return view;
    }

    public List<AccessLink> getValues() {
        return values;
    }

    public void setLink(String websiteData) {
        values.clear();
        valuesPane.getChildren().clear();

        if (websiteData == null || websiteData.isEmpty()) {return;}

        String[] entries = websiteData.split(",\\s*");
        for (String entry : entries) {
            String[] parts = entry.split("\\|");

            if (parts.length == 2) {
                String name = parts[0].trim();
                String link = parts[1].trim();

                AccessLink newLink = new AccessLink(name, link);
                values.add(newLink);

                Button chip = new Button(name);
                chip.setFont(FontLoader.light(15));
                chip.getStyleClass().add("input-chip");

                chip.setOnAction(e -> {
                    values.remove(newLink);
                    valuesPane.getChildren().remove(chip);
                });

                valuesPane.getChildren().add(chip);
            }
        }
    }
}
