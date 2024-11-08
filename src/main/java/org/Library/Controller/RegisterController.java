package org.Library.Controller;

import org.Library.DaoModels.AdminDao;
import org.Library.DaoModels.UserDao;
import org.Library.DaoModels.UtenteDao;
import org.Library.Pattern.Observable;
import org.Library.models.Admin;
import org.Library.models.Users;
import org.Library.models.Utente;

public class RegisterController extends Observable {

    private Users users;
    private Utente utente;
    private UtenteDao utenteDao;
    private UserDao userDao;
    private Admin admin;
    private AdminDao adminDao;

    public RegisterController() {}

    public void registerUser(String cartId,String nome,String cognome,String username,String password){
        users = new Users(cartId, nome, cognome);
        utente = new Utente(username, password, users);

        userDao = new UserDao();
        utenteDao = new UtenteDao();
        userDao.addUser(users);
        utenteDao.addUtente(utente);

        synchronized (this) {
            notify();
        }

    }

    public void registerAdmin(String cartId,String nome,String cognome,String username,String password,String idBiblioteca){
        users = new Users(cartId, nome, cognome);
        admin = new Admin(idBiblioteca, username, password, users);

        userDao = new UserDao();
        userDao.addUser(users);

        adminDao = new AdminDao();
        adminDao.addAdmin(admin);
    }
}
