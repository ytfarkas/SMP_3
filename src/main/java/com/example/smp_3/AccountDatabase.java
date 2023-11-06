package com.example.smp_3;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.TextArea;
import javafx.event.ActionEvent;
import javafx.scene.control.*;


/**
 * The AccountDatabase class contains all the necessary methods for executing the commands that are read from TransactionManager.
 *
 * @author David Rahabi, Judah Farkas
 */
public class AccountDatabase {
    private Account[] accounts; //list of various types of accounts
    private int numAcct; //number of accounts in the array

    /**
     * Constructor that creates an AccountDatabase object.
     * Initializes size of accounts to 4 and numAcct to 0
     */
    public AccountDatabase() {
        this.accounts = new Account[4];
        this.numAcct = 0;
    }


    /**
     * Searches for the account in the accounts array.
     *
     * @param account The account to be found
     * @return i, the index of the account in accounts if found, -1 if not found
     */
    private int find(Account account) { //search for an account in the array
        for (int i = 0; i < numAcct; i++) {
            if (accounts[i].compareTo(account) == 0) {
                return i; // return 1 or the place in the array?
            }
        }
        return -1; //Not found
    }

    /**
     * Increases the size of accounts by 4
     */
    private void grow() { //increase the capacity by 4
        Account[] newAccounts = new Account[numAcct + 4];
        for (int i = 0; i < numAcct; i++) {
            newAccounts[i] = accounts[i];
        }
        accounts = newAccounts;
    }

    /**
     * Searches for the account in the accounts array
     *
     * @param account the account to be found
     * @return true if account found, false if not found
     */
    public boolean contains(Account account) {
        for (int i = 0; i < numAcct; i++) {
            if (account.compareTo(accounts[i]) == 0) {
                return true; // Account is in the array
            }
        }
        return false; //account is not in the array

    } //overload if necessary

    /**
     * Checks if the account holder already has a checking or college checking account.
     * Used to prevent one person from opening both a checking and college checking account
     *
     * @param account the account to be checked
     * @return true if the holder has a checking or college checking account, false if not
     */
    public boolean hasChecking(Account account) {
        for (int i = 0; i < numAcct; i++) {
            if (accounts[i].holder.compareTo(account.holder) == 0 && (accounts[i].printType().equals("(CC)") || accounts[i].printType().equals("(C)"))) {
                return true;
            }
        }
        return false; //account is not in the array
    }

    /**
     * Adds new account to the accounts array if the account is valid.
     *
     * @param account the account to be opened
     * @return true if the account is opened successfully, false if not
     */
    public boolean open(Account account) {
        if (validOpen(account).equals(account.holder.toString() + account.printType() + " opened.")) {
            if (numAcct == accounts.length) {
                grow();
            }
            accounts[numAcct] = account;
            numAcct++;
            return true;
        }
        return false;
    } //add a new account

    /**
     * Checks if the account to be opened is valid and prints the correct error if not.
     *
     * @param account the account to be checked
     * @return true if valid, false if not
     */
    public String validOpen(Account account) {
        if (account.balance <= 0) {
            return "Initial deposit cannot be 0 or negative.";
        }
        if (contains(account)) {

            return account.holder.toString() + account.printType() + " is already in the database.";
        }
        if (account.printType().equals("(CC)")) {
            CollegeChecking checking = (CollegeChecking) account;
            try {
                boolean checkCampCode = !checking.getCampus().isValid();
            } catch (NullPointerException e) {
                return "Invalid campus code.";
            }
        }
        if ((account.printType().equals("(CC)") || account.printType().equals("(C)")) && hasChecking(account)) {
            return account.holder.toString() + account.printType() + " is already in the database.";
        }
        if (!account.holder.getDOB().isValid()) {
            if(account.holder.getDOB().checkLeap() != null){
                return(account.holder.getDOB().checkLeap());
            }
            if(account.holder.getDOB().checkDate(account.holder.getDOB().getMonth(), account.holder.getDOB().getDay(), account.holder.getDOB().getYear()) != null){
                return account.holder.getDOB().checkDate(account.holder.getDOB().getMonth(), account.holder.getDOB().getDay(), account.holder.getDOB().getYear());
            }
        }
        if (account.holder.getDOB().getAge() < 16) {
            return "DOB invalid: " + account.holder.getDOB().toString() + " under 16.";
        }
        if ((account.printType().equals("(CC)") && account.holder.getDOB().getAge() >= 24)) {
            return "DOB invalid: " + account.holder.getDOB().toString() + " over 24.";
        }
        if (account.printType().equals("(MM)") && account.balance < 2000) {
            return "Minimum of $2000 to open a Money Market account.";
        }
        return account.holder.toString() + account.printType() + " opened.";
    }




