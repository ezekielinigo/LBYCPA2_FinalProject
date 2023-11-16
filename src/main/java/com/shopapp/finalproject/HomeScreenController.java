package com.shopapp.finalproject;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class HomeScreenController extends BaseController implements Initializable {

    @FXML
    private GridPane productGrid;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        GlobalData g = GlobalData.getInstance();

        g.setRelevantResults();
        for (Product product : g.getRelevantProducts()) {
            try {
                // load a new thumbnail view for each product
                FXMLLoader loader = new FXMLLoader(getClass().getResource("ThumbnailView.fxml"));
                AnchorPane thumbnailView = loader.load();
                ThumbnailViewController controller = loader.getController();
                controller.setProduct(product);

                Point2D nextPosition = getNextAvailablePosition(productGrid);
                if (nextPosition.getX() != -1 && nextPosition.getY() != -1) {
                    // add the new thumbnailView to the grid
                    productGrid.add(thumbnailView, (int) nextPosition.getX(), (int) nextPosition.getY());

                    // set a click handler for the product detail view
                    thumbnailView.setOnMouseClicked(e -> gotoProductDetail(product));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        for (Seller seller : g.getRelevantSellers()) {
            try {
                // load a new thumbnail view for each product
                FXMLLoader loader = new FXMLLoader(getClass().getResource("ThumbnailView.fxml"));
                AnchorPane thumbnailView = loader.load();
                ThumbnailViewController controller = loader.getController();
                controller.setSeller(seller);

                Point2D nextPosition = getNextAvailablePosition(productGrid);
                if (nextPosition.getX() != -1 && nextPosition.getY() != -1) {
                    // add the new thumbnailView to the grid
                    productGrid.add(thumbnailView, (int) nextPosition.getX(), (int) nextPosition.getY());

                    // set a click handler for the product detail view
                    thumbnailView.setOnMouseClicked(e -> gotoSellerDetail(seller));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    void gotoCart(MouseEvent event) throws IOException {

    }

    @FXML
    void gotoSearchResults(MouseEvent event) {

    }

    private Point2D getNextAvailablePosition(GridPane gridPane) {
        int numColumns = gridPane.getColumnConstraints().size();
        int numRows = gridPane.getRowConstraints().size();
        boolean isOccupied = false;

        // Check existing rows and columns for an empty space
        for (int rowIndex = 0; rowIndex < numRows; rowIndex++) {
            for (int colIndex = 0; colIndex < numColumns; colIndex++) {
                isOccupied = false;
                for (Node child : gridPane.getChildren()) {
                    // GridPane.getRowIndex and getColumnIndex might return null, so we are checking for that
                    Integer childRowIndex = GridPane.getRowIndex(child);
                    Integer childColIndex = GridPane.getColumnIndex(child);
                    // Using == for Integer comparison is safe here because it's within the range of -128 to 127 due to Integer caching
                    if (childRowIndex != null && childRowIndex == rowIndex &&
                            childColIndex != null && childColIndex == colIndex) {
                        isOccupied = true;
                        break;
                    }
                }
                if (!isOccupied) {
                    return new Point2D(colIndex, rowIndex);
                }
            }
        }

        // No empty space found, so add a new row
        gridPane.getRowConstraints().add(new RowConstraints()); // Add constraints if you have specific needs
        return new Point2D(0, numRows); // Return the first column index of the new row
    }

    @FXML
    void gotoPrevious(MouseEvent event) {

    }

    @FXML
    void gotoHistory(MouseEvent event) {

    }


}
