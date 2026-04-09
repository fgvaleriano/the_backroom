package edu.tangingina.thebackroom.controller;

import javafx.application.*;
import javafx.geometry.*;
import javafx.scene.*;
import javafx.scene.image.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.*;
import javafx.stage.*;

public class LoginController {
    Button login, guest, btn;
    StackPane root;
    VBox form;
    Image img, bkgImage;
    ImageView view;
    Label userLabel, pwLabel;
    TextField userTxt;
    PasswordField pwTxt;

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
        //form.setPadding(new Insets(0, 150, 0, 0));

        userLabel = new Label("USERNAME");
        userLabel.getStyleClass().add("label");

        pwLabel = new Label("PASSWORD");
        pwLabel.getStyleClass().add("label");

        userTxt = createUsername();
        pwTxt = createPw();
        login = loginBtn();
        guest = guestBtn();

        form.getChildren().addAll(userLabel, userTxt, pwLabel, pwTxt, login, guest);

        StackPane rightWraper = new StackPane(form);
        rightWraper.setAlignment(Pos.CENTER_RIGHT);
        rightWraper.setPadding(new Insets(0, 80,0,0));

        content.setRight(rightWraper);

        root.getChildren().add(content);

        return root;
    }

    //buttons
    private Button loginBtn(){
        btn = new Button();
        btn.getStyleClass().add("image-button");

        img = new Image(getClass().getResourceAsStream("/edu/tangingina/thebackroom/assets/login btn.png"));
        view = new ImageView(img);
        view.setPreserveRatio(true);
        view.setFitWidth(275);
        btn.setGraphic(view);
        return btn;
    }

    private Button guestBtn(){
        btn = new Button();
        btn.getStyleClass().add("image-button");

        img = new Image(getClass().getResourceAsStream("/edu/tangingina/thebackroom/assets/sign up.png"));
        view = new ImageView(img);
        view.setPreserveRatio(true);
        view.setFitWidth(275);

        btn.setGraphic(view);
        return btn;
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
}
