package com.shopapp.finalproject;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class ProductDetailController extends BaseController{

    Product product;
    Seller seller;
    int amount;
    int stock;
    float price;

    public void setup(Product product) {
        this.product = product;
        this.seller = product.getSeller();
        this.amount = 1;
        this.stock = product.getStock();
        this.price = product.getPrice();

        displayName.setText(product.getName());
        displayPrice.setText(String.format("P %,.2f", price));
        displayImage.setImage(product.getImage());
        displayDescription.setText(product.getName()+"\n\n"+product.getDescription());
        displaySeller.setText(seller.getName());
        displayStock.setText("stock: "+stock);

        displayAmount.textProperty().addListener((observable, oldValue, newValue) ->{
            editAmount(new ActionEvent());
        });
    }

    @FXML
    private TextField searchBar;

    @FXML
    private TextField displayAmount;

    @FXML
    private TextArea displayDescription;

    @FXML
    private Label displayName;

    @FXML
    private Label displayPrice;

    @FXML
    private Label displayRemark;

    @FXML
    private Button displaySeller;

    @FXML
    private Label displayStock;

    @FXML
    private Button minusButton;

    @FXML
    private Button plusButton;
    @FXML
    private ImageView displayImage;

    @FXML
    void addtoCart(ActionEvent event) {
        try {
            // the format of displayStock is "stock: 10"
            // int stock should only get "10"
            int stock = Integer.parseInt(displayStock.getText().substring(7));
            if (stock >= Integer.parseInt(displayAmount.getText())) {
                displayRemark.setText("Successfully added to cart!");
                GlobalData.getInstance().addToCart(displayName.getText(), Integer.parseInt(displayAmount.getText()));
            }else{
                displayRemark.setText("Please enter a valid amount");
            }

        }catch (Exception e) {
            displayRemark.setText("Please enter a valid amount");
        }
    }

    @FXML
    void editAmount(ActionEvent event) {
        // plus, minus, and textfield uses the same method
        try {
            if (event.getSource() == plusButton && amount < stock)
                amount++;
            else if (event.getSource() == minusButton && amount > 1)
                amount--;
            else {
                if (Integer.parseInt(displayAmount.getText()) > stock)
                    amount = stock;
                else if (Integer.parseInt(displayAmount.getText()) < 1)
                    amount = 1;
                else
                    amount = Integer.parseInt(displayAmount.getText());
            }
            displayAmount.setText(String.valueOf(amount));
            displayRemark.setText("");
        }catch (Exception e) {
            displayAmount.setText("1");
        }
    }

    @FXML
    void gotoCart() {
        super.gotoCart((Stage) searchBar.getScene().getWindow(), "product", displayName.getText());
    }

    @FXML
    void gotoHistoryScreen() {
        super.gotoHistory((Stage) searchBar.getScene().getWindow(), "product", displayName.getText());
    }

    @FXML
    public void gotoPreviousScreen() {
        super.gotoPrevious((Stage) searchBar.getScene().getWindow());
    }

    @FXML
    void gotoSearchResults() {
        super.gotoSearchScreen((Stage) searchBar.getScene().getWindow(), "home", null, searchBar.getText());
    }

    @FXML
    void gotoSeller(ActionEvent event) {
        Seller seller = GlobalData.getInstance().getGlobalSeller(displaySeller.getText());
        super.gotoSellerDetail(seller, (Stage) searchBar.getScene().getWindow(), "product", displayName.getText());
    }

}
