package org.Library.Controller;

import javafx.scene.control.Label;
import org.Library.DaoModels.BookDao;
import org.Library.DaoModels.LoansDao;
import org.Library.DaoModels.UserDao;
import org.Library.JavaFX.MessageView;
import org.Library.models.Books;
import org.Library.models.Loans;
import org.Library.models.Users;

import java.time.LocalDate;

public class UtenteController {

    private BookDao bookDao;
    private Books books;
    private UserDao userDao;
    private Users user;
    private LoansDao loansDao;
    private Loans loan;

    public UtenteController() {}

    public Label prendiPrestito(String titolo, String id, LocalDate currentDate, LocalDate dueDate,Label message) {

        //Serve per cercare il Libro da prendere in Prestito
        bookDao = new BookDao();
        books = bookDao.findBook(titolo);

        //Isbn utile per la ricerca del libro
        String isbn = books.getIsbn();

        //Serve per cercare l'user per poterlo usare per creare il Loan
        userDao = new UserDao();
        user = userDao.findUserByCartId(id);

        int disponibili = books.getDisponibili();

        //Aggiunta del prestito dopo un controllo if
        loansDao = new LoansDao();
        Boolean trova = loansDao.controlReturnDate(isbn);

        if (loansDao.findLoanbyIsbn(isbn) && trova) {
            MessageView.mostraMessaggio(message, "Libro gia in prestito", "-fx-background-color: #ff1500; -fx-text-fill: white");

        } else if (disponibili == 0) {
            MessageView.mostraMessaggio(message,"Libro non disponibile perche gia in prestito","-fx-background-color: #ff1500; -fx-text-fill: white");

        } else {
             loan = new Loans(currentDate, dueDate, user, books);
            disponibili = disponibili - 1;
            loansDao= new LoansDao();
            loansDao.addLoan(loan);
            bookDao = new BookDao();
            bookDao.cambiaDisponibilita(isbn, disponibili);

            MessageView.mostraMessaggio(message, "Prestito andato a buon fine", "-fx-background-color: #26ff00; -fx-text-fill: white");
        }


        return message;
    }

    public boolean restituisciLibro(String isbn, String cartID) {
        LocalDate currentDate = LocalDate.now();
        loansDao = new LoansDao();
        if (loansDao.setDueDate(isbn, cartID, currentDate)) {
            System.out.println(isbn);
            bookDao = new BookDao();
            books = bookDao.findBookByIsbn(isbn);
            System.out.println(books.getTitle());
            int disponibili = books.getDisponibili() + 1;
            bookDao.cambiaDisponibilita(isbn, disponibili);
            return true;
        } else {
            return false;
        }
    }

}
