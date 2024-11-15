package org.Library.Controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import org.Library.DaoModels.*;
import org.Library.JavaFX.MessageView;
import org.Library.Proxy.BookDaoProxy;
import org.Library.Proxy.LoanDaoProxy;
import org.Library.models.Books;
import org.Library.models.Users;
import org.Library.models.Utente;
import java.util.List;
import org.Library.Pattern.*;
import java.util.stream.Collectors;

public class AdminController extends Observable {

    private BookDao bookDao;
    private Books book;
    private UserDao userDao;;
    private UtenteDao utenteDao;
    private List<Users> users;
    private BookDaoInterface adminProxy;
    private LoanDaoInterface loanProxy;


    public AdminController () {}



    public void removeBook(TextField isbnText, TextField titoloText, Label messaggioLabel2) {
        bookDao = new BookDao();
        String isbn = isbnText.getText();
        String titolo = titoloText.getText();
        boolean trova = bookDao.findBookBooleanIsbn(isbn);
        loanProxy = new LoanDaoProxy(new LoansDao(),"admin");
        boolean eliminato = loanProxy.removeLoan(isbn);
        if(!eliminato){
            MessageView.mostraMessaggio(messaggioLabel2,"Libro attualmente in Prestito non eliminabile","-fx-text-fill: #26ff00; -fx-font-scale: white");
        }else{
            if(!isbn.isEmpty() && trova ) {
                System.out.println("Libro trovato");
                adminProxy = new BookDaoProxy(new BookDao(),"admin");
                adminProxy.removeBook(isbn);

                MessageView.mostraMessaggio(messaggioLabel2, "Libro con titolo : " + titolo + "eliminato con successo", "-fx-text-fill: #26ff00; -fx-font-scale: white");

            }else{
                MessageView.mostraMessaggio(messaggioLabel2,"Libro non trovato","-fx-text-fill: #26ff00; -fx-font-scale: white");
            }
        }


        synchronized (this) {
            notify();
        }
    }

    public void addBook(TextField isbnText, TextField autoreText, TextField titoloText, TextField generoText, TextField linguaText, TextField edizioneText,
                        TextField disponibiliText, Label messaggioLabel) {

        String isbn = isbnText.getText();
        String autore = autoreText.getText();
        String titolo = titoloText.getText();
        String genero = generoText.getText();
        String lingua = linguaText.getText();
        String edizione = edizioneText.getText();
        String disponibili = disponibiliText.getText();
        int numero = 0;

        try {
            numero = Integer.parseInt(disponibiliText.getText());
        } catch (NumberFormatException e) {
            System.out.println("Inserisci un numero valido.");
        }
        if(!isbn.isEmpty() && !titolo.isEmpty() && !genero.isEmpty() && !lingua.isEmpty()
                && !edizione.isEmpty() && !disponibili.isEmpty()) {
            book = new Books(isbn,titolo,autore,genero,lingua,numero,edizione);
            adminProxy = new BookDaoProxy(new BookDao(),"admin");
            adminProxy.addBook(book);

            MessageView.mostraMessaggio(messaggioLabel,"Libro aggiunto correttamente","-fx-text-fill: #ff1500; -fx-font-scale: white");
        }else
            MessageView.mostraMessaggio(messaggioLabel,"Tutti i campi devono essere convalidati.","-fx-background-color: #a63845; -fx-text-fill: white;");

        synchronized (this) {
            notify();
        }
    }

    public void clientSearch(String searchTerm, TableView<Users> tableView) {

        utenteDao = new UtenteDao();
        userDao = new UserDao();

        if (searchTerm == null || searchTerm.trim().isEmpty()) {
            List<Utente> utenti = utenteDao.findAllUtenti();
            users = utenti.stream()
                    .map(Utente::getUsers)
                    .collect(Collectors.toList());
        } else {

            users = userDao.findUsersById(searchTerm.trim());

        }

        if (!users.isEmpty()) {
            ObservableList<Users> usersObservableList = FXCollections.observableArrayList(users);
            tableView.setItems(usersObservableList);
        } else {
            tableView.setItems(FXCollections.observableArrayList());
            tableView.setPlaceholder(new Label("Nessun utente trovato con questo ID."));
        }
    }

    public void eliminaUtenteController(TextField eliminaField,Label messaggio){
        String cartID = eliminaField.getText().trim();
        userDao = new UserDao();
        boolean trova =userDao.findUser(cartID);
        if(trova){
            loanProxy = new LoanDaoProxy(new LoansDao(),"admin");
            loanProxy.removeUserAndAssociations(cartID);
            MessageView.mostraMessaggio(messaggio,"Utente eliminato correttamente","-fx-text-fill: #26ff00; -fx-font-scale: white");
        }else{
            MessageView.mostraMessaggio(messaggio,"Utente non trovato","-fx-text-fill: #ff1500; -fx-font-scale: white");
        }
        synchronized (this) {
            notify();
        }
    }
}
