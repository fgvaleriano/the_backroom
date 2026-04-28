package edu.tangingina.thebackroom.util;

import edu.tangingina.thebackroom.TheBackroom;
import edu.tangingina.thebackroom.controller.HomePageController;
import edu.tangingina.thebackroom.controller.LoginController;
import edu.tangingina.thebackroom.controller.dashboard.DashboardShell;
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
    private UserDaoImpl userDao = new UserDaoImpl();

    public SceneManager(Stage stage){
        this.stage =  stage;

        //before everything, we need to check if there is an app env, so that if there is automatic home
        if(checkAppEnv()){
            showHome();
            stage.setScene(scene);
            stage.setMaximized(true);
            stage.show();
            stage.setResizable(true);
        }else{
            initializeLogin();
        }

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

            //stage.setFullScreen(true);

            stage.setTitle("The Backroom - Login");
            root.requestFocus();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void showHome() {
        try {
            DashboardShell dashboard = new DashboardShell();
            if(scene == null){
                scene = new Scene(dashboard);
            }else{
                scene.setRoot(dashboard);
            }
            scene.getStylesheets().add(
                    getClass().getResource("/edu/tangingina/thebackroom/the_backroom_style.css").toExternalForm()
            );

            stage.setTitle("The Backroom");
            //dashboard.setView(new MainMenuView());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void clearStage(){
    }

    public boolean checkAppEnv(){
        String username = TheBackroom.util.checkAppEnv();
        if(username != null){
            try{
                userDao.getUser(username);
                return true;
            }catch(Exception e){
                e.printStackTrace();
            }
        }
        return false;
    }

}
