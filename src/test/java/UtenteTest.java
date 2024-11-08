
import org.Library.DaoModels.UserDao;
import org.Library.DaoModels.UtenteDao;
import org.Library.models.Users;
import org.Library.models.Utente;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class UtenteTest {
    private UtenteDao utenteDao;
    private UserDao userDao;
    EntityManager em;

    @BeforeEach
    public void setUp() {
        this.em = ConnectionTest.getInstance().getEntityManager();
        userDao = new UserDao(em);
        utenteDao = new UtenteDao(em);
    }

    @Test
    public void testAddUtente() {
        Users user = new Users("cart001", "NomeTest", "CognomeTest");
        userDao.addUser(user);

        Utente utente = new Utente("testUser", "testPass", user);
        utenteDao.addUtente(utente);

        Utente retrievedUtente = utenteDao.findUtenteUsername("testUser", "testPass");
        assertNotNull(retrievedUtente);
        assertEquals("testUser", retrievedUtente.getUsername());
        assertEquals("testPass", retrievedUtente.getPassword());
    }

    @Test
    public void testFindUtente() {
        Users user = new Users("cart002", "NomeTest", "CognomeTest");
        userDao.addUser(user);
        Utente utente = new Utente("User", "Pass", user);
        utenteDao.addUtente(utente);

        Utente retrievedUtente = utenteDao.findUtenteUsername("User", "Pass");
        assertNotNull(retrievedUtente);
        assertEquals("User", retrievedUtente.getUsername());
    }

    @Test
    public void testFindUtenteByUsername() {
        Users user = new Users("cart003", "NomeTest", "CognomeTest");
        userDao.addUser(user);
        Utente utente = new Utente("User", "Pass", user);
        utenteDao.addUtente(utente);

        List<Utente> utenti = utenteDao.findUtenteByUsername("User");
        assertFalse(utenti.isEmpty());
        assertEquals("User", utenti.get(0).getUsername());
    }

    @Test
    public void testFindAllUtenti() {
        Users user1 = new Users("cart004", "Nome1", "Cognome1");
        Users user2 = new Users("cart005", "Nome2", "Cognome2");

        userDao.addUser(user1);
        userDao.addUser(user2);

        Utente utente1 = new Utente("user1", "pass1", user1);
        Utente utente2 = new Utente("user2", "pass2", user2);

        utenteDao.addUtente(utente1);
        utenteDao.addUtente(utente2);

        List<Utente> allUtenti = utenteDao.findAllUtenti();
        assertTrue(allUtenti.size() >= 2);
    }


    @Test
    public void testUpdatePassword() {
        Users user = new Users("cart007", "Nome", "Cognome");
        userDao.addUser(user);
        Utente utente = new Utente("User", "Pass", user);
        utenteDao.addUtente(utente);

        utenteDao.updatePassword("User", "newPass");

        Utente updatedUtente = utenteDao.findUtenteUsername("User", "newPass");
        assertNotNull(updatedUtente);
        assertEquals("newPass", updatedUtente.getPassword());
    }



    @AfterEach
    public void tearDown() {
        em.getTransaction().begin();
        Query deleteUtente = em.createQuery("DELETE FROM Utente ");
        Query query = em.createQuery("DELETE FROM Users");
        deleteUtente.executeUpdate();
        query.executeUpdate();
        em.getTransaction().commit();

        if (em.isOpen()) {
            em.close();
        }
    }
}
