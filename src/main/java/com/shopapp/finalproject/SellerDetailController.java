package com.shopapp.finalproject;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.function.BiConsumer;

public class SellerDetailController extends BaseController{

    public void setup(Seller seller) {
        displayName.setText(seller.getName());
        displayDescription.setText(seller.getDescription());
        ArrayList<String> rating = new ArrayList<>();
        for (int i = 0; i < seller.getRating(); i++)
            rating.add("★");
        for (int i = seller.getRating(); i < 5; i++)
            rating.add("☆");
        displayPrice.setText(rating.toString());

        // handle choicebox and thumbnail display depending on selected value
        choiceBox.getItems().addAll("Products","Related Sellers");
        choiceBox.setValue("Products");
        choiceBox.setOnAction(event -> {
            String selected = choiceBox.getValue();
            displayThumbnail(selected, seller);
        });

        // handle product thumbnails
        displayThumbnail("Products", seller);
    }

    private <T> void addThumbnail(String fxmlFile, T item, BiConsumer<T, Stage> detailHandler, Stage stage) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
            AnchorPane thumbnailView = loader.load();
            ThumbnailViewController controller = loader.getController();

            if (item instanceof Product) {
                controller.setProduct((Product) item);
            } else if (item instanceof Seller) {
                controller.setSeller((Seller) item);
            }

            Point2D nextPosition = getNextAvailablePosition(productGrid);
            if (nextPosition.getX() != -1 && nextPosition.getY() != -1) {
                productGrid.add(thumbnailView, (int) nextPosition.getX(), (int) nextPosition.getY());
                thumbnailView.setOnMouseClicked(e -> detailHandler.accept(item, stage));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void displayThumbnail(String selected, Seller seller) {

        if (selected == "Products") {
            Platform.runLater(() -> {
                Stage stage = (Stage) productGrid.getScene().getWindow(); // This might be null here
                for (Product product : seller.getProducts()) {
                    addThumbnail("ThumbnailView.fxml", product, (p, s) -> gotoProductDetail(p, s), stage);
                }
            });
        }else{
            Platform.runLater(() -> {
                System.out.println("SHOW RELATED SELLERS");
            });
        }
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
    private ChoiceBox<String> choiceBox = new ChoiceBox<>();

    @FXML
    private TextArea displayDescription;

    @FXML
    private Label displayName;

    @FXML
    private Label displayPrice;

    @FXML
    private GridPane productGrid;



    @FXML
    void gotoCart(MouseEvent event) {

    }

    @FXML
    void gotoHistory(MouseEvent event) {

    }

    @FXML
    void gotoPrevious(MouseEvent event) {
        super.gotoPrevious((Stage) displayDescription.getScene().getWindow());
    }

    @FXML
    void gotoSearchResults(MouseEvent event) {

    }

}
