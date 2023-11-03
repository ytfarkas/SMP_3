package com.example.smp_3;

import javafx.beans.binding.BooleanBinding;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import java.time.LocalDate;


public class TransactionManagerController {

    ObservableList<String> accountTypeList = FXCollections.observableArrayList(
            "Checking", "College Checking", "Savings", "Money Market");
    AccountDatabase accountDatabase = new AccountDatabase();

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
    private TextField amount;

    @FXML
    private DatePicker dateOfBirth;

    @FXML
    private Button clear;

    @FXML
    private Button confirm;

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
        campusContainer.setVisible(false);
        amount.setDisable(true);


    }



    @FXML
    void openCloseToggleButton(ActionEvent event) {
        if (event.getSource() == openButton){
            firstName.setDisable(false);
            lastName.setDisable(false);
            dateOfBirth.setDisable(false);
            accountType.setDisable(false);
            amount.setDisable(false);
            campusContainer.setVisible(true);
        }
        if (event.getSource() == closeButton){
            firstName.setDisable(false);
            lastName.setDisable(false);
            dateOfBirth.setDisable(false);
            accountType.setDisable(false);
            amount.setDisable(true);
            campusContainer.setVisible(false);
        }
        if (!openButton.isSelected() && !closeButton.isSelected()){
            firstName.setDisable(true);
            lastName.setDisable(true);
            dateOfBirth.setDisable(true);
            accountType.setDisable(true);
            amount.setDisable(true);
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
    void clearField(ActionEvent event){ //accountType.setprompt needs to be fixed
        firstName.clear();
        lastName.clear();
        dateOfBirth.setValue(null);
        accountType.getSelectionModel().clearSelection();
        isLoyal.setSelected(false);
        campusLocation.selectToggle(null);
        amount.clear();
    }


    //fix This is more than 40 lines
    @FXML
    void confirmField (ActionEvent event){
        String fName = firstName.getText().trim();
        String lName = lastName.getText().trim();
        String dateOB = String.valueOf(dateOfBirth.getValue());
        Date date = new Date(dateOB);
        Profile profile = new Profile(fName, lName, date);
        String accType = (String) accountType.getValue();
        if (open_close.getSelectedToggle().equals(openButton)){
            int amountDepo = Integer.parseInt(amount.getText());
            if (accType.equals("Savings")){
                boolean isLoy = isLoyal.isSelected();
                Account account = new Savings(profile, amountDepo, isLoy);
                accountDatabase.open(account);
            } else if (accType.equals("Money Market")){
                Account account = new MoneyMarket(profile, amountDepo);
                accountDatabase.open(account);
            } else if (accType.equals("Checking")) {
                Account account = new Checking(profile, amountDepo);
                accountDatabase.open(account);
            }else if (accType.equals("College Checking")) {
                int CC = 0;
                if (nbButton.isSelected()) {
                    CC = 0;
                } else if (newarkButton.isSelected()) {
                    CC = 1;
                } else if (caButton.isSelected()) {
                    CC = 2;
                }
                Account account = new CollegeChecking(profile, amountDepo, CC);
                accountDatabase.open(account);
            }
        }else if (open_close.getSelectedToggle().equals(closeButton)){
            if (accType.equals("Savings")){
                accountDatabase.close(new Savings(profile));
            } else if (accType.equals("Money Market")) {
                accountDatabase.close(new MoneyMarket(profile));
            } else if (accType.equals("Checking")) {
            accountDatabase.close(new Checking(profile));
            }else if (accType.equals("College Checking")) {
                accountDatabase.close(new CollegeChecking(profile));
            }
        }
    }

    }


