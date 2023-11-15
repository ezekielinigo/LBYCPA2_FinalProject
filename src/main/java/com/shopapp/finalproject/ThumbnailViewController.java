package com.shopapp.finalproject;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

public class ThumbnailViewController {

    @FXML
    private ImageView image;

    @FXML
    private Label nameLabel;

    @FXML
    private Label priceLabel;

    public void setProduct(Product product) {
        image.setImage(product.getImage(0));
        nameLabel.setText(product.getName());
        priceLabel.setText(String.format("P %.2f", product.getPrice()));
    }

}
