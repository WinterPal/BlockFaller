module com.example.blockfaller {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.blockfaller to javafx.fxml;
    exports com.example.blockfaller;
}