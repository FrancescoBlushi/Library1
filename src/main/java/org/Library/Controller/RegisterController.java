package org.Library.Controller;

import org.Library.DaoModels.AdminDao;
import org.Library.DaoModels.UserDao;
import org.Library.DaoModels.UtenteDao;
import org.Library.models.Admin;
import org.Library.models.Users;
import org.Library.models.Utente;

public class RegisterController {

    public RegisterController() {}

    public void registerUser(String cartId,String nome,String cognome,String username,String password){
        Users users = new Users(cartId, nome, cognome);
        Utente utente = new Utente(username, password, users);

        UserDao userDao = new UserDao();
        UtenteDao utenteDao1 = new UtenteDao();
        userDao.addUser(users);
        utenteDao1.addUtente(utente);

    }

    public void registerAdmin(String cartId,String nome,String cognome,String username,String password,String idBiblioteca){
        Users users = new Users(cartId, nome, cognome);
        Admin admin = new Admin(idBiblioteca, username, password, users);

        UserDao userDao = new UserDao();
        userDao.addUser(users);

        AdminDao adminDao = new AdminDao();
        adminDao.addAdmin(admin);
    }
}
