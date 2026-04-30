package edu.tangingina.thebackroom.controller;

import edu.tangingina.thebackroom.util.FontLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Window;

import java.io.File;

public class ImageFileField {
    /*
        Handles image file selection for forms

        Purpose:
            - lets user choose an image file only
            - show selected file apth
            - stores selected file

     */

    private VBox view;
    private TextField filePathField;
    private Button browseBtn, clearBtn;
    private ImageView imagePreview;
    private StackPane previewBox;

    private File selectedFile;

    public ImageFileField(String labelText, int fieldWidth){
        Label label = new Label(labelText);
        label.setFont(FontLoader.bold(18));
        label.getStyleClass().add("input-label");

        filePathField = new TextField();
        filePathField.setFont(FontLoader.light(14));
        filePathField.getStyleClass().add("input-field");
        filePathField.setPromptText("Upload image here");
        filePathField.setEditable(false);
        filePathField.setPrefWidth(fieldWidth);
        filePathField.setMinWidth(Control.USE_PREF_SIZE);
        filePathField.setMaxWidth(Control.USE_COMPUTED_SIZE);

        browseBtn = createBrowseBtn();
        clearBtn = createClearButton();

        HBox inputRow = new HBox(12, label, filePathField, browseBtn, clearBtn);
        inputRow.setAlignment(Pos.CENTER_LEFT);

        Region spacer = new Region();
        spacer.setMinWidth(FormFieldFactory.getLabelSectionWidth());
        spacer.setPrefWidth(FormFieldFactory.getLabelSectionWidth());
        spacer.setMaxWidth(FormFieldFactory.getLabelSectionWidth());

        previewBox = createPreviewBox();

        HBox previewRow = new HBox(12, spacer, previewBox);
        previewRow.setAlignment(Pos.TOP_LEFT);

        view = new VBox(8, inputRow, previewRow);
        view.setAlignment(Pos.TOP_LEFT);
        view.setPadding(new Insets(0));

        browseBtn.setOnAction(e -> openImageChooser());
        clearBtn.setOnAction(e -> clearSelectedImage());

    }

    private StackPane createPreviewBox() {
        StackPane box = new StackPane();
        box.getStyleClass().add("image-preview-box");
        box.setPrefSize(180, 230);
        box.setMinSize(180, 230);
        box.setMaxSize(180, 230);

        Label placeholder = new Label("No image selected");
        placeholder.setFont(FontLoader.light(15));
        placeholder.getStyleClass().add("image-preview-placeholder");

        imagePreview = new ImageView();
        imagePreview.setPreserveRatio(true);
        imagePreview.setFitWidth(170);
        imagePreview.setFitHeight(220);

        box.getChildren().addAll(placeholder, imagePreview);

        return box;
    }

    private Button createBrowseBtn(){
        Button btn = new Button();
        btn.getStyleClass().add("image-button");
        Image img = new Image(getClass().getResourceAsStream(
                "/edu/tangingina/thebackroom/assets/browse.png"));
        ImageView view = new ImageView(img);
        view.setPreserveRatio(true);
        view.setFitWidth(68);
        btn.setGraphic(view);
        return btn;
    }

    private Button createClearButton() {
        Button button = new Button("Clear");
        button.setFont(FontLoader.regular(17));
        button.getStyleClass().add("btn-guest");
        return button;
    }

    private void openImageChooser() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose Image File");

        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter(
                        "Image Files", "*.png", "*.jpg"
                ),
                new FileChooser.ExtensionFilter("PNG Files", "*.png"),
                new FileChooser.ExtensionFilter("JPEG Files", "*.jpg", "*.jpeg"),
                new FileChooser.ExtensionFilter("GIF Files", "*.gif"),
                new FileChooser.ExtensionFilter("BMP Files", "*.bmp")
        );

        Window owner = null;

        if (view.getScene() != null) {
            owner = view.getScene().getWindow();
        }

        File file = fileChooser.showOpenDialog(owner);

        if (file != null) {
            setSelectedImage(file);
        }
    }

    private void setSelectedImage(File file) {
        try {
            Image image = new Image(file.toURI().toString());

            selectedFile = file;
            filePathField.setText(file.getAbsolutePath());
            imagePreview.setImage(image);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void clearSelectedImage() {
        selectedFile = null;
        filePathField.clear();
        imagePreview.setImage(null);
    }

    public boolean isEmpty() {
        return selectedFile == null;
    }

    public VBox getView() {
        return view;
    }

    public File getSelectedFile() {
        return selectedFile;
    }

    public String getSelectedFilePath() {
        if (selectedFile == null) {
            return "";
        }

        return selectedFile.getAbsolutePath();
    }

    public String getSelectedFileName() {
        if (selectedFile == null) {
            return "";
        }

        return selectedFile.getName();
    }


    public void setImage(String filePath) {
        if (filePath == null) {
            clearSelectedImage();
            return;
        }

        File file = new File(filePath);

        if (file.exists()) {
            setSelectedImage(file);
        } else {
            System.err.println("Widget image file not found at: " + filePath);
            clearSelectedImage();
        }
    }

}
