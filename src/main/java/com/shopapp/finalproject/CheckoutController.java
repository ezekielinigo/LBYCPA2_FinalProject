package com.shopapp.finalproject;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.util.ArrayList;

public class CheckoutController{

    @FXML
    private Button confirmButton;


    @FXML
    void confirmBilling(ActionEvent event) {
        Stage currentStage = (Stage) confirmButton.getScene().getWindow();
        currentStage.close();
    }

}
