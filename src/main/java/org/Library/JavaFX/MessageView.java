package org.Library.JavaFX;
import javafx.animation.PauseTransition;
import javafx.scene.control.Label;
import javafx.util.Duration;

public class MessageView {


    public static void mostraMessaggio(Label messageLabel, String testoMessaggio, String stile) {

        messageLabel.setText(testoMessaggio);
        messageLabel.setStyle(stile);


        PauseTransition pause = new PauseTransition(Duration.seconds(5));
        pause.setOnFinished(event -> messageLabel.setText(""));


        pause.play();
    }

}
