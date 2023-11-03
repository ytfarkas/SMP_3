package com.example.smp_3;

import javafx.beans.binding.BooleanBinding;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
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
    private ToggleButton depositButton;
    @FXML
    private ToggleButton withdrawButton;
    @FXML
    private ToggleGroup depo_with;
    @FXML
    private TextField dwFname;
    @FXML
    private TextField dwLname;
    @FXML
    private DatePicker dwDOB;
    @FXML
    private ComboBox dwAccountType;
    @FXML
    private TextField dwAmount;
    @FXML
    private Button dwConfirm;
    @FXML
    private Button dwClear;

    @FXML
    private void initialize() {
        accountType.setItems(accountTypeList);
        accountType.setPromptText("Account Type");
        dwAccountType.setItems(accountTypeList);
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
        dwFname.setDisable(true);
        dwLname.setDisable(true);
        dwDOB.setDisable(true);
        dwAccountType.setDisable(true);
        dwAmount.setDisable(true);
    }

    @FXML
    void openCloseToggleButton(ActionEvent event) {
        if (event.getSource() == openButton) {
            firstName.setDisable(false);
            lastName.setDisable(false);
            dateOfBirth.setDisable(false);
            accountType.setDisable(false);
            amount.setDisable(false);
            campusContainer.setVisible(true);
            amount.setVisible(true);
        }
        if (event.getSource() == closeButton) {
            firstName.setDisable(false);
            lastName.setDisable(false);
            dateOfBirth.setDisable(false);
            accountType.setDisable(false);
            amount.setDisable(true);
            campusContainer.setVisible(false);
            amount.setVisible(false);
        }
        if (!openButton.isSelected() && !closeButton.isSelected()) {
            firstName.setDisable(true);
            lastName.setDisable(true);
            dateOfBirth.setDisable(true);
            accountType.setDisable(true);
            amount.setDisable(true);
        }
    }

    @FXML
    void checkAccount(ActionEvent event) {
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
    void clearField(ActionEvent event) { //accountType.setprompt needs to be fixed
        firstName.clear();
        lastName.clear();
        dateOfBirth.setValue(null);
        accountType.getSelectionModel().clearSelection();
        isLoyal.setSelected(false);
        campusLocation.selectToggle(null);
        amount.clear();
    }


    //fix Exceptions
    @FXML
    void confirmField(ActionEvent event) {
        String fName = firstName.getText().trim();
        String lName = lastName.getText().trim();
        String dateOB = String.valueOf(dateOfBirth.getValue());
        Date date = new Date(dateOB);
        Profile profile = new Profile(fName, lName, date);
        String accType = (String) accountType.getValue();
        if (open_close.getSelectedToggle().equals(openButton)) {
            int amountDepo = Integer.parseInt(amount.getText());
            if (accType.equals("Savings")) {
                boolean isLoy = isLoyal.isSelected();
                accountDatabase.open(new Savings(profile, amountDepo, isLoy));
            } else if (accType.equals("Money Market")) {
                accountDatabase.open(new MoneyMarket(profile, amountDepo));
            } else if (accType.equals("Checking")) {
                accountDatabase.open(new Checking(profile, amountDepo));
            } else if (accType.equals("College Checking")) {
                int CC = 0;
                if (nbButton.isSelected()) {
                    CC = 0;
                } else if (newarkButton.isSelected()) {
                    CC = 1;
                } else if (caButton.isSelected()) {
                    CC = 2;
                }
                accountDatabase.open(new CollegeChecking(profile, amountDepo, CC));
            }
        } else if (open_close.getSelectedToggle().equals(closeButton)) {
            if (accType.equals("Savings")) {
                accountDatabase.close(new Savings(profile));
            } else if (accType.equals("Money Market")) {
                accountDatabase.close(new MoneyMarket(profile));
            } else if (accType.equals("Checking")) {
                accountDatabase.close(new Checking(profile));
            } else if (accType.equals("College Checking")) {
                accountDatabase.close(new CollegeChecking(profile));
            }
        }
    }

    @FXML
    void depoWithToggle(ActionEvent event) {
        if (event.getSource() == depositButton) {
            dwFname.setDisable(false);
            dwLname.setDisable(false);
            dwDOB.setDisable(false);
            dwAccountType.setDisable(false);
            dwAmount.setPromptText("Deposit Amount");
            dwAmount.setDisable(false);
        }
        if (event.getSource() == withdrawButton) {
            dwFname.setDisable(false);
            dwLname.setDisable(false);
            dwDOB.setDisable(false);
            dwAccountType.setDisable(false);
            dwAmount.setPromptText("Withdraw Amount");
            dwAmount.setDisable(false);
        }
        if (!depositButton.isSelected() && !withdrawButton.isSelected()) {
            dwFname.setDisable(true);
            dwLname.setDisable(true);
            dwDOB.setDisable(true);
            dwAccountType.setDisable(true);
            dwAmount.setDisable(true);
        }
    }

    @FXML
    void dwclearField(ActionEvent event) { //accountType.setprompt needs to be fixed
        dwFname.clear();
        dwLname.clear();
        dwDOB.setValue(null);
        dwAccountType.getSelectionModel().clearSelection();
        dwAmount.clear();
    }

    @FXML
    void dwConfirm(ActionEvent event) {
        String fName = dwFname.getText().trim();
        String lName = dwLname.getText().trim();
        String dateOB = String.valueOf(dwDOB.getValue());
        Date date = new Date(dateOB);
        Profile profile = new Profile(fName, lName, date);
        String accType = (String) dwAccountType.getValue();
        int amountDepoWith = Integer.parseInt(dwAmount.getText());
        if (depo_with.getSelectedToggle().equals(depositButton)) {
            if (accType.equals("Savings")) {
                Account account = new Savings(profile, amountDepoWith);
                accountDatabase.deposit(account);
            } else if (accType.equals("Money Market")) {
                Account account = new MoneyMarket(profile, amountDepoWith);
                accountDatabase.deposit(account);
            } else if (accType.equals("Checking")) {
                Account account = new Checking(profile, amountDepoWith);
                accountDatabase.deposit(account);
            } else if (accType.equals("College Checking")) {
                Account account = new CollegeChecking(profile, amountDepoWith);
                accountDatabase.deposit(account);
            }
        } else if (depo_with.getSelectedToggle().equals(withdrawButton)) {
            if (accType.equals("Savings")) {
                accountDatabase.withdraw(new Savings(profile, amountDepoWith));
            } else if (accType.equals("Money Market")) {
                accountDatabase.withdraw(new MoneyMarket(profile, amountDepoWith));
            } else if (accType.equals("Checking")) {
                accountDatabase.withdraw(new Checking(profile, amountDepoWith));
            } else if (accType.equals("College Checking")) {
                accountDatabase.withdraw(new CollegeChecking(profile, amountDepoWith));
            }
        }


    }

    @FXML
    void printAccount(ActionEvent event) {
        accountDatabase.printSorted();
    }
    @FXML
    void printInterestAndFees(ActionEvent event) {
        accountDatabase.printFeesAndInterests();
    }
    @FXML
    void printUpdatedBalances(ActionEvent event) {
        accountDatabase.printUpdatedBalances();
    }
    @FXML
    void loadFile(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Load Account From File");
        FileChooser.ExtensionFilter ex1 = new FileChooser.ExtensionFilter("Text Files","*.txt");
        File file = fileChooser.showOpenDialog(new Stage());
        fileChooser.setInitialDirectory(new File("~/Destktop"));

    }
}




