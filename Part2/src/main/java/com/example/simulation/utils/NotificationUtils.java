package com.example.simulation.utils;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

public class NotificationUtils {
    private static final double NOTIFICATION_DURATION = 2000;

    public void showNotification(String title, String message) {
        Stage stage = new Stage();
        stage.initStyle(StageStyle.UNDECORATED);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle(title);

        Label messageLabel = new Label(message);
        messageLabel.setWrapText(true);
        messageLabel.setTextFill(Color.WHITE);
        messageLabel.setPadding(new Insets(10));

        Button closeButton = new Button("×");
        closeButton.setOnAction(e -> stage.close());
        closeButton.setStyle("-fx-background-color: transparent; -fx-text-fill: white; -fx-font-weight: bold;");

        HBox layout = new HBox(messageLabel, closeButton);
        layout.setAlignment(Pos.CENTER);
        layout.setSpacing(10);
        layout.setBackground(new Background(new BackgroundFill(Color.DARKSLATEBLUE, new CornerRadii(10), Insets.EMPTY)));
        layout.setEffect(new DropShadow(10, 5, 5, Color.DARKGRAY));
        layout.setPadding(new Insets(10));

        Scene scene = new Scene(layout);
        stage.setScene(scene);

        stage.setOnShown(event -> {
            Timeline fadeInTimeline = new Timeline(
                    new KeyFrame(Duration.ZERO, evt -> stage.setOpacity(0)),
                    new KeyFrame(new Duration(500), evt -> stage.setOpacity(1))
            );
            fadeInTimeline.play();

            Timeline hideTimeline = new Timeline(
                    new KeyFrame(Duration.millis(NOTIFICATION_DURATION))
            );
            hideTimeline.setOnFinished(evt -> stage.close());
            hideTimeline.play();
        });

        stage.setOnHiding(event -> {
            Timeline fadeOutTimeline = new Timeline(
                    new KeyFrame(Duration.ZERO, evt -> stage.setOpacity(1)),
                    new KeyFrame(new Duration(500), evt -> stage.setOpacity(0))
            );
            fadeOutTimeline.play();
        });

        stage.showAndWait();
    }
}