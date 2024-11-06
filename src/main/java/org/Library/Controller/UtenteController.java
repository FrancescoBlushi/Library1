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

    public UtenteController() {}

    public Label prendiPrestito(String titolo, String id, LocalDate currentDate, LocalDate dueDate,Label message) {

        //Serve per cercare il Libro da prendere in Prestito
        BookDao bookDao = new BookDao();
        Books books = bookDao.findBook(titolo);


        //Isbn utile per la ricerca del libro
        String isbn = books.getIsbn();


        //Serve per cercare l'user per poterlo usare per creare il Loan
        UserDao userDao = new UserDao();
        Users user = userDao.findUserByCartId(id);

        int disponibili = books.getDisponibili();

        //Aggiunta del prestito dopo un controllo if
        LoansDao loansDao = new LoansDao();
        LoansDao loansDao1 = new LoansDao();
        Boolean trova = loansDao1.controlReturnDate(isbn);

        if (loansDao.findLoanbyIsbn(isbn) && trova) {
            MessageView.mostraMessaggio(message, "Libro gia in prestito", "-fx-background-color: #ff1500; -fx-text-fill: white");

        } else if (disponibili == 0) {
            MessageView.mostraMessaggio(message,"Libro non disponibile perche gia in prestito","-fx-background-color: #ff1500; -fx-text-fill: white");

        } else {
            Loans loan = new Loans(currentDate, dueDate, user, books);
            disponibili = disponibili - 1;
            LoansDao loansDao2 = new LoansDao();
            loansDao2.addLoan(loan);
            BookDao bookDao2 = new BookDao();
            bookDao2.cambiaDisponibilita(isbn, disponibili);

            MessageView.mostraMessaggio(message, "Prestito andato a buon fine", "-fx-background-color: #26ff00; -fx-text-fill: white");
        }


        return message;
    }

    public boolean restituisciLibro(String isbn, String cartID) {
        LocalDate currentDate = LocalDate.now();
        LoansDao loansDao1 = new LoansDao();
        if (loansDao1.setDueDate(isbn, cartID, currentDate)) {
            System.out.println(isbn);
            BookDao bookDao1 = new BookDao();
            Books books1 = bookDao1.findBookByIsbn(isbn);
            System.out.println(books1.getTitle());
            int disponibili = books1.getDisponibili() + 1;
            bookDao1.cambiaDisponibilita(isbn, disponibili);
            return true;
        } else {
            return false;
        }
    }

}
