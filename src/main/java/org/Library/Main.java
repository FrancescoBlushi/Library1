package org.Library;
import org.Library.JavaFX.FirstView;
import javafx.application.Application;
import javafx.stage.Stage;


public class Main extends Application {

    @Override
    public void start(Stage stage) {

        FirstView firstView = new FirstView(stage);
        firstView.creaStage();

    }

    public static void main(String[] args) {
        launch(args);
    }

}
