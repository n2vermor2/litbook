package com.example.demo;

import javafx.animation.TranslateTransition;
import javafx.util.Duration;
import javafx.scene.Node;

public class animationShake {
    private TranslateTransition tt;
    public animationShake(Node node){
        tt = new TranslateTransition(Duration.millis(60),node);
        tt.setFromX(0f);
        tt.setByX(10f);
        tt.setCycleCount(4);
        tt.setAutoReverse(true);
    }
    public void playAnimation(){
        tt.playFromStart();
    }
}