    /**
     * Removes the account from the database if inputted account is in the database.
     *
     * @param account the account to be removed
     * @return true if the account was successfully closed, false if not
     */
    public boolean close(Account account) {
        if (account.holder.getDOB().isValid() && isInDatabase(account) == null) {
            Account[] newAccounts = new Account[accounts.length];
            int count = 0;
            for (int i = 0; i < numAcct; i++) {
                if (account.compareTo(accounts[i]) == 0) {
                    continue;
                }
                newAccounts[count] = accounts[i];
                count++;
            }
            accounts = newAccounts;
            numAcct--;
            //System.out.println(account.holder.toString() + account.printType() + " has been closed.");
            return true;
        }
        return false;
    } //remove the given account

    /**
     * Checks if the account exists in the database and prints error if not found.
     *
     * @param account the account to search
     * @return true if in the database, false if not
     */
    public String isInDatabase(Account account) {
        if (!contains(account)) {
            return account.holder.toString() + account.printType() + " is not in the database.";
        }
        return null;
    }

    /**
     * Withdraws money from the account if the amount is valid.
     *
     * @param account the account to withdraw from
     * @return true if withdraw completed, false if not
     */
    public boolean withdraw(Account account) {
        //need check for if amount entered is not a number in transactionmanager i think

        if (account.balance <= 0) {
            //System.out.println("Withdraw - amount cannot be 0 or negative.");
            return false;
        }

        if (!account.holder.getDOB().isValid()) {
            return false;
        }

        if (isInDatabase(account) == null) {
            if (accounts[find(account)].balance > account.balance) {
                accounts[find(account)].balance -= account.balance;
                if (accounts[find(account)].printType().equals("(MM)")) {
                    updateWithdrawalsAndLoyalty(account);
                }
               // System.out.println(account.holder.toString() + account.printType() + " Withdraw - balance updated.");
                return true;
            }
            //System.out.println(account.holder.toString() + account.printType() + " Withdraw - insufficient fund.");
            return false;
        }
        return false;
    } //false if insufficient fund

    /**
     * Updates the Withdrawal and loyalty in MoneyMarket account
     *
     * @param account account
     */
    public void updateWithdrawalsAndLoyalty(Account account) {
        MoneyMarket update = (MoneyMarket) accounts[find(account)];
        update.updateWithdrawal();
        update.updateLoyalty();
        accounts[find(account)] = update;

    }

    /**
     * Updates loyalty in the MoneyMarket account
     *
     * @param account account
     */
    public void updateLoyalty(Account account) {
        MoneyMarket update = (MoneyMarket) accounts[find(account)];
        update.updateLoyalty();
        accounts[find(account)] = update;
    }

    /**
     * Deposits money into the account if the deposit is valid.
     *
     * @param account the account to add money to
     */
    public void deposit(Account account) {
        //need check for if amount entered is not a number in transactionmanager i think
        if (account.balance <= 0) {
        //    System.out.println("Deposit - amount cannot be 0 or negative.");
        } else if (account.holder.getDOB().isValid() && isInDatabase(account) == null) {
            accounts[find(account)].balance += account.balance;
            if (accounts[find(account)].printType().equals("(MM)")) {
                updateLoyalty(account);
            }
           // System.out.println(account.holder.toString() + account.printType() + " Deposit - balance updated.");
        }
    }

