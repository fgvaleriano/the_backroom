package edu.tangingina.thebackroom.controller.dashboard;

import edu.tangingina.thebackroom.util.*;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.*;

public class ExportDialog {

    /*
        This manages the UI for exporting and aslo the saving logic

     */
    private static Scene scene;
    private static Stage window;
    private static ComboBox<String> mediaTypeSelector;
    private static HBox row, mediaTypeRow;
    private static StackPane root;
    private static Label header, mediaTypeLabel, errorLabel;
    private static VBox formContent;

    public static void exportDialogView() {
        window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Export Archive");
        window.setResizable(false);

        root = new StackPane();
        root.getStyleClass().add("light-bkg");

        formContent = new VBox(50);
        formContent.setAlignment(Pos.TOP_CENTER);
        formContent.setPadding(new Insets(40, 0, 0, 0));

        header = new Label ("Export Archive");
        header.setFont(FontLoader.extra(30));
        header.getStyleClass().add("header-text");

        VBox fieldsBox = new VBox(28);
        fieldsBox.setAlignment(Pos.CENTER);
        fieldsBox.setPrefWidth(520);
        fieldsBox.setMaxWidth(520);

        errorLabel = new Label("Please select a media type");
        errorLabel.setFont(FontLoader.bold(16));
        errorLabel.getStyleClass().add("error-label");
        errorLabel.setVisible(false);
        errorLabel.setManaged(false);

        mediaTypeRow = getMediaType();

        fieldsBox.getChildren().addAll(mediaTypeRow);

        Button exportBtn = new Button();
        exportBtn.getStyleClass().add("image-button");
        Image img = new Image(ImportDialog.class.getResourceAsStream(
                "/edu/tangingina/thebackroom/assets/export.png"));
        ImageView view = new ImageView(img);
        view.setPreserveRatio(true);
        view.setFitWidth(125);

        exportBtn.setGraphic(view);

        exportBtn.setOnAction(e -> {

            if (mediaTypeSelector.getValue()== null) {
                mediaTypeSelector.getStyleClass().add("combo-box-error");
                errorLabel.setVisible(true);
                errorLabel.setManaged(true);
                return;
            }

            mediaTypeSelector.getStyleClass().remove("combo-box-error");
            errorLabel.setVisible(false);
            errorLabel.setManaged(false);

            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Save Exported Archive");

            String selectedType = mediaTypeSelector.getValue();
            String fileName = (selectedType == null || selectedType.equals("All Media")) ? "Full_Archive" : selectedType;

            fileChooser.setInitialFileName(fileName + "_Export.csv");
            fileChooser.getExtensionFilters().add(
                    new FileChooser.ExtensionFilter("CSV files (*.csv)", "*.csv")
            );

            java.io.File file = fileChooser.showSaveDialog(window);

            if (file != null) {
                try {
                    DatabaseManager db = new DatabaseManager();
                    //db.getConnection();

                    FileManager.exportData(db, file.getAbsolutePath(), mediaTypeSelector.getValue());
                    closeWindow();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }

            }
        });

        VBox btnContainer = new VBox(8, exportBtn, errorLabel);
        btnContainer.setAlignment(Pos.CENTER);

        formContent.getChildren().addAll(header, fieldsBox, btnContainer);
        root.getChildren().add(formContent);
        StackPane.setAlignment(formContent, Pos.TOP_CENTER);

        scene = new Scene(root, 645, 450);
        scene.getStylesheets().add(ImportDialog.class.getResource(
                "/edu/tangingina/thebackroom/the_backroom_style.css").toExternalForm());


        window.setScene(scene);
        window.showAndWait();
    }

    //media type selector
    private static HBox getMediaType() {
        mediaTypeLabel = new Label("Media Type");
        mediaTypeLabel.setFont(FontLoader.bold(18));
        mediaTypeLabel.getStyleClass().add("input-label");
        mediaTypeLabel.setPrefWidth(85);
        mediaTypeLabel.setMinWidth(85);
        mediaTypeLabel.setMaxWidth(85);

        //combo box that shows what type of media user will add
        mediaTypeSelector = new ComboBox<>();
        mediaTypeSelector.getItems().addAll("All Media", "Book", "Movie", "TvShow", "Game");
        mediaTypeSelector.setPromptText("");
        mediaTypeSelector.getStyleClass().add("combo-box");
        mediaTypeSelector.setPrefSize(315, 40);
        mediaTypeSelector.setMinSize(315, 40);
        mediaTypeSelector.setMaxSize(315, 40);

        row = new HBox(20, mediaTypeLabel, mediaTypeSelector);
        row.setAlignment(Pos.CENTER_LEFT);
        row.setPrefWidth(400);

        return row;
    }

    public static void closeWindow() {
        window.close();
    }

}
