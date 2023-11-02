package com.example.smp_3;

import javafx.beans.binding.BooleanBinding;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;


public class TransactionManagerController {

    ObservableList<String> accountTypeList = FXCollections.observableArrayList(
            "Checking", "College Checking", "Savings", "Money Market");

    @FXML
    private ToggleButton closeButton;

    @FXML
    private ToggleButton openButton;

    @FXML
    private ToggleGroup open_close;

    @FXML
    private CheckBox isLoyal;

    @FXML
    private RadioButton nbButton;

    @FXML
    private RadioButton newarkButton;

    @FXML
    private RadioButton caButton;

    @FXML
    private ToggleGroup campusLocation;

    @FXML
    private ComboBox accountType;
    @FXML
    private TextField firstName;

    @FXML
    private TextField lastName;

    @FXML
    private DatePicker dateOfBirth;

    @FXML
    private Button clear;

    @FXML
    private GridPane campusContainer;

    @FXML
    private void initialize(){
        accountType.setItems(accountTypeList);
        accountType.setPromptText("Account Type");
        firstName.setDisable(true);
        lastName.setDisable(true);
        dateOfBirth.setDisable(true);
        accountType.setDisable(true);
        isLoyal.setDisable(true);
        nbButton.setDisable(true);
        newarkButton.setDisable(true);
        caButton.setDisable(true);

    }



    @FXML
    void openCloseToggleButton(ActionEvent event) {
        if (event.getSource() == openButton || event.getSource() == closeButton){
            firstName.setDisable(false);
            lastName.setDisable(false);
            dateOfBirth.setDisable(false);
            accountType.setDisable(false);
        }
        if (event.getSource() == closeButton){
        }
        if (!openButton.isSelected() && !closeButton.isSelected()){
            firstName.setDisable(true);
            lastName.setDisable(true);
            dateOfBirth.setDisable(true);
            accountType.setDisable(true);
        }
    }
    @FXML
    void checkAccount (ActionEvent event) {
        if (accountType.getSelectionModel().getSelectedItem() != null) {
            if (accountType.getSelectionModel().getSelectedItem().equals("Savings")
                    || accountType.getSelectionModel().getSelectedItem().equals("Money Market")) {
                isLoyal.setDisable(false);
            } else {
                isLoyal.setDisable(true);
            }
            if (accountType.getSelectionModel().getSelectedItem().equals("College Checking")) {
                nbButton.setDisable(false);
                newarkButton.setDisable(false);
                caButton.setDisable(false);
            } else {
                nbButton.setDisable(true);
                newarkButton.setDisable(true);
                caButton.setDisable(true);
            }
        } else {
            isLoyal.setDisable(true);
            nbButton.setDisable(true);
            newarkButton.setDisable(true);
            caButton.setDisable(true);
        }
    }
    @FXML
    void clearField(ActionEvent event){
        firstName.clear();
        lastName.clear();
        dateOfBirth.setValue(null);
        accountType.getSelectionModel().clearSelection();
        accountType.setPromptText("Account Type");
        isLoyal.setSelected(false);
        campusLocation.selectToggle(null);


    }

}

