package com.app.vending.controller.list;

import com.app.vending.model.Item;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.Consumer;

public class ProductCardController implements Initializable {

    @FXML private ImageView imgProduct;
    @FXML private Label     lblName;
    @FXML private Label     lblPrice;
    @FXML private Label     lblQty;

    private Item item;
    private Consumer<Void> onQuantityChanged;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        lblQty.setAlignment(Pos.CENTER);
        lblQty.setText("0");
    }

    public void initData(Item item, Consumer<Void> onQuantityChanged) {
        this.item              = item;
        this.onQuantityChanged = onQuantityChanged;

        lblName.setText(item.getName());
        lblPrice.setText(item.getPriceFormatted());
        refreshQty();

        item.quantityProperty().addListener((obs, oldVal, newVal) -> refreshQty());

        URL imgUrl = getClass().getResource(item.getImagePath());
        if (imgUrl != null) {
            imgProduct.setImage(new Image(imgUrl.toExternalForm()));
        }
    }

    private void refreshQty() {
        lblQty.setText(String.valueOf(item.getQuantity()));
        lblQty.setAlignment(Pos.CENTER);
    }

    @FXML
    private void handlePlus() {
        item.increment();
        refreshQty();
        if (onQuantityChanged != null) onQuantityChanged.accept(null);
    }

    @FXML
    private void handleMinus() {
        item.decrement();
        refreshQty();
        if (onQuantityChanged != null) onQuantityChanged.accept(null);
    }
}