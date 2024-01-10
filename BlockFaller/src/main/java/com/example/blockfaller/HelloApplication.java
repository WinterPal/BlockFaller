package com.example.blockfaller;

import javafx.animation.AnimationTimer;
import javafx.animation.Interpolator;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

public class HelloApplication extends Application {
    private Pane root = new Pane();
    private Player player = new Player(400 - 50,1000 - 100,100,100);
    public Parent createContent(){
        root.setPrefSize(800,1000);

        root.getChildren().add(player);
        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long l) {
                update();
            }

        };
        timer.start();
        return root;
    }
    @Override
    public void start(Stage stage) throws IOException {
        Scene scene = new Scene(createContent());

        scene.setOnKeyPressed(e -> {
            switch (e.getCode()){
                case A:
                    player.moveLeft();
                    break;
                case D:
                    player.moveRight();
                    break;
                case SPACE:
                    player.dash();
            }
        });
        stage.setScene(scene);
        stage.show();
    }
    private void update(){
        if(player.getTranslateX() >= 0 && player.getTranslateX() <= 700) {
            if (player.dir.equals("left")) {
                player.setTranslateX(player.getTranslateX() - player.speed);
            }else if (player.dir.equals("right")){
                player.setTranslateX(player.getTranslateX() + player.speed);
            };
        }else{
            if (player.dir.equals("left")){
                player.setTranslateX(player.getTranslateX() + player.speed);
                player.dir = "right";
            }else if (player.dir.equals("right")){
                player.setTranslateX(player.getTranslateX() - player.speed);
                player.dir = "left";
            }

        }


    }
    private static class Player extends Rectangle {
        boolean dead = false;
        String dir = "";
        private TranslateTransition tt = new TranslateTransition(Duration.seconds(0.1),this);
        int speed = 5;
        private boolean isAnimating = false;

        Player(int x,int y,int w,int h ){
            super(w,h,Color.rgb(0,255,0));
            setTranslateX(x);
            setTranslateY(y);
            tt.setOnFinished( e -> {isAnimating = false;
                if(getTranslateX() < 0){
                    setTranslateX(0);
                }else if(getTranslateX() > 700){
                    setTranslateX(700);
                }});
            tt.setInterpolator(Interpolator.EASE_OUT);
        }

        void moveLeft(){
            dir = "left";
        }
        void moveRight(){
            dir = "right";
        }
        void dash(){
            if (isAnimating){
                return;
            }
            isAnimating = true;
            int dir_n = 0;
            if(dir.equals("right")){
                dir_n = 1;
            }else if (dir.equals("left")){
                dir_n = -1;
            }
            tt.setFromX(getTranslateX());
            if ( dir_n == -1){
                tt.setToX(getTranslateX() - 200);
            } else if (dir_n == 1){
                tt.setToX(getTranslateX() + 200);
            };
            tt.play();

        }
    }
    public static void main(String[] args) {
        launch();
    }
}