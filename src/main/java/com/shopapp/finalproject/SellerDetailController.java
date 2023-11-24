package com.shopapp.finalproject;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;

public class SellerDetailController {

    public void setup(Seller seller) {
        displayName.setText(seller.getName());
        displayDescription.setText(seller.getDescription());

        // handle choicebox and thumbnail display depending on selected value
        choiceBox.getItems().addAll("Products","Related Sellers");
        choiceBox.setValue("Products");
        choiceBox.setOnAction(event -> {
            String selected = choiceBox.getValue();
            displayThumbnail(selected);
        });
    }

    void displayThumbnail(String selected) {

    }

    @FXML
    private ChoiceBox<String> choiceBox = new ChoiceBox<>();

    @FXML
    private TextArea displayDescription;

    @FXML
    private Label displayName;

    @FXML
    private Label displayPrice;

    @FXML
    private GridPane productGrid;



    @FXML
    void gotoCart(MouseEvent event) {

    }

    @FXML
    void gotoHistory(MouseEvent event) {

    }

    @FXML
    void gotoPrevious(MouseEvent event) {

    }

    @FXML
    void gotoSearchResults(MouseEvent event) {

    }

}
