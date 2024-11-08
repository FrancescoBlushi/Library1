package org.Library.Pattern;

import javafx.collections.ObservableList;
import org.Library.models.Books;

import java.awt.print.Book;
import java.util.List;

public interface BookDaoInterface {

    public void addBook(Books book);
    public void removeBook(String isbn);
    public Books findBookByIsbn(String isbn);
    public boolean cambiaDisponibilita(String isbn, int nuoviDisponibili);
}
