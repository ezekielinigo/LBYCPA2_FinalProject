package com.shopapp.finalproject;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

public class CartController extends BaseController{

    Stage currentStage;

    @FXML
    private GridPane productGrid;

    @FXML
    private TextField searchBar;

    @FXML
    private TextField displayGrandTotal;

    @FXML
    private CheckBox checkAll;

    public void setup() {
        GlobalData g = GlobalData.getInstance();
        currentStage = (Stage) productGrid.getScene().getWindow();
        Platform.runLater(() -> {
            for (String[] x : g.getCart()) {
                Product product = g.getGlobalProduct(x[0]);
                int count = Integer.parseInt(x[1]);
                displayThumbnail(product, count);
            }
        });

    }

    ArrayList<CartThumbnailViewController> thumbnails = new ArrayList<>();

    private void displayThumbnail(Product product, int count) {
        // adds the CartThumbnailView.fxml to the productGrid
        // since the productGrid is 1 width and X height, where X == number of products in cart
        // products are added one after the other, with new rows being created as needed

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("CartThumbnailView.fxml"));
            AnchorPane thumbnailView = loader.load();

            CartThumbnailViewController controller = loader.getController();
            controller.setup(product, count);
            thumbnails.add(controller);

            RowConstraints rowConstraints = new RowConstraints(150);
            productGrid.getRowConstraints().add(0, rowConstraints); // Add the new row at the top

            // Shift existing thumbnails down
            for (Node child : productGrid.getChildren()) {
                int rowIndex = GridPane.getRowIndex(child);
                GridPane.setRowIndex(child, rowIndex + 1);
            }

            // Add the new thumbnail at the top
            productGrid.add(thumbnailView, 0, 0);

            // update grand total when amount changes
            controller.displayName.selectedProperty().addListener((observable, oldValue, newValue) -> {
                updateTotal();
            });
            controller.plusButton.onMouseClickedProperty().addListener((observable, oldValue, newValue) -> {
                updateTotal();
            });
            controller.minusButton.onMouseClickedProperty().addListener((observable, oldValue, newValue) -> {
                updateTotal();
            });
            controller.displayAmount.textProperty().addListener((observable, oldValue, newValue) -> {
                updateTotal();
            });

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @FXML
    public void updateTotal() {
        try {
            float total = 0;
            for (CartThumbnailViewController thumbnail : thumbnails) {
                if (thumbnail.displayName.isSelected()) {
                    total += thumbnail.getTotal();
                }
            }
            final String totalText = String.format("TOTAL: P %,.2f", total);
            displayGrandTotal.setText(totalText);
        } catch (Exception e) {
            System.out.println("Total updated");
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

    @FXML
    void selectAll() {
        if (checkAll.isSelected()) {
            for (CartThumbnailViewController x : thumbnails) {
                x.setChecked(true);
            }
        }else {
            for (CartThumbnailViewController x : thumbnails) {
                x.setChecked(false);
            }
        }
    }

    @FXML
    void checkOut() {
        ArrayList<String[]> checkoutCart = new ArrayList<>();

        // get all selected items
        for (CartThumbnailViewController x : thumbnails) {
            if (x.displayName.isSelected()) {
                checkoutCart.add(new String[]{x.product.getName(), x.displayAmount.getText()});
            }
        }

        if (checkoutCart.isEmpty())
            return;

        // open Checkout.fxml in a new window
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Checkout.fxml"));
        try {
            Scene scene = new Scene(loader.load());
            Stage checkoutStage = new Stage();
            checkoutStage.setTitle("Checkout");
            checkoutStage.setScene(scene);
            checkoutStage.show();

            checkoutStage.setOnHiding(event -> {

                GlobalData g = GlobalData.getInstance();

                for (String[] x : checkoutCart) {
                    Product product = g.getGlobalProduct(x[0]);
                    int count = Integer.parseInt(x[1]);

                    if (product.getStock() - count == 0) {
                        g.removeGlobalProduct(product.getName());
                        g.getGlobalSeller(product.getSeller().getName()).removeProduct(product);
                    }else
                        product.setStock(product.getStock() - count);

                    g.removeFromCart(product.getName());
                }

                super.gotoCart((Stage) productGrid.getScene().getWindow());

            });

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
