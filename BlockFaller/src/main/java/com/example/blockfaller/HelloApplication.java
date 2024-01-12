package com.example.blockfaller;

import javafx.animation.AnimationTimer;
import javafx.animation.Interpolator;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.stream.Collectors;

public class HelloApplication extends Application {
    private Pane root = new Pane();
    private Player player = new Player(400 - 50,1000 - 100,100,100);
    private Label score_label = new Label();
    private int enemy_speed = 3;
    private double t = 0;
    private double score = 0;
    public Parent createContent(){
        root.setPrefSize(800,1000);
        root.setBackground(new Background(new BackgroundFill(Color.valueOf("#000038"), new CornerRadii(0),new Insets(0))));
        root.getChildren().add(player);
        score_label.setTranslateX(400);
        score_label.setTranslateY(50);
        score_label.setTextFill(Paint.valueOf("#ffffff"));
        root.getChildren().add(score_label);
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
    private List<Node> all_enemies(){
        return root.getChildren().stream().collect(Collectors.toList());
    }
    private void update(){
        t += 0.016;
        score += 0.016;
        score_label.setText(Double.toString(Math.floor(score)));

        all_enemies().forEach(s -> {
            if(s.getUserData() == "enemy"){
                s.setTranslateY(s.getTranslateY() + enemy_speed);
                if (s.getTranslateY() >= 1100){
                    root.getChildren().remove(s);
                }
                if (s.getBoundsInParent().intersects(player.getBoundsInParent())){
                    root.getChildren().remove(player);
                }
            }
        });
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
        if (t > 1.5){
            Enemy enemy = new Enemy(Math.random() * (750 - 50) + 50,-50,50,Color.color(Math.random(),Math.random(),Math.random()));
            root.getChildren().add(enemy);
            t = 0;
        }



    }
    private static class Enemy extends Circle{
        boolean dead = false;
        String type = "enemy";

        private TranslateTransition tt = new TranslateTransition(Duration.seconds(1),this);
        Enemy(double x, double y, double r,Color color){
            super(x,y,r,color);
            setUserData(type);
        }

    }
    private static class Player extends Rectangle {
        boolean dead = false;
        String type ="player";
        String dir = "";
        private TranslateTransition tt = new TranslateTransition(Duration.seconds(0.1),this);
        int speed = 3;
        private boolean isAnimating = false;

        Player(int x,int y,int w,int h ){
            super(w,h,Color.rgb(0,255,0));
            setTranslateX(x);
            setTranslateY(y);
            setUserData(type);
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