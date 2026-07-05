package com.app.vending.controller.list;

import com.app.vending.model.Item;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class OrderItemRowController implements Initializable {

    @FXML private Label lblName;
    @FXML private Label lblQty;
    @FXML private Label lblSubtotal;

    @Override
    public void initialize(URL url, ResourceBundle rb) { }

    public void initData(Item item) {
        lblName.setText(item.getName());
        lblQty.setText("x" + item.getQuantity());
        lblSubtotal.setText(formatVnd(item.getSubtotal()));
    }

    private String formatVnd(long amount) {
        return String.format("%,d VNĐ", amount).replace(',', '.');
    }
}