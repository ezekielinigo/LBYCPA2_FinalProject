package com.shopapp.finalproject;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class ProductDetailController extends BaseController {

    @FXML
    Label label;


    public void setup(Product product) {
        label.setText(product.getName());
    }

    @FXML
    public void gotoPrevious() {
        super.gotoPrevious((Stage) label.getScene().getWindow());
    }
}
