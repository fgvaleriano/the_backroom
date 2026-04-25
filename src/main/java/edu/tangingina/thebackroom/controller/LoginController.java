package edu.tangingina.thebackroom.controller;

import edu.tangingina.thebackroom.controller.dashboard.DashboardShell;
import edu.tangingina.thebackroom.util.*;
import javafx.geometry.*;
import javafx.scene.Scene;
import javafx.scene.image.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.*;
import javafx.scene.control.*;
import javafx.animation.*;
import javafx.util.*;
import javafx.stage.*;
import javafx.scene.text.*;
import javafx.event.*;

public class LoginController {
    Button login, signUp, btn, guest;
    StackPane root;
    VBox form, btn_holder;
    Image img, bkgImage;
    ImageView view;
    Label userLabel, pwLabel;
    TextField userTxt;
    CheckBox rememberMe_checkBox;
    PasswordField pwTxt;
    Alert invalid;

    public StackPane getLayout(Stage stage ) {

        root = new StackPane();
        //root.getStyleClass().add("main-background");
        bkgImage = new Image(getClass().getResourceAsStream(
                "/edu/tangingina/thebackroom/assets/folder_bkg.png"));

        BackgroundImage BI = new BackgroundImage(
                bkgImage,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                new BackgroundSize(100, 100, true, true, false, true)
        );
        root.setBackground(new Background(new BackgroundFill(Color.web("#305852"),
                CornerRadii.EMPTY, Insets.EMPTY)));
        root.setBackground(new Background(BI));

        BorderPane content = new BorderPane();
        content.setPadding(new Insets(40, 60, 40, 60));

        //container for other stuff
        form = new VBox(20);
        form.setAlignment(Pos.CENTER_LEFT);
        form.setMaxWidth(380);

        userLabel = new Label("Username");
        userLabel.setFont(FontLoader.regular(20));

        pwLabel = new Label("Password");
        pwLabel.setFont(FontLoader.regular(20));

        userTxt = createUsername();
        pwTxt = createPw();
        btn_holder = button_holder(stage);

        form.getChildren().addAll(userLabel, userTxt, pwLabel, pwTxt, btn_holder);

        StackPane rightWraper = new StackPane(form);
        rightWraper.setAlignment(Pos.CENTER_RIGHT);
        rightWraper.setPadding(new Insets(120, 80,0,0));

        content.setRight(rightWraper);

        root.getChildren().add(content);

        return root;
    }

    private VBox button_holder(Stage stage){
        VBox holder = new VBox(20);
        holder.setAlignment(Pos.CENTER);

        rememberMe_checkBox = remember();
        login = loginBtn(stage);
        signUp = signUp();
        guest = guest(stage);

        holder.getChildren().addAll(rememberMe_checkBox, login, signUp, guest);

        return holder;
    }
    //buttons
    private Button loginBtn(Stage stage){
        btn = new Button();
        btn.getStyleClass().add("image-button");

        img = new Image(getClass().getResourceAsStream("/edu/tangingina/thebackroom/assets/login btn.png"));
        view = new ImageView(img);
        view.setPreserveRatio(true);
        view.setFitWidth(250);
        btn.setGraphic(view);

        //button action
        btn.setOnAction(e -> {
            String username = userTxt.getText().trim();
            String password = pwTxt.getText().trim();

            if (username.isEmpty() || password.isEmpty()) {
                userTxt.getStyleClass().add("login-input-error");
                pwTxt.getStyleClass().add("login-input-error");
                return;
            }

            userTxt.getStyleClass().remove("login-input-error");
            pwTxt.getStyleClass().remove("login-input-error");

            //if filled and correct, continue here
            DashboardShell dashboard = new DashboardShell();

            Scene dashboardScene = new Scene(dashboard, stage.getWidth(), stage.getHeight());
            dashboardScene.getStylesheets().add(
                    getClass().getResource("/edu/tangingina/thebackroom/the_backroom_style.css").toExternalForm()
            );

            stage.setScene(dashboardScene);
            stage.setMaximized(true);

            System.out.println("Good to login");
        });
        return btn;
    }

    //button for sign up
    private Button signUp(){
        btn = new Button();
        btn.getStyleClass().add("image-button");

        img = new Image(getClass().getResourceAsStream("/edu/tangingina/thebackroom/assets/sign up.png"));
        view = new ImageView(img);
        view.setPreserveRatio(true);
        view.setFitWidth(250);

        btn.setOnAction(e-> {
            System.out.println("Going to sign up page");
        });

        btn.setGraphic(view);
        return btn;
    }

    //button for guest sign in
    private Button guest(Stage stage){
        guest = new Button("Continue as Guest");
        guest.setFont(FontLoader.semibold(20));
        guest.getStyleClass().add("btn-guest");
        //button action
        guest.setOnAction(e -> {

            //if filled and correct, continue here
            DashboardShell dashboard = new DashboardShell();

            Scene dashboardScene = new Scene(dashboard, stage.getWidth(), stage.getHeight());
            dashboardScene.getStylesheets().add(
                    getClass().getResource("/edu/tangingina/thebackroom/the_backroom_style.css").toExternalForm()
            );

            stage.setScene(dashboardScene);
            stage.setMaximized(true);

            System.out.println("Good to login");
        });
        return guest;
    }

    //text field for username
    private TextField createUsername(){
        TextField username = new TextField();
        username.setPromptText("Enter Username");

        username.getStyleClass().add("login-input");
        username.setPrefWidth(350);
        username.setMaxHeight(50);
        return username;
    }

    //text field for password
    private PasswordField createPw(){
        PasswordField pass = new PasswordField();
        pass.setPromptText("Enter Password");

        pass.getStyleClass().add("login-input");
        pass.setPrefWidth(350);
        return pass;
    }

    //remeber me check box for logging in
    private CheckBox remember() {
        rememberMe_checkBox = new CheckBox("Remember Me");
        rememberMe_checkBox.setFont(FontLoader.light(12));

        return rememberMe_checkBox;
    }

}
