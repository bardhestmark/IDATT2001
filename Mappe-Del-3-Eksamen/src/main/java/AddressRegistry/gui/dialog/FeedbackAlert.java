package AddressRegistry.gui.dialog;

import javafx.animation.*;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.util.Duration;

/**
 * Class for feedback
 */
public class FeedbackAlert {

    /**
     * Creates a feedback alert and shows it with animation in the given view
     *
     * @param alertBox The view to show feedback
     * @param message  The message to show
     * @param success  True for green and false for red
     */
    public static void display(HBox alertBox, String message, boolean success) {
        HBox successCard = new HBox();
        successCard.setAlignment(Pos.CENTER);
        successCard.setPrefWidth(0);
        successCard.setPrefHeight(50);

        Label messageLabel = new Label(message);
        messageLabel.setStyle("-fx-text-fill: #F5F5F5");

        if (success) successCard.setStyle("-fx-background-color: #689F38"); //green
        else successCard.setStyle("-fx-background-color: #F44336"); //red

        alertBox.getChildren().clear();
        alertBox.getChildren().add(successCard);
        showTransition(successCard, messageLabel);
    }

    private static void showTransition(HBox card, Label text) {
        KeyValue kv1 = new KeyValue(card.prefWidthProperty(), 200);
        KeyFrame kf1 = new KeyFrame(Duration.millis(50), kv1);
        Timeline grow = new Timeline();
        grow.getKeyFrames().add(kf1);
        grow.setOnFinished(event -> card.getChildren().add(text));

        PauseTransition pause = new PauseTransition(Duration.millis(1500));
        pause.setOnFinished(event -> card.getChildren().clear());

        KeyValue kv2 = new KeyValue(card.prefWidthProperty(), 0);
        KeyFrame kf2 = new KeyFrame(Duration.millis(50), kv2);
        Timeline shrink = new Timeline();
        shrink.getKeyFrames().add(kf2);

        SequentialTransition sequence = new SequentialTransition(grow, pause, shrink);
        sequence.play();
    }
}
