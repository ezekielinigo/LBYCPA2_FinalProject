package com.shopapp.finalproject;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.stage.Stage;

import java.io.IOException;

public class CartController extends BaseController{

    Stage currentStage;

    @FXML
    private GridPane productGrid;

    @FXML
    private TextField searchBar;

    public void setup() {
        GlobalData g = GlobalData.getInstance();
        Stage stage = (Stage) productGrid.getScene().getWindow();
        for (String[] x : g.getCart()) {
            Product product = g.getGlobalProduct(x[0]);
            int count = Integer.parseInt(x[1]);
            addThumbnail(product, count);
        }

        currentStage = (Stage) productGrid.getScene().getWindow();
    }

    private void addThumbnail(Product product, int count) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("CartThumbnailView.fxml"));
            AnchorPane thumbnailView = loader.load();
            CartThumbnailViewController controller = loader.getController();

            controller.setup(product, count);

            // Insert the new thumbnail at the beginning of the productGrid
            productGrid.getChildren().add(0, thumbnailView);
            RowConstraints rc = new RowConstraints();
            rc.setPrefHeight(100);
            productGrid.getRowConstraints().add(0, rc);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /** moving between screens **/
    @FXML
    void gotoCart() {
        super.gotoCart(currentStage, "cart", null);
    }

    @FXML
    void gotoHistoryScreen() {
        super.gotoHistory(currentStage, "cart", null);
    }

    @FXML
    void gotoPreviousScreen() {
        super.gotoPrevious(currentStage);
    }

    @FXML
    void gotoSearchResults() {
        super.gotoSearchScreen(currentStage, "cart", searchBar.getText());
    }

    /** thumbnail functionalities **/

}
