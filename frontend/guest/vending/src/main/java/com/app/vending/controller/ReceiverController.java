package com.app.vending.controller;

import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.net.URL;
import java.util.ResourceBundle;

public class ReceiverController implements Initializable {

    @FXML private VBox  rootBox;
    @FXML private Label lblMessage;
    @FXML private Label lblSub;
    @FXML private Label lblCountdown;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        playFadeIn();
        startReturnCountdown();
    }

    /** Hiệu ứng fade-in khi trang load */
    private void playFadeIn() {
        FadeTransition ft = new FadeTransition(Duration.seconds(1), rootBox);
        ft.setFromValue(0);
        ft.setToValue(1);
        ft.play();
    }

    /** Đếm ngược 5s rồi quay về trang chủ */
    private void startReturnCountdown() {
        final int[] count = {5};
        lblCountdown.setText("Quay về trang chủ sau: " + count[0] + "s");

        Timeline timeline = new Timeline(
                new KeyFrame(Duration.seconds(1), e -> {
                    count[0]--;
                    if (count[0] > 0) {
                        lblCountdown.setText("Quay về trang chủ sau: " + count[0] + "s");
                    } else {
                        returnToMain();
                    }
                })
        );
        timeline.setCycleCount(5);
        timeline.play();
    }

    private void returnToMain() {
        try {
            URL fxml = getClass().getResource("/com/app/vending/main.fxml");
            Parent root = FXMLLoader.load(fxml);
            Stage stage = (Stage) rootBox.getScene().getWindow();
            stage.setScene(new Scene(root, 400,800));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}