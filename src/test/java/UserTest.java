
import org.Library.DaoModels.UserDao;
import org.Library.models.Users;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class UserTest {

    private UserDao userDao;
    EntityManager em;

    @BeforeEach
    public void setUp() {
        this.em = ConnectionTest.getInstance().getEntityManager();
        userDao = new UserDao(em);
    }

    @Test
    public void testAddUser() {
        Users user = new Users("cart123", "NomeTest", "CognomeTest");
        userDao.addUser(user);

        Users retrievedUser = userDao.findUserByCartId("cart123");
        assertNotNull(retrievedUser);
        assertEquals("NomeTest", retrievedUser.getName());
        assertEquals("CognomeTest", retrievedUser.getLastname());
    }


    @Test
    public void testFindUserByCartId() {
        Users user = new Users("cart", "NomeTest", "CognomeTest");
        userDao.addUser(user);

        Users retrievedUser = userDao.findUserByCartId("cart");
        assertNotNull(retrievedUser);
        assertEquals("NomeTest", retrievedUser.getName());
        assertEquals("CognomeTest", retrievedUser.getLastname());
    }


    @Test
    public void testControllUser() {
        Users user = new Users("cart111", "NomeTest", "CognomeTest");
        userDao.addUser(user);

        boolean exists = userDao.controllUser("cart111", "NomeTest", "CognomeTest");
        assertTrue(exists);

        boolean notExists = userDao.controllUser("cart222", "NomeTest1", "CognomeTest1");
        assertFalse(notExists);
    }

    @Test
    public void testFindUser() {
        Users user = new Users("cart333", "NomeTest", "CognomeTest");
        userDao.addUser(user);

        assertTrue(userDao.findUser("cart333"));
        assertFalse(userDao.findUser("cart999"));
    }

    @Test
    public void testFindUsersById() {
        Users user = new Users("cart444", "NomeTest", "CognomeTest");
        userDao.addUser(user);

        List<Users> usersList = userDao.findUsersById("cart444");
        assertFalse(usersList.isEmpty());
        assertEquals(1, usersList.size());
        assertEquals("NomeTest", usersList.get(0).getName());
    }




    @AfterEach
    public void tearDown() {
        em.getTransaction().begin();
        Query deleteUsers = em.createQuery("DELETE FROM Users");
        deleteUsers.executeUpdate();
        em.getTransaction().commit();

        if (em.isOpen()) {
            em.close();
        }
    }




}