    /**
     * Sorts and prints the accounts array by account type, name, and date of birth.
     */
    public String printSorted() {
        String printAccounts = "";
        if (numAcct == 0) {
            return "Account Database is empty!";
        } else {
            printAccounts = printAccounts + "*Accounts sorted by account type and profile.\n";
            Account[] sortedChecking = sortChecking();
            Account[] sortedCollege = sortCollege();
            Account[] sortedMoneyMarket = sortMoneyMarket();
            Account[] sortedSavings = sortSavings();
            int count = 0;
            for (int i = 0; i < sortedChecking.length; i++) {
                printAccounts = printAccounts + sortedChecking[i].toString() + "\n";
                accounts[count] = sortedChecking[i];
                count++;
            }
            for (int i = 0; i < sortedCollege.length; i++) {
                printAccounts = printAccounts + sortedCollege[i].toString() + "\n";
                accounts[count] = sortedCollege[i];
                count++;
            }
            for (int i = 0; i < sortedMoneyMarket.length; i++) {
                printAccounts = printAccounts + sortedMoneyMarket[i].toString() + "\n";
                accounts[count] = sortedMoneyMarket[i];
                count++;
            }
            for (int i = 0; i < sortedSavings.length; i++) {
                printAccounts = printAccounts + sortedSavings[i].toString() + "\n";
                accounts[count] = sortedSavings[i];
                count++;
            }

            printAccounts = printAccounts + "*end of list.";
        }
        return printAccounts;
    } //sort by account type and profile

    /**
     * resorts the accounts array.
     */
    public void resortAccounts() {
        Account[] sortedChecking = sortChecking();
        Account[] sortedCollege = sortCollege();
        Account[] sortedMoneyMarket = sortMoneyMarket();
        Account[] sortedSavings = sortSavings();
        int count = 0;
        for (int i = 0; i < sortedChecking.length; i++) {
            accounts[count] = sortedChecking[i];
            count++;
        }
        for (int i = 0; i < sortedCollege.length; i++) {
            accounts[count] = sortedCollege[i];
            count++;
        }
        for (int i = 0; i < sortedMoneyMarket.length; i++) {
            accounts[count] = sortedMoneyMarket[i];
            count++;
        }
        for (int i = 0; i < sortedSavings.length; i++) {
            accounts[count] = sortedSavings[i];
            count++;
        }
    }

    /**
     * Finds all the Checking accounts in the database and sorts them.
     *
     * @return an account array that contains only the sorted checking accounts
     */
    public Account[] sortChecking() {
        Account[] newAccounts = new Account[numAcct];
        int count = 0;
        for (int i = 0; i < numAcct; i++) {
            if (accounts[i].printType().equals("(C)")) {
                newAccounts[count] = accounts[i];
                count++;
            }
        }
        for (int i = 0; i < count - 1; i++) {
            for (int j = 0; j < count - i - 1; j++) {
                if ((newAccounts[j].holder.compareTo(newAccounts[j + 1].holder) > 0)) {
                    Account temp = newAccounts[j];
                    newAccounts[j] = newAccounts[j + 1];
                    newAccounts[j + 1] = temp;
                }
            }
        }
        Account[] noNullAccounts = new Account[count];
        for (int i = 0; i < count; i++) {
            noNullAccounts[i] = newAccounts[i];
        }

        return noNullAccounts;
    }

