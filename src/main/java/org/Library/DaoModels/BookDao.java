package org.Library.DaoModels;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.Library.Pattern.BookDaoInterface;
import org.Library.models.*;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;


public class BookDao implements BookDaoInterface {

    private EntityManager em;
    private Boolean isManaged;

    public BookDao(EntityManager entityManager){
        this.em = entityManager;
        this.isManaged = true;
    }

    public BookDao(){
        this.isManaged = false;
        this.em = ConnectionDB.getInstance().getEntity();
    }

    @Override
    public void addBook(Books book) {
        if (!isManaged) {               // Apertura del collegamento
            em = ConnectionDB.getInstance().getEntity();
        }
        try {
            em.getTransaction().begin();  // Inizia la transazione
            em.persist(book);  // Aggiunge il libro al database
            em.getTransaction().commit();  // Conferma la transazione
        } finally {
            if(!isManaged){
                em.close();
            }

        }
    }
    @Override
    public void removeBook(String isbn) {
        if (!isManaged) {               // Apertura del collegamento
            em = ConnectionDB.getInstance().getEntity();
        }
        try {
            em.getTransaction().begin();
            Books book = em.find(Books.class, isbn);
            if (book != null) {
                em.remove(book);
            }
            em.getTransaction().commit();
        } finally {
            if(!isManaged){
                em.close();
            }

        }
    }

    @Override
    public Books findBookByIsbn(String isbn) {
        if (!isManaged) {               // Apertura del collegamento
            em = ConnectionDB.getInstance().getEntity();
        }
        try {
            Query query = em.createQuery("SELECT b FROM Books b WHERE b.isbn = :isbn");
            query.setParameter("isbn", isbn);
            return (Books) query.getSingleResult();
        }finally {
            if(!isManaged){
                em.close();
            }


        }
    }

    // Metodo che permette di disattivare caseSensitive e di avere ricerca anche parziale

    public List<Books> findBooksByTitle(String title) {
        if (!isManaged) {
            em = ConnectionDB.getInstance().getEntity();
        }
        try {
            String titolo = title.toLowerCase();
            Query query = em.createQuery("SELECT b FROM Books b WHERE LOWER(b.title) LIKE :title");
            query.setParameter("title", "%"+ titolo +"%");
            return query.getResultList();
        } finally {
            if(!isManaged){
                em.close();
            }
        }
    }


    public Books findBook(String titolo) {
        if (!isManaged) {
            em = ConnectionDB.getInstance().getEntity();
        }
        try {
            Query query = em.createQuery("SELECT b FROM Books b WHERE b.title = :title");
            query.setParameter("title", titolo);
            return (Books) query.getSingleResult();
        }catch (NoResultException e){
            return null;
        }finally {
            if(!isManaged){
                em.close();
            }
        }
    }



    public ObservableList<Books> getBooks() {
        if (!isManaged ) {               // Apertura del collegamento
            em = ConnectionDB.getInstance().getEntity();
        }
        try {
            Query query = em.createQuery("SELECT b FROM Books b");
            List<Books> booksList = query.getResultList();
            // Converte la lista di libri in ObservableList
            return FXCollections.observableArrayList(booksList);
        } finally { if(!isManaged){
            em.close();
        }
        }
    }

    public Boolean findBookBooleanIsbn(String isbn) {
        if (!isManaged ) {               // Apertura del collegamento
            em = ConnectionDB.getInstance().getEntity();
        }
        try {
            Query query = em.createQuery("SELECT b FROM Books b WHERE b.isbn = :isbn");
            query.setParameter("isbn", isbn);
            return !query.getResultList().isEmpty();
        } finally {
            if (!isManaged) {
                em.close();
            }
        }
    }

    @Override
    public boolean cambiaDisponibilita(String isbn, int nuoviDisponibili) {
        if (!isManaged) {               // Apertura del collegamento
            em = ConnectionDB.getInstance().getEntity();
        }
        try {
            Query findQuery = em.createQuery("SELECT b FROM Books b WHERE b.isbn = :isbn");
            findQuery.setParameter("isbn", isbn);
            boolean find = !findQuery.getResultList().isEmpty();

            if (find) {
                em.getTransaction().begin();
                Query updateQuery = em.createQuery(
                        "UPDATE Books b SET b.disponibili = :disponibili WHERE b.isbn = :isbn AND b.disponibili > 0"
                );
                updateQuery.setParameter("disponibili", nuoviDisponibili);
                updateQuery.setParameter("isbn", isbn);

                int result = updateQuery.executeUpdate();
                em.getTransaction().commit();

                return result == 1;
            }
            return false;
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw e;
        } finally {
            if (!isManaged) {
                em.close();
            }
        }
    }


}
