package edu.tangingina.thebackroom.util;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ConfigSetupWindow extends Application {

    private VBox root;
    private String adminUser;
    private String adminPass;
    private ConfigManager configManager = new ConfigManager();

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("The Backroom: System Initialization");

        //=======>>> Make it less ai everything here <<<========//

        root = new VBox();
        root.setPadding(new Insets(60, 60, 60, 60));
        root.setAlignment(Pos.CENTER);
        root.getStyleClass().add("setup-root");

        showStep1();

        Scene scene = new Scene(root, 600, 750);

        try {
            scene.getStylesheets().add(getClass().getResource("/edu/tangingina/thebackroom/the_backroom_style.css").toExternalForm());
        } catch (NullPointerException e) {
            System.err.println("Warning: CSS not found.");
        }

        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    private void showStep1() {
        root.getChildren().clear();
        VBox centerPane = createCenterPane();

        Label mainTitle = new Label("THE BACKROOM INITIALIZATION");
        mainTitle.setFont(FontLoader.extra(24));
        mainTitle.getStyleClass().add("setup-title");

        Label subtitle = new Label("Enter your local MySQL credentials to initialize the database. The account must have permission to create databases, tables, views, and manage users.");
        subtitle.setFont(FontLoader.regular(18));
        subtitle.getStyleClass().add("setup-subtitle");
        subtitle.setWrapText(true);
        subtitle.setTextAlignment(javafx.scene.text.TextAlignment.CENTER);
        subtitle.setMinHeight(javafx.scene.layout.Region.USE_PREF_SIZE);
        subtitle.setMaxWidth(440);

        TextField username = new TextField();
        username.setFont(FontLoader.regular(16));
        username.setPromptText("MySQL Username");
        username.getStyleClass().add("setup-field");

        PasswordField pass = new PasswordField();
        pass.setFont(FontLoader.regular(16));
        pass.setPromptText("MySQL Password");
        pass.getStyleClass().add("setup-field");

        Label status = new Label();
        status.setFont(FontLoader.bold(18));
        status.getStyleClass().add("setup-status");

        Button connectBtn = new Button("Set Up Database");
        connectBtn.setFont(FontLoader.bold(16));
        connectBtn.getStyleClass().add("setup-btn-dark-academia");
        VBox.setMargin(connectBtn, new Insets(15, 0, 0, 0));

        connectBtn.setOnAction(e -> {
            if (username.getText().isEmpty()) {
                status.setText("Username is required");
                status.setStyle("-fx-text-fill: #8B2E2E;");
                return;
            } else if(pass.getText().isEmpty()){
                status.setText("Password is required.");
                status.setStyle("-fx-text-fill: #8B2E2E;");
                return;
            }

            status.setText("Connecting to database...");
            status.setStyle("-fx-text-fill: #555555;");
            connectBtn.setDisable(true);

            boolean isConnected = configManager.testDbConnection(username.getText(), pass.getText());

            if(isConnected){
                adminUser = username.getText();
                adminPass = pass.getText();
                showStep2();
            }else{
                status.setText("Connection failed. Check credentials.");
                status.setStyle("-fx-text-fill: #8B2E2E;");
                connectBtn.setDisable(false);
            }
        });

        centerPane.getChildren().addAll(getTitleLogo(), mainTitle, subtitle, username, pass, connectBtn, status);
        root.getChildren().add(centerPane);
        Platform.runLater(() -> root.requestFocus());
    }

    private void showStep2() {
        root.getChildren().clear();
        VBox centerPane = createCenterPane();

        Label mainTitle = new Label("THE BACKROOM INITIALIZATION");
        mainTitle.setFont(FontLoader.extra(24));
        mainTitle.getStyleClass().add("setup-title");

        Label subtitle = new Label("Database connected. Set passwords for the Moderator and Normal User accounts used by the application to access the database.");
        subtitle.setFont(FontLoader.regular(18));
        subtitle.getStyleClass().add("setup-subtitle");
        subtitle.setWrapText(true);
        subtitle.setTextAlignment(javafx.scene.text.TextAlignment.CENTER);
        subtitle.setMinHeight(javafx.scene.layout.Region.USE_PREF_SIZE);
        subtitle.setMaxWidth(440);

        PasswordField modPass = new PasswordField();
        modPass.setFont(FontLoader.regular(16));
        modPass.setPromptText("Moderator Password");
        modPass.getStyleClass().add("setup-field");

        PasswordField userPass = new PasswordField();
        userPass.setFont(FontLoader.regular(16));
        userPass.setPromptText("Normal User Password");
        userPass.getStyleClass().add("setup-field");

        Label status = new Label();
        status.setFont(FontLoader.bold(17));
        status.getStyleClass().add("setup-status");

        Button finishBtn = new Button("Finish Setup");
        finishBtn.setFont(FontLoader.bold(16));
        finishBtn.getStyleClass().add("setup-btn-dark-academia");
        VBox.setMargin(finishBtn, new Insets(15, 0, 0, 0));

        finishBtn.setOnAction(e -> {
            if (modPass.getText().isEmpty() || userPass.getText().isEmpty()) {
                status.setText("Please fill in all passwords.");
                status.setStyle("-fx-text-fill: #8B2E2E;");
                return;
            }

            finishBtn.setDisable(true);
            status.setText("Initializing database... Please wait.");
            status.setStyle("-fx-text-fill: #555555;");

            configManager = new ConfigManager();

            boolean success = configManager.initializeDatabase(adminUser, adminPass, modPass.getText(), userPass.getText());

            if(success){
                showSuccessScreen();
            }else{
                status.setText("Setup failed while initializing the database. Please ensure MySQL has sufficient permissions.");
                status.setStyle("-fx-text-fill: #8B2E2E;");
                finishBtn.setDisable(false);
            }
        });

        centerPane.getChildren().addAll(getTitleLogo(), mainTitle, subtitle, modPass, userPass, finishBtn, status);
        root.getChildren().add(centerPane);
        Platform.runLater(() -> root.requestFocus());
    }

    private void showSuccessScreen() {
        root.getChildren().clear();

        VBox dialogPane = createCenterPane();
        dialogPane.setSpacing(25);

        Label title = new Label("SETUP COMPLETE");
        title.setFont(FontLoader.extra(32));
        title.getStyleClass().add("setup-title-success");

        Label subtitle = new Label("Setup complete.\nPlease relaunch the application to continue.");
        subtitle.setFont(FontLoader.regular(18));
        subtitle.getStyleClass().add("setup-subtitle");
        subtitle.setWrapText(true);
        subtitle.setTextAlignment(javafx.scene.text.TextAlignment.CENTER); // Forces the wrapped text to stay centered

        Button exitBtn = new Button("Exit Setup");
        exitBtn.setFont(FontLoader.bold(16));
        exitBtn.getStyleClass().add("setup-btn-dark-academia");

        exitBtn.setOnAction(e -> System.exit(0));

        dialogPane.getChildren().addAll(getSmallLogo(), title, subtitle, exitBtn);
        root.getChildren().add(dialogPane);
    }

    private ImageView getTitleLogo() {
        try {
            ImageView imgView = new ImageView(new Image(getClass().getResourceAsStream("/edu/tangingina/thebackroom/assets/title.png")));
            imgView.setFitWidth(300);
            imgView.setPreserveRatio(true);
            VBox.setMargin(imgView, new Insets(0, 0, 10, 0));
            return imgView;
        } catch (Exception e) {
            return new ImageView();
        }
    }

    private ImageView getSmallLogo() {
        try {
            ImageView imgView = new ImageView(new Image(getClass().getResourceAsStream("/edu/tangingina/thebackroom/assets/tbr.png")));
            imgView.setFitWidth(130);
            imgView.setPreserveRatio(true);
            VBox.setMargin(imgView, new Insets(0, 0, 15, 0));
            return imgView;
        } catch (Exception e) {
            return new ImageView();
        }
    }

    private VBox createCenterPane() {
        VBox centerPane = new VBox(20);
        centerPane.setAlignment(Pos.CENTER);
        centerPane.getStyleClass().add("setup-dialog-pane");
        return centerPane;
    }

}