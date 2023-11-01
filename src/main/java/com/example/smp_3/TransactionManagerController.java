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
    private CheckBox isLoyal;

    @FXML
    private ToggleGroup open_close;

    @FXML ToggleGroup campusLocation;

    @FXML
    private ComboBox accountType;
    @FXML
    private TextField firstName;

    @FXML
    private TextField lastName;

    @FXML
    private DatePicker dateOfBirth;

    @FXML
    private GridPane campusContainer;

    @FXML
    private void initialize(){
        BooleanBinding noneSelected = openButton.selectedProperty().not().and(closeButton.selectedProperty().not());
        firstName.disableProperty().bind(noneSelected);
        lastName.disableProperty().bind(noneSelected);
        dateOfBirth.disableProperty().bind(noneSelected);
        accountType.disableProperty().bind(noneSelected);
        isLoyal.setVisible(false);
        campusContainer.setVisible(false);
        accountType.setItems(accountTypeList);

    }



    @FXML
    void openCloseToggleButton(ActionEvent event) {
        if (event.getSource() == openButton){

        }
        else {
            isLoyal.setVisible(false);
        }
    }
    @FXML
    void checkAccount (ActionEvent event){
        if (accountType.getSelectionModel().getSelectedItem().equals("Savings")
                ||accountType.getSelectionModel().getSelectedItem().equals("Money Market")) {
            isLoyal.setVisible(true);
        }
        else{
            isLoyal.setVisible(false);
        }
        if (accountType.getSelectionModel().getSelectedItem().equals("College Checking")){
            campusContainer.setVisible(true);
        }
        else {
            campusContainer.setVisible(false);
        }
    }

}

