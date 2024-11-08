
import org.Library.DaoModels.BookDao;
import org.Library.models.Books;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class BookTest {

    private BookDao bookDao;
    EntityManager em;

    @BeforeEach
    public void setUp() {
        this.em = ConnectionTest.getInstance().getEntityManager();
        bookDao = new BookDao(em);
    }

    @Test
    public void testAddBook() {
        Books book =  new Books("978-00099910107","Ernest","A Farewell to Arms","Storico","Inglese",1,"prima");
        bookDao.addBook(book);
        Books tryBook =bookDao.findBook("A Farewell to Arms");

        assertNotNull(tryBook);
        assertEquals("Ernest",tryBook.getAutore());
    }

    @Test
    public void testRemoveBookIsbn(){
        Books book =  new Books("978-00099910107","Ernest","Arms","Storico","Inglese",1,"prima");
        bookDao.addBook(book);
        Books tryBook =bookDao.findBook("Arms");
        String isbn = tryBook.getIsbn();

        bookDao.removeBook(isbn);
        Books tryBook2 = bookDao.findBook("Arms");
        assertNull(tryBook2);
    }

    @Test
    public void testFindBook() {
        Books book =  new Books("978-00099910107","Ernest","A Farewell to Arms","Storico","Inglese",1,"prima");
        bookDao.addBook(book);
        Books tryBook = bookDao.findBookByIsbn("978-00099910107");
        assertNotNull(tryBook);
        assertEquals("Ernest",tryBook.getAutore());

    }

    @Test
    public void testFindBookByTitle() {
        Books book =  new Books("978-00099910107","Ernest","A Farewell to Arms","Storico","Inglese",1,"prima");
        bookDao.addBook(book);
        List<Books>listBooks = bookDao.findBooksByTitle("A Farewell to Arms");

        assertNotNull(listBooks);
        assertEquals(1, listBooks.size());
    }


    @Test
    public void testBooleanFindIsbn(){
        Books books = new Books("123465","test","test","test","test",1,"test");
        bookDao.addBook(books);
        Boolean trova = bookDao.findBookBooleanIsbn("123465");

        assertTrue(trova);
        assertFalse(bookDao.findBookBooleanIsbn("12"));
    }


    @AfterEach
    public void tearDown() {
        em.getTransaction().begin();
        Query deleteBooks = em.createQuery("DELETE FROM Books");
        deleteBooks.executeUpdate();
        em.getTransaction().commit();

        if (em.isOpen()) {
            em.close();
        }
    }
}
