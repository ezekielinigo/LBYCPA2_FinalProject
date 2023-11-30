package com.shopapp.finalproject;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.stage.Stage;

import java.io.IOException;

public class CartController extends BaseController{

    Stage currentStage;

    @FXML
    private GridPane productGrid;

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

            productGrid.addRow(productGrid.getRowCount(), thumbnailView);
            RowConstraints rc = new RowConstraints();
            rc.setPrefHeight(100);
            productGrid.getRowConstraints().add(rc);
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

    }

    /** thumbnail functionalities **/

}
