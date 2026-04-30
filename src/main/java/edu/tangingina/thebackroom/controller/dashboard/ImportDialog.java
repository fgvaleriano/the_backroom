package edu.tangingina.thebackroom.controller.dashboard;

import edu.tangingina.thebackroom.controller.AddArchive_v2;
import edu.tangingina.thebackroom.controller.FormFieldFactory;
import edu.tangingina.thebackroom.controller.FormFieldGroup;
import edu.tangingina.thebackroom.util.FontLoader;
import javafx.geometry.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.*;

import java.io.File;

public class ImportDialog {

    private static Scene scene;
    private static Stage window;
    private static ComboBox<String> mediaTypeSelector;
    private static Label header, mediaTypeLabel, fileTypeLabel, inputLabel;
    private static StackPane root;
    private static HBox row, fileTypeRow, mediaTypeRow, file;
    private static File selectedFile;
    private static VBox formContent;
    private static TextField filePathField;

    public static void importDialogView() {
        window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Import Archive");
        window.setResizable(false);

        root = new StackPane();
        root.getStyleClass().add("light-bkg");

        formContent = new VBox(50);
        formContent.setAlignment(Pos.TOP_CENTER);
        formContent.setPadding(new Insets(40, 0, 0, 0));

        //----- HEADER COMPONENT --------
        header = new Label("Import Archive");
        header.setFont(FontLoader.extra(30));
        header.getStyleClass().add("header-text");
        //header.setAlignment(Pos.CENTER);

        VBox fieldsBox = new VBox(28);
        fieldsBox.setAlignment(Pos.CENTER);
        fieldsBox.setPrefWidth(520);
        fieldsBox.setMaxWidth(520);

        file = getInput();

        fieldsBox.getChildren().addAll(file);

        //import button
        Button addBtn = new Button();
        addBtn.getStyleClass().add("image-button");
        Image img = new Image(ImportDialog.class.getResourceAsStream(
                "/edu/tangingina/thebackroom/assets/import.png"));
        ImageView view = new ImageView(img);
        view.setPreserveRatio(true);
        view.setFitWidth(125);

        addBtn.setGraphic(view);
        addBtn.setOnAction(e -> {
            System.out.println("Importing Archive");

            if (selectedFile == null || filePathField.getText().isBlank()) {
                if (!filePathField.getStyleClass().contains("error-field")) {
                    filePathField.getStyleClass().add("input-field-error");
                }

                filePathField.setPromptText("Please upload a file");
                return;
            }
            closeWindow();
        });

        formContent.getChildren().addAll(header, fieldsBox, addBtn);
        root.getChildren().add(formContent);
        StackPane.setAlignment(formContent, Pos.TOP_CENTER);

        scene = new Scene(root, 645, 450);
        scene.getStylesheets().add(ImportDialog.class.getResource(
                "/edu/tangingina/thebackroom/the_backroom_style.css").toExternalForm());


        window.setScene(scene);
        window.showAndWait();
    }

    //close dialog box
    public static void closeWindow() {
        window.close();
    }

    private static HBox getInput() {
        inputLabel = new Label("Input");
        inputLabel.setFont(FontLoader.bold(18));
        inputLabel.getStyleClass().add("input-label");
        inputLabel.setPrefWidth(85);
        inputLabel.setMinWidth(85);
        inputLabel.setMaxWidth(85);

        filePathField = new TextField();
        filePathField.setPromptText("Upload File Here");
        filePathField.setEditable(false);
        filePathField.getStyleClass().add("input-field");
        filePathField.setPrefSize(315, 35);
        filePathField.setMinSize(315, 35);
        filePathField.setMaxSize(315, 35);

        Button browseBtn = new Button();
        browseBtn.getStyleClass().add("image-button");
        Image img = new Image(ImportDialog.class.getResourceAsStream(
                "/edu/tangingina/thebackroom/assets/browse.png"));
        ImageView view = new ImageView(img);
        view.setPreserveRatio(true);
        view.setFitWidth(60);
        browseBtn.setGraphic(view);

        browseBtn.setOnAction(e -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Choose File to Import");

            fileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("Supported Files", "*.csv", "*.json", "*.sql"),
                    new FileChooser.ExtensionFilter("CSV Files", "*.csv"),
                    new FileChooser.ExtensionFilter("JSON Files", "*.json"),
                    new FileChooser.ExtensionFilter("SQL Files", "*.sql"),
                    new FileChooser.ExtensionFilter("All Files", "*.*")
            );

            selectedFile = fileChooser.showOpenDialog(window);

            if (selectedFile != null) {
                filePathField.setText(selectedFile.getAbsolutePath());
                filePathField.getStyleClass().remove("error-field");
            }
        });

        HBox filePicker = new HBox(10, filePathField, browseBtn);
        filePicker.setAlignment(Pos.CENTER_LEFT);

        row = new HBox(18, inputLabel, filePicker);
        row.setAlignment(Pos.CENTER_LEFT);
        row.setPrefWidth(400);
        return row;
    }
}
