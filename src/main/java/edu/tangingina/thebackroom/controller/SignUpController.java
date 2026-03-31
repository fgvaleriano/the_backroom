package edu.tangingina.thebackroom.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

//This class handles with controlling the components in the fxml file....
public class SignUpController implements Initializable {
    //this would get the components in the fxml, and it must be the names are all the same....
    @FXML private Label errorLabel;
    @FXML private TextField usernameField;
    @FXML private PasswordField passField;
    @FXML private PasswordField confirmPassField;
    @FXML private Button signUpBtn;


    @FXML
    public void signUp(){
        //set style color...for clicking the button, style choice.. (this means, every chang, we have to change everything back..)
        signUpBtn.setStyle("-fx-background-color: rgba(74, 91, 109, 1); -fx-background-radius: 10;");
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
            System.out.println("Welcome new member!!!");
        }

        signUpBtn.setStyle("-fx-background-color: rgb(99, 121, 145); -fx-background-radius: 10;");
    }

    public void hideError(boolean hide){
        if(hide){
            errorLabel.setVisible(false);
            //errorLabel.setManaged(false);
        }else{
            errorLabel.setVisible(true);
            //errorLabel.setManaged(true);
        }
    }

    //this means that when a window opens do this things first....
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        hideError(true);
    }

    @FXML
    public void signUpEnter(){
        signUpBtn.setStyle("-fx-background-color: rgba(74, 91, 109, 1); -fx-background-radius: 10;");
    }

    @FXML
    public void signUpExit(){
        signUpBtn.setStyle("-fx-background-color: rgb(99, 121, 145); -fx-background-radius: 10;");
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
