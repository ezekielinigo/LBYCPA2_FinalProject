package com.shopapp.finalproject;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class CartThumbnailViewController extends CartController{

    Product product;
    float price;
    Seller seller;
    int amount;
    int stock;


    public void setup(Product product, int count) {
        this.product = product;
        this.price = product.getPrice();
        this.seller = product.getSeller();
        this.stock = product.getStock();
        this.amount = count;

        displayAmount.setText(String.valueOf(amount));
        displayName.setText(product.getName());
        displayPrice.setText(String.format("P %,.2f", price));
        displayStock.setText("Stock: "+stock);
        displayTotal.setText(String.format("TOTAL: P %,.2f", amount * price));
        image.setImage(product.getImage());

        displayAmount.textProperty().addListener((observable, oldValue, newValue) ->{
            editAmount(new ActionEvent());
        });

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
            if (event.getSource() == plusButton && amount < product.getStock())
                amount++;
            else if (event.getSource() == minusButton && amount > 1)
                amount--;
            else{
                if (Integer.parseInt(displayAmount.getText()) > product.getStock())
                    amount = stock;
                else if (Integer.parseInt(displayAmount.getText()) < 1)
                    amount = 1;
                else
                    amount = Integer.parseInt(displayAmount.getText());
            }
            displayAmount.setText(String.valueOf(amount));
            displayTotal.setText(String.format("TOTAL: P %,.2f", amount * price));
        } catch (Exception e) {
            displayAmount.setText("1");
        }
    }

    private boolean isNumeric(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    @FXML
    void gotoSeller() {
        super.gotoSellerDetail(seller, (Stage) image.getScene().getWindow(), "cart", null);
    }

    @FXML
    void removefromCart() {

    }

    @FXML
    void gotoProduct() {
        super.gotoProductDetail(product, (Stage) image.getScene().getWindow(), "cart", null);
    }

}
