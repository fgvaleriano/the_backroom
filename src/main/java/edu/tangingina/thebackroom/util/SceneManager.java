package edu.tangingina.thebackroom.util;

import edu.tangingina.thebackroom.TheBackroom;
import edu.tangingina.thebackroom.controller.HomePageController;
import edu.tangingina.thebackroom.controller.LoginController;
import edu.tangingina.thebackroom.dao.impl.UserDaoImpl;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class SceneManager {
    private FXMLLoader signUp;
    private FXMLLoader addArchive;
    private Stage stage;
    private Scene scene;

    public SceneManager(Stage stage){
        this.stage =  stage;
        initializeLogin();
    }

    public void initializeLogin(){
        try {
            //if there is a rememeber me, here muna ig check.....or before anywhere
            LoginController controller = new LoginController();
            StackPane root = controller.getLayout(stage);
            scene = new Scene(root);

            //for styling
            scene.getStylesheets().add(getClass().getResource(
                    "/edu/tangingina/thebackroom/the_backroom_style.css").toExternalForm());

            stage.setScene(scene);
            stage.setTitle("The Backroom - Login");
            stage.setMaximized(true);
            stage.show();
            stage.setResizable(true);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void showSignUp(){
        try{
            //for swtiching scenes, if using an fxml, for every swtich dpt new fxml instance...
            FXMLLoader signUp = new FXMLLoader(getClass().getResource("/edu/tangingina/thebackroom/SignUp.fxml"));

            Parent root = signUp.load();
            scene.setRoot(root);
            stage.setScene(scene);
            //stage.setFullScreen(true);

        }catch (Exception e)  {
            throw new RuntimeException(e);
        }

    }


    public void showLogin(){
        try {
            //for switching scenes, similar to fxml, for everyswitch we need a clean state...
            LoginController controller = new LoginController();
            StackPane root = controller.getLayout(stage);
            scene.setRoot(root);

            //for styling
            scene.getStylesheets().add(getClass().getResource(
                    "/edu/tangingina/thebackroom/the_backroom_style.css").toExternalForm());

            stage.setScene(scene);
            //stage.setFullScreen(true);
            stage.setTitle("The Backroom - Login");
            root.requestFocus();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void showHome() {
        try {
            HomePageController home = new HomePageController();
            StackPane homePage = home.getLayout(stage);
            scene.setRoot(homePage);
            scene.getStylesheets().add(getClass().getResource(
                    "/edu/tangingina/thebackroom/the_backroom_style.css").toExternalForm());

            stage.setScene(scene);
            stage.setTitle("The Backroom");
            homePage.requestFocus();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void clearStage(){
    }

}
