package pl.agataanaszewicz.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import pl.agataanaszewicz.models.UserSession;
import pl.agataanaszewicz.models.Utils;
import pl.agataanaszewicz.models.dao.UserDao;
import pl.agataanaszewicz.models.dao.impl.UserDaoImpl;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

    public class LoginController implements Initializable{

        @FXML
        TextField textLogin, textLoginR;

        @FXML
        PasswordField textPassword, textPasswordR, textPasswordRepeatR;

        @FXML
        Button buttonLogin, buttonRegister;

        private UserSession userSession = UserSession.getInstance();
        private UserDao userDao = new UserDaoImpl();

        @Override
        public void initialize(URL location, ResourceBundle resources) {
            buttonLogin.setOnMouseClicked(e -> tryLogin());
            buttonRegister.setOnMouseClicked(e -> tryRegister());
        }


        private void tryRegister() {
            String login = textLoginR.getText();
            String password = textPasswordR.getText();

            if(!checkRegisterData()){
                return;
            }

            if(userDao.register(login,  password)){
                Utils.createSimpleDialog("Rejestracja", "", "Zarejestrowałes sie poprawnie");
            }else{
                Utils.createSimpleDialog("Rejestracja", "", "Login zajety");

            }

        }

        private boolean checkRegisterData() {
            String login = textLoginR.getText();
            String password = textPasswordR.getText();
            String passwordRepeat = textPasswordRepeatR.getText();

            if(login.isEmpty() || password.isEmpty() || passwordRepeat.isEmpty()){
                Utils.createSimpleDialog("Rejestracja", "", "Pola nie mogą być puste");
                return false;
            }

            if(!password.equals(passwordRepeat)){
                Utils.createSimpleDialog("Rejestracja", "", "Hasła muszą się zgadzać");
                return false;
            }

            if(password.length() < 5){
                Utils.createSimpleDialog("Rejestracja", "", "Hasło musi być dluzsze niz 5 znakow");
                return false;
            }
            return  true;
        }

        private boolean checkLoginData(){
            String login = textLogin.getText();
            String password = textPassword.getText();

            if(login.isEmpty() || password.isEmpty()){
                Utils.createSimpleDialog("Logowanie", "", "Pola nie mogą być puste");
                return false;
            }

            if(login.length() <= 3 || password.length() <= 5){
                Utils.createSimpleDialog("Logowanie", "", "Dane za krótkie");
                return false;
            }

            return true;
        }

        private void tryLogin() {
            String login = textLogin.getText();
            String password = textPassword.getText();

            if(!checkLoginData()){
                return;
            }

            if(userDao.login(login, password)){
                userSession.setUsername(login);
                userSession.setLogedIn(true);

                try {
                    Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("mainView.fxml"));
                    Stage stageRoot = (Stage) buttonLogin.getScene().getWindow();
                    stageRoot.setScene(new Scene(root, 600,400));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }else{
                Utils.createSimpleDialog("Logowanie", "", "Masz błąd w danych");
        }
    }

}
