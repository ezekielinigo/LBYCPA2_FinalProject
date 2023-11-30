package com.shopapp.finalproject;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class CartThumbnailViewController extends CartController{

    public void setup(Product product, int count) {
        displayAmount.setText(""+count);
        displayName.setText(product.getName());
        displayPrice.setText(String.format("P %,.2f", product.getPrice()));
        displayStock.setText("Stock: "+product.getStock());
        displayTotal.setText(String.format("P %,.2f", Float.parseFloat(displayAmount.getText()) * count));
        image.setImage(product.getImage());
    }

    @FXML
    private TextField displayAmount;

    @FXML
    private CheckBox displayName;

    @FXML
    private Label displayPrice;

    @FXML
    private Button displaySeller;

    @FXML
    private Label displayStock;

    @FXML
    private Label displayTotal;

    @FXML
    private ImageView image;

    @FXML
    private Button plusButton;

    @FXML
    private Button minusButton;

    @FXML
    void editAmount(ActionEvent event) {
        try {
            if (event.getSource() == plusButton) {
                int amount = Integer.parseInt(displayAmount.getText());
                amount++;
                displayAmount.setText("" + amount);
            }else if (event.getSource() == minusButton) {
                int amount = Integer.parseInt(displayAmount.getText());
                amount--;
                displayAmount.setText("" + amount);
            }
            float price = Float.parseFloat(displayAmount.getText()) * Float.parseFloat(displayPrice.getText().substring(2));
            displayTotal.setText(String.format("P %,.2f", price));
        }catch (Exception e) {
            displayAmount.setText("1");
        }
    }

    @FXML
    void gotoSeller() {
        Seller seller = GlobalData.getInstance().getGlobalSeller(displayName.getText());
        super.gotoSellerDetail(seller, (Stage) image.getScene().getWindow(), "cart", null);
    }

    @FXML
    void removefromCart() {

    }

    @FXML
    void gotoProduct() {
        Product product = GlobalData.getInstance().getGlobalProduct(displayName.getText());
        super.gotoProductDetail(product, (Stage) image.getScene().getWindow(), "cart", null);
    }

}
