package com.app.vending.app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.net.URL;

public class App extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        URL fxml = getClass().getResource("/com/app/vending/main.fxml");
        Parent root = FXMLLoader.load(fxml);
        Scene scene = new Scene(root,400,800);

        primaryStage.setTitle("Smart Vending Machine");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
