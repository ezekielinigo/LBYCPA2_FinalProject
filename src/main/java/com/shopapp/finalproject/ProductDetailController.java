package com.shopapp.finalproject;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class ProductDetailController {

    @FXML
    Label label;

    public void setProduct(Product product) {
        label.setText(product.getName());
    }
}
