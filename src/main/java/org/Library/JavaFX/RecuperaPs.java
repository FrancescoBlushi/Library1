package org.Library.JavaFX;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.Library.Controller.PasswordController;
import org.Library.DaoModels.UserDao;
import org.Library.DaoModels.UtenteDao;


public class RecuperaPs {


    public RecuperaPs() {
    }

    private Scene registerScene;
    private Stage stage;

    private UserDao userDao = new UserDao();
    private UtenteDao utenteDao = new UtenteDao();

    public RecuperaPs(Stage loginScene) {
            this.stage = loginScene;
        }

        public Scene createRecuperoScene() {
            VBox vbox = new VBox(10);
            vbox.setPadding(new Insets(10));

            Label registerLabel = new Label("CartdId");
            registerLabel.setStyle("-fx-font-weight: bold");
            TextField registerField = new TextField();
            registerField.setPromptText("Inserisci Carta' Identita");

            Label registerNome = new Label("Nome");
            registerNome.setStyle("-fx-font-weight: bold");
            TextField registerField1 = new TextField();
            registerField1.setPromptText("Inserisci Nome");

            Label registerCognome = new Label("Cognome");
            registerCognome.setStyle("-fx-font-weight: bold");
            TextField registerField2 = new TextField();
            registerField2.setPromptText("Inserisci Cognome");

            Label registerUsername = new Label("Username");
            registerUsername.setStyle("-fx-font-weight: bold");
            TextField registerField3 = new TextField();
            registerField3.setPromptText("Inserisci Username");

            Label registerPassword = new Label("New Password");
            registerPassword.setStyle("-fx-font-weight: bold");
            PasswordField registerField4 = new PasswordField();
            registerField4.setPromptText("Inserisci Password");

            Button confermaButton = new Button("Conferma");
            confermaButton.setStyle("-fx-font-weight: bold");

            Button indietroButton = new Button("Indietro");
            confermaButton.setStyle("-fx-font-weight: bold");

            Label messageLabel = new Label();

            HBox bottonBox = new HBox(10);
            bottonBox.setPadding(new javafx.geometry.Insets(10));
            bottonBox.getChildren().addAll(confermaButton,indietroButton);

            vbox.getChildren().addAll(
                    registerLabel, registerField,
                    registerNome, registerField1,
                    registerCognome, registerField2,
                    registerUsername, registerField3,
                    registerPassword, registerField4,

                    bottonBox, messageLabel

            );

            confermaButton.setOnAction((event) -> {
                PasswordController passwordController = new PasswordController();
                passwordController.newPassword(registerField,registerField1,registerField2,registerField3,registerField4,messageLabel);

            });

            Scene login = stage.getScene();
            indietroButton.setOnAction(event1 ->
                    stage.setScene(login));

            registerScene = new Scene(vbox,1400,600);

            return registerScene;
        }


    }

