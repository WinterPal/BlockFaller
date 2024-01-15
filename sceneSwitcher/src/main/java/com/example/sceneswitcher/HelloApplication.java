package com.example.sceneswitcher;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    private Pane root = new Pane();
    public Pane createScreen(){
        Button button = new Button("Change scene");
        button.setOnAction(e->{
            Stage new_stage =  (Stage) root.getScene().getWindow();
            Pane p = new Pane();
            p.setPrefSize(800,600);
            new_stage.setScene(new Scene(p));
        });
        root.getChildren().add(button);
        root.setPrefSize(600,800);
        return root;
    }
    @Override
    public void start(Stage stage) throws IOException {
        Scene s = new Scene(createScreen());
        stage.setScene(s);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}