    /**
     * Finds all the College Checking accounts in the array and sorts them.
     *
     * @return an account array containing only the sorted College Checking accounts
     */
    public Account[] sortCollege() {
        Account[] newAccounts = new Account[numAcct];
        int count = 0;
        for (int i = 0; i < numAcct; i++) {
            if (accounts[i].printType().equals("(CC)")) {
                newAccounts[count] = accounts[i];
                count++;
            }
        }
        for (int i = 0; i < count - 1; i++) {
            for (int j = 0; j < count - i - 1; j++) {
                if ((newAccounts[j].holder.compareTo(newAccounts[j + 1].holder) > 0)) {
                    Account temp = newAccounts[j];
                    newAccounts[j] = newAccounts[j + 1];
                    newAccounts[j + 1] = temp;
                }
            }
        }
        Account[] noNullAccounts = new Account[count];
        for (int i = 0; i < count; i++) {
            noNullAccounts[i] = newAccounts[i];
        }

        return noNullAccounts;
    }

    /**
     * Finds all the MoneyMarket accounts and sorts them.
     *
     * @return an account array containing only the sorted MoneyMarket accounts
     */
    public Account[] sortMoneyMarket() {
        Account[] newAccounts = new Account[numAcct];
        int count = 0;
        for (int i = 0; i < numAcct; i++) {
            if (accounts[i].printType().equals("(MM)")) {
                newAccounts[count] = accounts[i];
                count++;
            }
        }
        for (int i = 0; i < count - 1; i++) {
            for (int j = 0; j < count - i - 1; j++) {
                if ((newAccounts[j].holder.compareTo(newAccounts[j + 1].holder) > 0)) {
                    Account temp = newAccounts[j];
                    newAccounts[j] = newAccounts[j + 1];
                    newAccounts[j + 1] = temp;
                }
            }
        }
        Account[] noNullAccounts = new Account[count];
        for (int i = 0; i < count; i++) {
            noNullAccounts[i] = newAccounts[i];
        }
        return noNullAccounts;
    }

    /**
     * Finds all the savings accounts and sorts them.
     *
     * @return an accounts array containing only the sorteed savings accounts
     */
    public Account[] sortSavings() {
        Account[] newAccounts = new Account[numAcct];
        int count = 0;
        for (int i = 0; i < numAcct; i++) {
            if (accounts[i].printType().equals("(S)")) {
                newAccounts[count] = accounts[i];
                count++;
            }
        }
        for (int i = 0; i < count - 1; i++) {
            for (int j = 0; j < count - i - 1; j++) {
                if ((newAccounts[j].holder.compareTo(newAccounts[j + 1].holder) > 0)) {
                    Account temp = newAccounts[j];
                    newAccounts[j] = newAccounts[j + 1];
                    newAccounts[j + 1] = temp;
                }
            }
        }
        Account[] noNullAccounts = new Account[count];
        for (int i = 0; i < count; i++) {
            noNullAccounts[i] = newAccounts[i];
        }
        return noNullAccounts;
    }

    /**
     * Prints all the accounts in the database with their fees and interest charges.
     */
    public String printFeesAndInterests() {
        resortAccounts();
        String printAccounts="";
        if (numAcct == 0) {
            return "Account Database is empty!";
        } else {
            printAccounts = printAccounts + "*list of accounts with fee and monthly interest\n";
            for (int i = 0; i < numAcct; i++) {
                printAccounts = printAccounts + accounts[i].printWithFeesAndInterest() + "\n";
            }
            printAccounts = printAccounts + "*end of list.";
        }
        return printAccounts;
    } //calculate interests/fees


    /**
     * prints all the accounts with their balances updated based on their interest and fees.
     */
    public String printUpdatedBalances() {
        resortAccounts();
        String printAccounts="";
        if (numAcct == 0) {
            return "Account Database is empty!";
        } else {
            printAccounts = printAccounts + "*list of accounts with fees and interests applied." +"\n";
            for (int i = 0; i < numAcct; i++) {
                printAccounts = printAccounts + accounts[i].printUpdatedBalance() +"\n";
            }
            printAccounts = printAccounts + "*end of list.";
        }
        return printAccounts;
    } //apply the interests/fees
}
