import org.Library.DaoModels.BookDao;
import org.Library.DaoModels.LoansDao;
import org.Library.DaoModels.UserDao;
import org.Library.models.Books;
import org.Library.models.Loans;
import org.Library.models.Users;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


public class LoanTest {

    private LoansDao loanDao;
    private BookDao bookDao;
    private UserDao userDao;
    EntityManager em;

    @BeforeEach
    public void setUp() {
        this.em = ConnectionTest.getInstance().getEntityManager();
        loanDao = new LoansDao(em);
        bookDao = new BookDao(em);
        userDao = new UserDao(em);
    }

    @Test
    public void testAddLoan(){
        Users user = new Users("test","test","test");
        userDao.addUser(user);
        Books books = new Books("978-00099910107","Ernest","A Farewell to Arms","Storico","Inglese",1,"prima");
        bookDao.addBook(books);
        LocalDate now = LocalDate.now();
        LocalDate plusOneMonth = now.plusMonths(1);
        Loans loan = new Loans(now,plusOneMonth,user,books);

        loanDao.addLoan(loan);
        Loans loan1 = loanDao.findAllLoans().get(0);

        assertNotNull(loan1);

    }

    @Test
    public void testFindAllLoans() {
        Users user = new Users("cartId2", "Nome2", "Cognome2");
        userDao.addUser(user);

        Books book1 = new Books("12345","Autore Test1", "Titolo Test1", "Genere Test1", "Lingua", 1, "Edizione Test1");
        Books book2 = new Books("67890","Autore Test2", "Titolo Test2", "Genere Test2", "Lingua", 1, "Edizione Test2");
        bookDao.addBook(book1);
        bookDao.addBook(book2);

        Loans loan1 = new Loans(LocalDate.now(), LocalDate.now().plusDays(30), user, book1);
        Loans loan2 = new Loans(LocalDate.now(), LocalDate.now().plusDays(60), user, book2);
        loanDao.addLoan(loan1);
        loanDao.addLoan(loan2);

        List<Loans> allLoans = loanDao.findAllLoans();
        assertTrue(allLoans.size() >= 2);
    }
    @Test
    public void testFindByCartId() {
        Users user = new Users("cartId3", "Nome3", "Cognome3");
        userDao.addUser(user);

        Books book = new Books("11111", "Autore Test3","Titolo Test3", "Genere Test3", "Lingua", 1, "Edizione Test3");
        bookDao.addBook(book);

        Loans loan = new Loans(LocalDate.now(), LocalDate.now().plusDays(45), user, book);
        loanDao.addLoan(loan);

        List<Loans> loansByUser = loanDao.findAllLoans();
        assertNotNull(loansByUser);
        assertEquals(1, loansByUser.size());
        assertEquals(user.getCartId(), loansByUser.get(0).getUser().getCartId());
    }

    @Test
    public void testFindLoanbyIsbn() {
        Books book = new Books("22222","Autore Test5", "Titolo Test4", "Genere Test4", "Lingua", 1, "Edizione Test4");
        bookDao.addBook(book);

        Users user = new Users("cartId4", "Nome4", "Cognome4");
        userDao.addUser(user);

        Loans loan = new Loans(LocalDate.now(), LocalDate.now().plusDays(30), user, book);
        loanDao.addLoan(loan);

        boolean exists = loanDao.findLoanbyIsbn("22222");
        assertTrue(exists);
    }




    @AfterEach
    public void tearDown() {
        em.getTransaction().begin();
        Query deleteLoans = em.createQuery("DELETE FROM Loans");
        Query deleteBooks = em.createQuery("DELETE FROM Books");
        Query deleteUsers = em.createQuery("DELETE FROM Users");
        deleteLoans.executeUpdate();
        deleteBooks.executeUpdate();
        deleteUsers.executeUpdate();
        em.getTransaction().commit();

        if (em.isOpen()) {
            em.close();
        }
    }
}
