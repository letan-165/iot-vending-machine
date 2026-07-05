package com.app.vending.controller;

import com.app.vending.controller.list.ProductCardController;
import com.app.vending.model.Item;
import com.app.vending.service.ItemService;
import com.app.vending.service.OrderService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class MainController implements Initializable {

    @FXML private GridPane productGrid;
    @FXML private Label    lblTotal;

    private final ItemService  itemService  = new ItemService();
    private final OrderService orderService = new OrderService();

    private List<Item> items;
    private static final int COLUMNS = 2;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        items = itemService.getAllItems();
        renderGrid();
    }

    private void renderGrid() {
        productGrid.getChildren().clear();
        for (int i = 0; i < items.size(); i++) {
            Item item = items.get(i);

            try {
                URL cardFxml = getClass().getResource("/com/app/vending/list/productCard.fxml");
                FXMLLoader loader = new FXMLLoader(cardFxml);
                VBox card = loader.load();

                ProductCardController cardCtrl = loader.getController();
                cardCtrl.initData(item, unused -> updateTotal());

                productGrid.add(card, i % COLUMNS, i / COLUMNS);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void updateTotal() {
        long total = items.stream().mapToLong(Item::getSubtotal).sum();
        if (lblTotal != null) {
            lblTotal.setText("Tổng: " + formatVnd(total));
        }
    }

    private String formatVnd(long amount) {
        return String.format("%,d VNĐ", amount).replace(',', '.');
    }

    @FXML
    private void handleCreateOrder() {
        List<Item> selected = items.stream()
                .filter(i -> i.getQuantity() > 0)
                .collect(Collectors.toList());

        if (selected.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Chú ý");
            alert.setHeaderText(null);
            alert.setContentText("Vui lòng chọn ít nhất một sản phẩm!");
            alert.showAndWait();
            return;
        }

        orderService.createOrder(items);
        long total = items.stream().mapToLong(Item::getSubtotal).sum();

        try {
            URL fxml = getClass().getResource("/com/app/vending/page/payment.fxml");
            FXMLLoader loader = new FXMLLoader(fxml);
            Parent root = loader.load();

            PaymentController pc = loader.getController();
            pc.initData(items, total);

            Stage stage = (Stage) productGrid.getScene().getWindow();
            stage.setScene(new Scene(root, 400, 800));

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}