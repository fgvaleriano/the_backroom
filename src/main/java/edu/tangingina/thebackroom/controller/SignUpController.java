package edu.tangingina.thebackroom.controller;

import edu.tangingina.thebackroom.TheBackroom;
import edu.tangingina.thebackroom.dao.impl.UserDaoImpl;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ResourceBundle;

//This class handles with controlling the components in the fxml file....
public class SignUpController implements Initializable {
    @FXML private Label errorLabel;
    @FXML private TextField usernameField;
    @FXML private PasswordField passField;
    @FXML private PasswordField confirmPassField;
    @FXML private Button signUpBtn;
    @FXML private Button exitBtn;
    @FXML private CheckBox rememberBox;

    private UserDaoImpl userDao = new UserDaoImpl();
    private boolean saveCredentials = false;

    //The signup label, change it to omehting parehas ha left panel....pra cohesive feels..

    @FXML
    public void signUp(){
        //set style color...for clicking the button, style choice.. (this means, every chang, we have to change everything back..)
        String user = usernameField.getText();
        String pass = passField.getText();
        String confirmPass = confirmPassField.getText();

        if(user.isEmpty()){
            errorLabel.setText("Please input your username.");
            hideError(false);
        }else if(pass.isEmpty() || confirmPass.isEmpty() ){
            errorLabel.setText("Please input your password.");
            hideError(false);
        } else if(!pass.equals(confirmPass)){
            errorLabel.setText("Passwords don't match.");
            hideError(false);
        } else{
            if(validateSignUp(user, pass)){
                TheBackroom.sm.showHome();
            }

        }
    }

    @FXML
    public void exitSignUp(){
        TheBackroom.sm.showLogin();
    }

    @FXML
    public void rememberChecked(){
        if(rememberBox.isSelected()){
            saveCredentials = true;
        }else{
            saveCredentials = false;
        }
    }

    public void hideError(boolean hide){
        if(hide){
            errorLabel.setVisible(false);
            errorLabel.setManaged(false);
            //errorLabel.setManaged(false);
        }else{
            errorLabel.setVisible(true);
            errorLabel.setManaged(true);
        }
    }

    public boolean validateSignUp(String user, String pass){
        try{
            userDao.signUp(user, TheBackroom.util.getHashPass(pass));
            if(saveCredentials){
                TheBackroom.util.saveCredentials(user);
            }
            return true;
        }catch(Exception e){
            //e.printStackTrace();
            errorLabel.setText(e.getMessage());
            hideError(false);
        }

        return false;
    }

    //this means that when a window opens do this things first....
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        hideError(true);
        rememberBox.setStyle("-fx-font-family: 'Inter 24pt SemiBold'; -fx-font-size: 15");
    }

    @FXML
    public void signUpEnter(){
        signUpBtn.setStyle("-fx-background-color: #612222; -fx-background-radius: 10;");
    }

    @FXML
    public void signUpExit(){
        signUpBtn.setStyle("-fx-background-color:  rgba(139, 46, 46, 1); -fx-background-radius: 10;");
    }

    @FXML
    public void exitEnter(){
        exitBtn.setStyle("-fx-background-color: #25403c; -fx-background-radius:  50;");
    }

    @FXML
    public void exitExit(){
        exitBtn.setStyle("-fx-background-color:   rgba(48, 88, 82, 1); -fx-background-radius:  50;");
    }

    //this methods is to hover away from the fields, artistic choice....just rewiring the focus elswehere here the errorLabel...
    @FXML
    public void greenClick(){
        errorLabel.getScene().getRoot().requestFocus();
    }

    @FXML
    public void leftPClick(){
        errorLabel.getScene().getRoot().requestFocus();
    }

    @FXML
    public void rightPClick(){
        errorLabel.getScene().getRoot().requestFocus();
    }


}
