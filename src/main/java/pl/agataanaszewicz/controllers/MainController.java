package pl.agataanaszewicz.controllers;

import javafx.fxml.Initializable;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import pl.agataanaszewicz.models.UserSession;
import pl.agataanaszewicz.models.Utils;
import pl.agataanaszewicz.models.dao.ContactDao;
import pl.agataanaszewicz.models.dao.impl.ContactDaoImpl;

import java.io.IOException;
import java.net.URL;
import java.util.Observable;
import java.util.ResourceBundle;

public class MainController implements Initializable{
    @FXML
    TextField textNumber, textName, textCName, textCNumber;

    @FXML
    ListView<String> listContacts;

    @FXML
    Button buttonLogout, buttonAdd, buttonDelete;

    private ObservableList contactItems;

    private UserSession session  = UserSession.getInstance();
    private ContactDao contactDao = new ContactDaoImpl();

    public void initialize(URL location, ResourceBundle resources) {
        textName.setEditable(false);
        textNumber.setEditable(false);


        loadContacts();

        listContacts.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            textName.setText(newValue);
            textNumber.setText(contactDao.getNumber(newValue));
        });

        buttonLogout.setOnMouseClicked(e -> logout());

        updateActions();

        buttonAdd.setOnMouseClicked(e -> addContact());
        buttonDelete.setOnMouseClicked(e -> deleteContact());

    }

    private void deleteContact() {
        contactDao.removeContact(listContacts.getSelectionModel().getSelectedItem());
        Utils.createSimpleDialog("Usuwanie", "", "Poprawnie wyrzuciłeś  kontakt");
        loadContacts();

    }

    private void addContact() {
        contactDao.addContact(textCName.getText(), textCNumber.getText());
        Utils.createSimpleDialog("Dodawnie", "", "Poprawnie dodałeś kontakt");

        textCName.clear();
        textCNumber.clear();

        loadContacts();
    }

    private void updateActions(){
        textName.setOnMouseClicked(e -> {
            if(e.getClickCount() >= 2){
                textName.setEditable(true);
            }
        });

        textName.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if(!newValue){
                    contactDao.editContact(textName.getText(), textNumber.getText(), listContacts.getSelectionModel().getSelectedItem());
                    loadContacts();
                    textName.setEditable(false);
                }
            }
        });

        textNumber.setOnMouseClicked(e -> {
            if(e.getClickCount() >= 2){
                textNumber.setEditable(true);
            }
        });

        textNumber.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if(!newValue){
                    contactDao.editContact(textName.getText(), textNumber.getText(), listContacts.getSelectionModel().getSelectedItem());
                    loadContacts();
                    textNumber.setEditable(false);
                }
            }
        });
    }

    private void logout() {
        session.setLogedIn(false);
        session.setUsername(null);
        session.setId(0);

        Stage stage = (Stage) buttonLogout.getScene().getWindow();
        try {
            Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("loginView.fxml"));
            stage.setScene(new Scene(root, 600,400));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadContacts() {
        contactItems = FXCollections.observableArrayList(contactDao.getAllContactsNames(session.getUsername()));
        listContacts.setItems(contactItems);
    }
}
