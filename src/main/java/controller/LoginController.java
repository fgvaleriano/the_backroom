package controller;

import javafx.application.*;
import javafx.geometry.*;
import javafx.scene.*;
import javafx.scene.image.*;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.*;
import javafx.stage.*;

public class LoginController {
    Button login, guest, btn;
    StackPane root;
    VBox form;
    Image img;
    ImageView view;
    Label userLabel, pwLabel;
    TextField userTxt;
    PasswordField pwTxt;

    public StackPane getLayout(Stage stage ) {
        root = new StackPane();
        root.getStyleClass().add("main-background");

        BorderPane content = new BorderPane();
        content.setPadding(new Insets(40, 60, 40, 60));

        //container for other stuff
        form = new VBox(20);
        form.setAlignment(Pos.CENTER_LEFT);
        form.setMaxWidth(380);
        //form.setPadding(new Insets(0, 150, 0, 0));

        userLabel = new Label("USERNAME");
        userLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 14px;" +
                "-fx-font-family: Roboto; -fx-text-fill: #305852");

        pwLabel = new Label("PASSWORD");
        pwLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 14px;" +
                "-fx-font-family: Roboto; -fx-text-fill: #305852");

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

        img = new Image(getClass().getResourceAsStream("/ui/assets/login.png"));
        view = new ImageView(img);
        view.setPreserveRatio(true);
        view.setFitWidth(350);

        btn.setGraphic(view);
        return btn;
    }

    private Button guestBtn(){
        btn = new Button();
        btn.getStyleClass().add("image-button");

        img = new Image(getClass().getResourceAsStream("/ui/assets/guest btn.png"));
        view = new ImageView(img);
        view.setPreserveRatio(true);
        view.setFitWidth(350);

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
