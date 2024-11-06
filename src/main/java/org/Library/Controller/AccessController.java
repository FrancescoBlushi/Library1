package org.Library.Controller;

import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.Library.DaoModels.AdminDao;
import org.Library.DaoModels.UtenteDao;
import org.Library.JavaFX.AdminView;
import org.Library.JavaFX.MessageView;
import org.Library.JavaFX.UtenteView;
import org.Library.models.Users;
import org.Library.models.Utente;

public class AccessController {

    public AccessController () {}

    public void accessUser (TextField usernameField, TextField passwordField, Label messaglabel, Stage stage) {
        String username = usernameField.getText();
        String passowrd = passwordField.getText();
        UtenteDao utenteDao = new UtenteDao();
        if (!username.isEmpty() && !passowrd.isEmpty()) {
            boolean trova = utenteDao.doesUserExist(username, passowrd);
            if (trova) {
                System.out.println("Trovato");
                UtenteDao utenteDao1 = new UtenteDao();
                Utente utente = utenteDao1.findUtenteUsername(username, passowrd);
                Users users = utente.getUsers();
                String cartdId = users.getCartId();
                UtenteView loginView = new UtenteView(stage);
                loginView.createStage(username, cartdId);
            } else {
                messaglabel.setText("");
                messaglabel.setStyle("");
                MessageView.mostraMessaggio(messaglabel, "Utente non trovato", "-fx-font-weight: bold;");
            }
        } else {
            messaglabel.setText("");
            messaglabel.setStyle("");
            MessageView.mostraMessaggio(messaglabel, "Devi compilare tutti i campi", "-fx-font-weight: bold;");
            messaglabel.setText("Devi compilare tutti i campi");
        }

    }

    public void accesAdmin(TextField usernameField, TextField passwordField, Label messaglabel, Stage stage) {
        String username = usernameField.getText();
        String passowrd = passwordField.getText();
        AdminDao adminDao = new AdminDao();
        if (!username.isEmpty() && !passowrd.isEmpty()) {
            boolean trova = adminDao.doesUtenteExist(username, passowrd);
            if (trova) {
                AdminView adminView = new AdminView(stage,username);
                adminView.mainLayout();
            }else {
                MessageView.mostraMessaggio(messaglabel, "Utente non trovato", "-fx-font-weight: bold;");
            }

        }else {
            MessageView.mostraMessaggio(messaglabel, "Devi compilare tutti i campi", "-fx-font-weight: bold;");
        }

    }
}
