package com.example.smp_3;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.CheckBox;

import java.net.URL;
import java.util.ResourceBundle;


public class TransactionManagerController {
        @FXML
        private ToggleButton closeButton;

        @FXML
        private ToggleButton openButton;

        @FXML
        private CheckBox isLoyal;

        @FXML
        private ToggleGroup open_close;

        @FXML
        private ComboBox<String> accountType;


        //public void initialize(URL url, ResourceBundle resourceBundle){
          //  accountType.setItems(FXCollections.observableArrayList("Checking", "College Checking", "Savings", "Money Market"));
        //}

        @FXML
        public void initilize(){
            accountType.getItems().removeAll((accountType.getItems()));
            accountType.getItems().addAll("Checking", "College Checking", "Savings", "Money Market");

        }


        @FXML
        void openCloseToggleButton(ActionEvent event) {
            if (event.getSource() == openButton){
                isLoyal.setVisible(true);
            }
           if (event.getSource() == closeButton){
                isLoyal.setVisible(false);
            }


        }

    }

