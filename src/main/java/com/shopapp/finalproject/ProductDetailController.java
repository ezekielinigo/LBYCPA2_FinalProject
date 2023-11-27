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

    public void setup(Product product) {
        displayName.setText(product.getName());
        displayPrice.setText(String.format("P %,.2f", product.getPrice()));
        displayImage.setImage(product.getImage());
        displayDescription.setText(product.getName()+"\n\n"+product.getDescription());
        displaySeller.setText(product.getSeller().getName());
        displayStock.setText("stock: "+String.valueOf(product.getStock()));
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
            }else{
                displayRemark.setText("Please enter a valid amount");
            }

        }catch (Exception e) {
            displayRemark.setText("Please enter a valid amount");
        }
    }

    @FXML
    void editAmount(ActionEvent event) {
        // plus and minus button uses the same method
        try {
            if (event.getSource() == plusButton) {
                displayAmount.setText(String.valueOf(Integer.parseInt(displayAmount.getText()) + 1));
            } else if (event.getSource() == minusButton) {
                if (Integer.parseInt(displayAmount.getText()) > 1)
                    displayAmount.setText(String.valueOf(Integer.parseInt(displayAmount.getText()) - 1));
            }
            displayRemark.setText("");
        }catch (Exception e) {
            displayRemark.setText("Please enter a valid amount");
        }
    }

    @FXML
    void gotoCart(MouseEvent event) {

    }

    @FXML
    void gotoHistoryScreen(MouseEvent event) {
        super.gotoHistory((Stage) searchBar.getScene().getWindow());
    }

    @FXML
    public void gotoPreviousScreen() {
        super.gotoPrevious((Stage) searchBar.getScene().getWindow());
    }

    @FXML
    void gotoSearchResults(MouseEvent event) {

    }

    @FXML
    void gotoSeller(ActionEvent event) {
        Seller seller = GlobalData.getInstance().getGlobalSeller(displaySeller.getText());
        super.gotoSellerDetail(seller, (Stage) searchBar.getScene().getWindow(), "product", displayName.getText());
    }

}
