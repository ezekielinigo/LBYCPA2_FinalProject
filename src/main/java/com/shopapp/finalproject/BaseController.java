package com.shopapp.finalproject;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.stage.Stage;

import java.io.IOException;

public class BaseController {

    /**
     * gotoProductDetail: used to go to the product detail page where you view images, info, add to cart, etc.
     * gotoPrevious: used to go to the previous page viewed
     *               uses a STACK to store the previous pages
     *
     * gotoHistory: displays ALL previously visited pages in a similar grid format to the homescreen but without the banner
     */


    /**
     * this method switches to a screen that displays the product's details, images, etc. as well as the add to cart and seller buttons
     * @param product: the product that will be used to fill the product detail page
     */
    protected void gotoProductDetail(Product product, Stage stage) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ProductDetail.fxml"));
            Parent root = loader.load();
            ProductDetailController controller = loader.getController();
            controller.setProduct(product);
            stage.getScene().setRoot(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected void gotoSellerDetail(Seller seller, Stage stage) {
        // insert code here
    }

    /**
     * this method switches to the screen that displays the previous page viewed
     */
    protected void gotoPrevious() {
        // TBA di pa tapos

    }

    /**
     * this method switches to the screen that displays the full history of visited pages
     */
    protected void gotoHistory() {

    }


}
