module com.app.vending {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.google.zxing;
    requires com.google.zxing.javase;
    requires java.desktop;
    requires javafx.swing;

    opens com.app.vending.app        to javafx.graphics;
    opens com.app.vending.controller to javafx.fxml;
    opens com.app.vending.model      to javafx.base;
    opens com.app.vending.controller.list to javafx.fxml;
}