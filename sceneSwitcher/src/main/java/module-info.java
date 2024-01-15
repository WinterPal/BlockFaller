module com.example.sceneswitcher {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.sceneswitcher to javafx.fxml;
    exports com.example.sceneswitcher;
}