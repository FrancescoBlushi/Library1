package org.Library.JavaFX;
import org.Library.Controller.RegisterController;
import org.Library.DaoModels.*;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;


public class RegisterView {

    private Stage stage;
    private Scene scene;
    private Scene scene2;
    UserDao userDao;
    UtenteDao utenteDao;
    RegisterController registerController;
    AdminDao adminDao;

    public RegisterView(Stage stage) {
        this.stage = stage;
    }

    public Scene createRegisterScene() {
        VBox vbox = new VBox(10);
        vbox.setPadding(new Insets(10));

        Label biblotecaLabel = new Label("IdBibloteca");
        biblotecaLabel.setStyle("-fx-font-weight: bold");

        TextField biblotecaTextField = new TextField();
        biblotecaTextField.setPromptText("Inserire codice identificazione Biblioteca");

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

        Label registerPassword = new Label("Password");
        registerPassword.setStyle("-fx-font-weight: bold");

        PasswordField registerField4 = new PasswordField();
        registerField4.setPromptText("Inserisci Password");

        Button registerButton = new Button("Registrazione");
        registerButton.setStyle("-fx-font-weight: bold");

        Button indietroButton = new Button("Indietro");
        registerButton.setStyle("-fx-font-weight: bold");

        ToggleGroup roleToggleGroup = new ToggleGroup();

        RadioButton userRadioButton = new RadioButton("User");
        userRadioButton.setToggleGroup(roleToggleGroup);
        userRadioButton.setSelected(true); // Opzione di default

        RadioButton adminRadioButton = new RadioButton("Admin");
        adminRadioButton.setToggleGroup(roleToggleGroup);

        HBox bottonBox = new HBox(10);
        bottonBox.setPadding(new javafx.geometry.Insets(10));
        bottonBox.getChildren().addAll(registerButton,indietroButton,userRadioButton,adminRadioButton);

        Label messageLabel = new Label();
        registerButton.setOnAction((event) -> {
            String cartId = registerField.getText().trim();
            String nome = registerField1.getText().trim();
            String cognome = registerField2.getText().trim();
            String username = registerField3.getText().trim();
            String password = registerField4.getText().trim();
            String idBiblioteca = biblotecaTextField.getText().trim();

            if (!cartId.isEmpty() && !nome.isEmpty() && !cognome.isEmpty() && !username.isEmpty() && !password.isEmpty()) {
                userDao = new UserDao();
                boolean find = userDao.findUser(cartId);

                if (find) {
                    MessageView.mostraMessaggio(messageLabel, "Card Id esistente", "-fx-font-size: 14; -fx-background-color: #a63845; -fx-text-fill: white;");
                    return;
                }
                registerController = new RegisterController();

                if (userRadioButton.isSelected()) {
                    utenteDao = new UtenteDao();
                    if (utenteDao.utenteEsistente(username)) {
                        MessageView.mostraMessaggio(messageLabel, "UserName esistente", "-fx-font-size: 14; -fx-background-color: #a63845; -fx-text-fill: white;");
                        return;
                    }

                    registerController.registerUser(cartId,nome,cognome,username,password);

                } else if (adminRadioButton.isSelected()) {
                     adminDao = new AdminDao();
                    if (adminDao.usernameEsiste(username)) {
                        MessageView.mostraMessaggio(messageLabel, "UserName esistente", "-fx-font-size: 14; -fx-background-color: #a63845; -fx-text-fill: white;");
                        return;
                    }
                    registerController.registerAdmin(cartId,nome,cognome,username,password,idBiblioteca);

                }


                MessageView.mostraMessaggio(messageLabel, "Registrazione completata con successo: CartId - " + cartId, "-fx-font-size: 14; -fx-background-color: #56b656; -fx-text-fill: white;");

            } else {
                MessageView.mostraMessaggio(messageLabel, "Errore: Tutti i campi devono essere compilati", "-fx-font-size: 14; -fx-background-color: #a63845; -fx-text-fill: white;");
            }
        });



        vbox.getChildren().addAll(
                biblotecaLabel,biblotecaTextField,
                registerLabel, registerField,
                registerNome, registerField1,
                registerCognome, registerField2,
                registerUsername, registerField3,
                registerPassword, registerField4,

                bottonBox, messageLabel
        );

        scene = stage.getScene();
        indietroButton.setOnAction(event1 -> stage.setScene(scene));

        scene2 = new Scene(vbox,1400,600);

        return scene2;
    }


}




