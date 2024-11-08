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

    private Utente utente ;
    private Users users;
    private UtenteDao utenteDao;
    private UtenteView loginView;
    private AdminDao adminDao;
    private AdminView adminView;


    public AccessController () {}

    public void accessUser (TextField usernameField, TextField passwordField, Label messaglabel, Stage stage) {
        String username = usernameField.getText();
        String passowrd = passwordField.getText();
        utenteDao = new UtenteDao();
        if (!username.isEmpty() && !passowrd.isEmpty()) {
            boolean trova = utenteDao.doesUserExist(username, passowrd);
            if (trova) {
                System.out.println("Trovato");
                utenteDao = new UtenteDao();
                utente = utenteDao.findUtenteUsername(username, passowrd);
                users = utente.getUsers();
                String cartdId = users.getCartId();
                loginView = new UtenteView(stage);
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
         adminDao = new AdminDao();
        if (!username.isEmpty() && !passowrd.isEmpty()) {
            boolean trova = adminDao.doesUtenteExist(username, passowrd);
            if (trova) {
                adminView = new AdminView(stage,username,new AdminController());
                adminView.mainLayout();
            }else {
                MessageView.mostraMessaggio(messaglabel, "Utente non trovato", "-fx-font-weight: bold;");
            }

        }else {
            MessageView.mostraMessaggio(messaglabel, "Devi compilare tutti i campi", "-fx-font-weight: bold;");
        }

    }
}
