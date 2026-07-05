package com.app.vending.controller;

import com.app.vending.controller.list.OrderItemRowController;
import com.app.vending.model.Item;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.awt.image.BufferedImage;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class PaymentController implements Initializable {

    @FXML
    private VBox itemListBox;

    @FXML
    private Label lblTotal;

    @FXML
    private Label lblCountdown;

    @FXML
    private VBox qrBox;

    private List<Item> selectedItems;
    private long totalAmount;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void initData(List<Item> items, long total) {

        this.selectedItems = items;
        this.totalAmount = total;

        populateItems();

        buildQRCode("https://www.google.com");

        startCountdown();
    }

    private void populateItems() {

        itemListBox.getChildren().clear();

        for (Item item : selectedItems) {

            if (item.getQuantity() <= 0)
                continue;

            try {

                FXMLLoader loader = new FXMLLoader(
                        getClass().getResource("/com/app/vending/list/orderItemRow.fxml")
                );

                HBox row = loader.load();

                OrderItemRowController controller = loader.getController();
                controller.initData(item);

                itemListBox.getChildren().add(row);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        lblTotal.setText("Tổng cộng: " + formatVnd(totalAmount));
    }

    /**
     * Tạo QR thật bằng ZXing
     */
    private void buildQRCode(String text) {

        try {

            QRCodeWriter writer = new QRCodeWriter();

            BitMatrix matrix = writer.encode(
                    text,
                    BarcodeFormat.QR_CODE,
                    220,
                    220
            );

            BufferedImage bufferedImage =
                    MatrixToImageWriter.toBufferedImage(matrix);

            ImageView imageView = new ImageView(
                    SwingFXUtils.toFXImage(bufferedImage, null)
            );

            imageView.setFitWidth(220);
            imageView.setFitHeight(220);
            imageView.setPreserveRatio(true);

            qrBox.getChildren().clear();
            qrBox.getChildren().add(imageView);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void startCountdown() {

        final int[] count = {3};

        lblCountdown.setText("Chuyển trang sau: " + count[0] + "s");

        Timeline timeline = new Timeline(

                new KeyFrame(Duration.seconds(1), e -> {

                    count[0]--;

                    if (count[0] > 0) {

                        lblCountdown.setText(
                                "Chuyển trang sau: " + count[0] + "s"
                        );

                    } else {

                        switchToReceiver();

                    }

                })

        );

        timeline.setCycleCount(3);
        timeline.play();
    }

    private void switchToReceiver() {

        try {

            Parent root = FXMLLoader.load(
                    getClass().getResource("/com/app/vending/page/receiver.fxml")
            );

            Stage stage = (Stage) lblCountdown.getScene().getWindow();

            stage.setScene(new Scene(root, 400, 800));

        } catch (Exception e) {

            e.printStackTrace();

        }
    }

    private String formatVnd(long amount) {

        return String.format("%,d VNĐ", amount)
                .replace(',', '.');

    }
}