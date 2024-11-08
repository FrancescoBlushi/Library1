package org.Library.Proxy;

import org.Library.Pattern.BookDaoInterface;
import org.Library.models.Books;

public class BookDaoProxy implements BookDaoInterface {
    private BookDaoInterface bookDao;
    private String userRole;

    public BookDaoProxy(BookDaoInterface bookDao, String userRole) {
        this.bookDao = bookDao;
        this.userRole = userRole;
    }

    @Override
    public void addBook(Books books){
        if(userRole.equals("admin")){
            bookDao.addBook(books);
        }else{
            System.out.println("Access denied");
        }

    }
    @Override
    public void removeBook(String isbn){
        if(userRole.equals("admin")){
            bookDao.removeBook(isbn);
        }else{
            System.out.println("Access denied");
        }
    }

    @Override
    public Books findBookByIsbn(String isbn){
        if(userRole.equals("user")){
            return bookDao.findBookByIsbn(isbn);
        }else {
            return null;
        }
    }

    @Override
    public boolean cambiaDisponibilita(String isbn, int nuoviDisponibili){

        return bookDao.cambiaDisponibilita(isbn, nuoviDisponibili );

    }
}
