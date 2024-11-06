package org.Library.Controller;

import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.Library.DaoModels.UserDao;
import org.Library.DaoModels.UtenteDao;
import org.Library.JavaFX.MessageView;

public class PasswordController {

    private UtenteDao utenteDao;
    private UserDao userDao;

    public PasswordController() {}

    public void newPassword(TextField registerField, TextField registerField1, TextField registerField2, TextField registerField3, TextField registerField4, Label messageLabel) {
        String cartId = registerField.getText();
        String nome = registerField1.getText();
        String cognome = registerField2.getText();
        String username = registerField3.getText();
        String password = registerField4.getText();

        utenteDao = new UtenteDao();
        userDao = new UserDao();

        if (!cartId.isEmpty() && !nome.isEmpty() && !cognome.isEmpty() && !username.isEmpty() && !password.isEmpty() && userDao.controllUser(cartId,nome,cognome)) {
            utenteDao.updatePassword(username,password);
            MessageView.mostraMessaggio(messageLabel,"Modifica completata con successo: CartId - " + cartId,"-fx-font-scale: 30;-fx-background-color: #56b656; -fx-text-fill: white;");
        }else
            MessageView.mostraMessaggio(messageLabel,"Errore: Tutti i campi devono essere compilati","-fx-font-scale: 30;-fx-background-color: #a63845 ; -fx-text-fill: white");


    }
}
