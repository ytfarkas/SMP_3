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
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.TextArea;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.Pane;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.util.StringTokenizer;

/**
 * TransactionManagerContoller is our controller class for the GUI
 * This file declares all of our FXML containers, objects, and fields needed to create the GUI
 * It included ActionEvent classes that control the events run when triggered
 *
 * @author Yehuda Farkas, David Rahabi
 */
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
    private TextArea console;


    /**
     * The Initialize class initializes all the GUI functions on program launch
     */
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
        addTextFieldListeners();
        addToggleButtonListeners();
        console.setText("Transaction Manager running...");
    }

    /**
     * addToggleButtonListeners adds listeners to the toggle buttons, allowing the program to determine which buttons were switched
     */
    @FXML
    void addToggleButtonListeners(){
        openButton.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                confirm.setDisable(true);
                clearOpen();
            }
        });
        closeButton.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                confirm.setDisable(true);
                clearClose();
            }
        });
        depositButton.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                confirm.setDisable(true);
                clearDW();
            }
        });
        withdrawButton.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                confirm.setDisable(true);
                clearDW();
            }
        });
    }


    /**
     * addTextFieldListeners adds listeners to the text field
     */
    @FXML
    void addTextFieldListeners(){
        firstName.textProperty().addListener((observable, oldValue, newValue) -> checkOCTextFieldCompletion());
        lastName.textProperty().addListener((observable, oldValue, newValue) -> checkOCTextFieldCompletion());
        dateOfBirth.valueProperty().addListener((observable, oldValue, newValue) -> checkOCTextFieldCompletion());
        accountType.valueProperty().addListener((observable, oldValue, newValue) -> checkOCTextFieldCompletion());
        amount.textProperty().addListener((observable, oldValue, newValue) -> checkOCTextFieldCompletion());
        campusLocation.selectedToggleProperty().addListener((observable, oldToggle, newToggle) -> checkOCTextFieldCompletion());
        dwFname.textProperty().addListener((observable, oldValue, newValue) -> checkDWTextFieldCompletion());
        dwLname.textProperty().addListener((observable, oldValue, newValue) -> checkDWTextFieldCompletion());
        dwDOB.valueProperty().addListener((observable, oldValue, newValue) -> checkDWTextFieldCompletion());
        dwAccountType.valueProperty().addListener((observable, oldValue, newValue) -> checkDWTextFieldCompletion());
        dwAmount.textProperty().addListener((observable, oldValue, newValue) -> checkDWTextFieldCompletion());

    }


    /**
     * checkOCTextFieldCompletion Checks if all the text fields are completed in Open and Close
     * if fields are completed, set confirm disabled to false
     */
    @FXML
    void checkOCTextFieldCompletion() {
        boolean isComplete = false;
        if (openButton.isSelected()) {
            if (!firstName.getText().trim().isEmpty() && !lastName.getText().trim().isEmpty() && dateOfBirth.getValue() != null &&
                    accountType.getValue() != null && !amount.getText().isEmpty()) {
                if (accountType.getValue().equals("College Checking")) {
                    boolean campusSelected = false;
                    for (Toggle radioButton : campusLocation.getToggles()) {
                        if (radioButton.isSelected()) {
                            isComplete = true;
                            break;
                        }
                    }
                }
                else {
                    isComplete = true;
                }
            }
        }
        if (closeButton.isSelected()) {
            if (!firstName.getText().isEmpty() && !lastName.getText().isEmpty() && dateOfBirth.getValue() != null &&
                    accountType.getValue() != null) {
                isComplete = true;
            }
        }
        if (isComplete) {
            confirm.setDisable(false);
        }
        else{
            confirm.setDisable(true);
        }
    }

    /**
     * Check if the Deposit and withdraw fields are completed
     * if so, allow the set disable confirm button to false.
     */
    @FXML
    void checkDWTextFieldCompletion(){
        boolean isComplete = false;
        if (!dwFname.getText().isEmpty() && !dwLname.getText().isEmpty() && dwDOB.getValue() != null
                && dwAccountType != null && !dwAmount.getText().isEmpty()) {
                isComplete = true;
        }

        if (isComplete) {
            dwConfirm.setDisable(false);
        }
        else{
            dwConfirm.setDisable(true);
        }
    }

    /**
     * This function determined with toggle button is selected (Open or close)
     * @param event event
     */
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

    /**
     * This function checks which account is selected
     * @param event event
     */
    @FXML
    void checkAccount(ActionEvent event) {
        if (accountType.getSelectionModel().getSelectedItem() != null) {
            if (accountType.getSelectionModel().getSelectedItem().equals("Savings")) {
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


    /**
     * This function clears all fields in the open section
     */
    @FXML
    void clearOpen() {
        firstName.clear();
        lastName.clear();
        dateOfBirth.setValue(null);
        accountType.getSelectionModel().clearSelection();
        isLoyal.setSelected(false);
        campusLocation.selectToggle(null);
        amount.clear();
    }

    /**
     * This function clears all fields in the close section
     */
    @FXML
    void clearClose() {
        firstName.clear();
        lastName.clear();
        dateOfBirth.setValue(null);
        accountType.getSelectionModel().clearSelection();

    }

    /**
     * This function clears all fields in the deposit/withdraw section
     */
    @FXML
    void clearDW() {
        dwFname.clear();
        dwLname.clear();
        dwDOB.setValue(null);
        dwAccountType.getSelectionModel().clearSelection();
        dwAmount.clear();
    }


    /**
     * This function clears all fields in open/close
     * @param event event
     */
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


    /**
     * This field activates when the confirm button is pressed in open/close
     * @param event event
     */
    @FXML
    void confirmField(ActionEvent event) {
            Profile profile = new Profile(firstName.getText().trim(), lastName.getText().trim(), new Date(String.valueOf(dateOfBirth.getValue())));
            String accType = (String) accountType.getValue();
            if (open_close.getSelectedToggle().equals(openButton)) {
                openHandler(profile, accType);
                //blank data fields, null pointers
            } else if (open_close.getSelectedToggle().equals(closeButton)) {
                closeHandler(profile, accType);
            }
    }

    /**
     * This function creates new accounts according to the inputs
     * @param profile profile
     * @param accType accType
     */
    @FXML
    void openHandler(Profile profile, String accType){
        try{
            Double.parseDouble(amount.getText());
        } catch (NumberFormatException n){
            console.setText(console.getText() + "\n" + "Not a valid amount.");
            return;
        }
        double amountDepo = Double.parseDouble(amount.getText());
        if (accType.equals("Savings")) {
            boolean isLoy = isLoyal.isSelected();
            console.setText(console.getText() + "\n" + accountDatabase.validOpen(new Savings(profile, amountDepo, isLoy)));
            accountDatabase.open(new Savings(profile, amountDepo, isLoy));
        } else if (accType.equals("Money Market")) {
            console.setText(console.getText() + "\n" + accountDatabase.validOpen(new MoneyMarket(profile, amountDepo)));
            accountDatabase.open(new MoneyMarket(profile, amountDepo));
        } else if (accType.equals("Checking")) {
            console.setText(console.getText() + "\n" +accountDatabase.validOpen(new Checking(profile, amountDepo)));
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
            console.setText(console.getText() + "\n" + accountDatabase.validOpen(new CollegeChecking(profile, amountDepo, CC)));
            accountDatabase.open(new CollegeChecking(profile, amountDepo, CC));
        }
    }

    /**
     * This function handles all close functions of all accounts
     * @param profile profile
     * @param accType account Type
     */
    @FXML
    void closeHandler(Profile profile, String accType){
        Account account;
        if (accType.equals("Savings")) {
            account = new Savings(profile);
            if(accountDatabase.close(account)){
                console.setText(console.getText() + "\n" + profile.toString() + account.printType() + " has been closed.");
            }
            else{
                printCloseErrors(account);
            }
        } else if (accType.equals("Money Market")) {
            account = new MoneyMarket(profile);
            if(accountDatabase.close(account)){
                console.setText(console.getText() + "\n" + profile.toString() + account.printType() + " has been closed.");
            }
            else{
                printCloseErrors(account);
            }
        } else if (accType.equals("Checking")) {
            account = new Checking(profile);
            if(accountDatabase.close(account)){
                console.setText(console.getText() + "\n" + profile.toString() + account.printType() + " has been closed.");
            }
            else{
                printCloseErrors(account);
            }
        } else if (accType.equals("College Checking")) {
            account = new CollegeChecking(profile);
            if(accountDatabase.close(account)){
                console.setText(console.getText() + "\n" + profile.toString() + account.printType() + " has been closed.");
            }
            else{
                printCloseErrors(account);
            }
        }
    }

    /**
     * This function prints all errors durring the close functions
     * @param account account
     */
    @FXML
    void printCloseErrors(Account account){
        if(account.holder.getDOB().checkLeap() != null){
            console.setText(console.getText() + "\n" + account.holder.getDOB().checkLeap());
        }
        else if(account.holder.getDOB().checkDate(account.holder.getDOB().getMonth(), account.holder.getDOB().getDay(), account.holder.getDOB().getYear()) != null){
            console.setText(console.getText() + "\n" + account.holder.getDOB().checkDate(account.holder.getDOB().getMonth(), account.holder.getDOB().getDay(), account.holder.getDOB().getYear()));
        }
        else{
            console.setText(console.getText() + "\n" + accountDatabase.isInDatabase(account));
        }
    }


    /**
     * This function determined with toggle button is selected (Deposit or withdraw)
     * @param event event
     */
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


    /**
     * This function clears all fields in deposit withdraw
     * @param event event
     */
    @FXML
    void dwclearField(ActionEvent event) { //accountType.setprompt needs to be fixed
        dwFname.clear();
        dwLname.clear();
        dwDOB.setValue(null);
        dwAccountType.getSelectionModel().clearSelection();
        dwAmount.clear();
    }


    /**
     * This field activates when the confirm button is pressed in Deposit/Withdraw
     * @param event event
     */
    @FXML
    void dwConfirm(ActionEvent event) {
        Profile profile = new Profile(dwFname.getText().trim(),dwLname.getText().trim(),  new Date(String.valueOf(dwDOB.getValue())));
        String accType = (String) dwAccountType.getValue();
        try{
            Double.parseDouble(dwAmount.getText());
        } catch(NumberFormatException n){
            console.setText(console.getText() + "\n" + "Not a valid amount.");
            return;
        }
        double amountDepoWith = Double.parseDouble(dwAmount.getText());
        Account account = null;
        if (depo_with.getSelectedToggle().equals(depositButton)) {
            if (accType.equals("Savings")) {
                account = new Savings(profile, amountDepoWith);
            } else if (accType.equals("Money Market")) {
                 account = new MoneyMarket(profile, amountDepoWith);
            } else if (accType.equals("Checking")) {
                 account = new Checking(profile, amountDepoWith);
            } else if (accType.equals("College Checking")) {
                 account = new CollegeChecking(profile, amountDepoWith);
            }
            depositHandler(account);
        } else if (depo_with.getSelectedToggle().equals(withdrawButton)) {
            if (accType.equals("Savings")) {
                account = new Savings(profile, amountDepoWith);
            } else if (accType.equals("Money Market")) {
                account = new MoneyMarket(profile, amountDepoWith);
            } else if (accType.equals("Checking")) {
                account = new Checking(profile, amountDepoWith);
            } else if (accType.equals("College Checking")) {
                account = new CollegeChecking(profile, amountDepoWith);
                accountDatabase.withdraw(account);
            }
            if(accountDatabase.withdraw(account)){
                console.setText(console.getText() + "\n" + account.holder.toString() + account.printType() + " Withdraw - balance updated.");
            }
            else{
                printWithdrawErrors(account);
            }
        }
    }


    /**
     * This function handle all account errors
     * @param account account
     */
    @FXML
    void depositHandler(Account account){
        if (account.balance <= 0) {
            console.setText(console.getText() + "\n" +"Deposit - amount cannot be 0 or negative.");
        } else if (account.holder.getDOB().isValid() && accountDatabase.isInDatabase(account) == null){
            console.setText(console.getText() + "\n" + account.holder.toString() + account.printType() + " Deposit - balance updated.");
            accountDatabase.deposit(account);
        } else{
            if(account.holder.getDOB().checkLeap() != null){
                console.setText(console.getText() + "\n" + account.holder.getDOB().checkLeap());
            }
            else if(account.holder.getDOB().checkDate(account.holder.getDOB().getMonth(), account.holder.getDOB().getDay(), account.holder.getDOB().getYear()) != null){
                console.setText(console.getText() + "\n" + account.holder.getDOB().checkDate(account.holder.getDOB().getMonth(), account.holder.getDOB().getDay(), account.holder.getDOB().getYear()));
            }
            else{
                console.setText(console.getText() + "\n" + accountDatabase.isInDatabase(account));
            }
        }
    }


    /**
     * This function prints all withdraw errors
     * @param account Account
     */
    @FXML
    void printWithdrawErrors(Account account){
        if(account.balance <= 0) {
            console.setText(console.getText() + "\n" + "Withdraw - amount cannot be 0 or negative.");
        }
        else if(account.holder.getDOB().checkLeap() != null){
            console.setText(console.getText() + "\n" + account.holder.getDOB().checkLeap());
        }
        else if(account.holder.getDOB().checkDate(account.holder.getDOB().getMonth(), account.holder.getDOB().getDay(), account.holder.getDOB().getYear()) != null){
            console.setText(console.getText() + "\n" + account.holder.getDOB().checkDate(account.holder.getDOB().getMonth(), account.holder.getDOB().getDay(), account.holder.getDOB().getYear()));
        }
        else if(accountDatabase.isInDatabase(account) != null){
            console.setText(console.getText() + "\n" + accountDatabase.isInDatabase(account));
        }
        else{
            console.setText(console.getText() + "\n" + account.holder.toString() + account.printType() + " Withdraw - insufficient fund.");
        }
    }

    /**
     * This function prints all accounts
     * @param event event
     */
    @FXML
    void printAccount(ActionEvent event) {
        console.setText(console.getText() + "\n" + accountDatabase.printSorted());

    }

    /**
     * this function prints all interest and fees
     * @param event event
     */
    @FXML
    void printInterestAndFees(ActionEvent event) {
        console.setText(console.getText() + "\n" + accountDatabase.printFeesAndInterests());

    }

    /**
     * This function prints all updated balances
     * @param event event
     */
    @FXML
    void printUpdatedBalances(ActionEvent event) {
        console.setText(console.getText() + "\n" +accountDatabase.printUpdatedBalances());
    }

    /**
     * This function loads the file and processes all commands on the file
     * @param event events
     */
    @FXML
    void loadFile(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Load Account From File");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text Files","*.txt"));
        File file = fileChooser.showOpenDialog(new Stage());
        if (file != null){
            try (BufferedReader lineReader = new BufferedReader(new FileReader(file))) {
                StringBuilder stringLine = new StringBuilder();
                String readLine;
                while ((readLine = lineReader.readLine()) != null) {
                    if (!readLine.isEmpty()) {
                        String[] split = readLine.split(",");
                        String account = split[0];
                        String[] splitD = split[3].split("/");
                        Date DOB = new Date(Integer.parseInt(splitD[0]), Integer.parseInt(splitD[1]), Integer.parseInt(splitD[2]));
                        Profile profile = new Profile(split[1], split[2], DOB);
                        double amount = Double.parseDouble(split[4]);
                        if (account.equals("C")) {
                            console.setText(console.getText() + "\n" + accountDatabase.validOpen(new Checking(profile, amount)));
                            accountDatabase.open(new Checking(profile, amount));
                        } else if (account.equals("CC")) {
                            console.setText(console.getText() + "\n" + accountDatabase.validOpen(new CollegeChecking(profile, amount, Integer.parseInt(split[5]))));
                            accountDatabase.open(new CollegeChecking(profile, amount, Integer.parseInt(split[5])));
                        } else if (account.equals("S")) {
                            console.setText(console.getText() + "\n" + accountDatabase.validOpen(new Savings(profile, amount, Boolean.parseBoolean(split[5]))));
                            accountDatabase.open(new Savings(profile, amount, Boolean.parseBoolean(split[5])));
                        } else if (account.equals("MM")) {
                            console.setText(console.getText() + "\n" + accountDatabase.validOpen(new MoneyMarket(profile, amount)));
                            accountDatabase.open(new MoneyMarket(profile, amount));
                        }
                    }
                }
            }
            catch (IOException e) {
                e.printStackTrace();
            }
            }
        }
    }
