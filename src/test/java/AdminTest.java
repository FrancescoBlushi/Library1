import org.Library.DaoModels.AdminDao;
import org.Library.DaoModels.UserDao;
import org.Library.models.Admin;
import org.Library.models.Users;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class AdminTest {

    private static AdminDao adminDao;
    private static UserDao userDao;
    EntityManager em ;


    @BeforeEach
    public void setUp() {
        em = ConnectionTest.getInstance().getEntityManager();
        adminDao = new AdminDao(em);
        userDao = new UserDao(em);
    }

    @Test
    public void addAdminTest() {
        adminDao = new AdminDao(em);
        userDao = new UserDao(em);
        Users u = new Users("test","test","test");
        userDao.addUser(u);
        Admin admin = new Admin("testBiblioteca","testAdmin","testPassword",u);
        adminDao.addAdmin(admin);

        Admin retrivedAdmin = adminDao.findAdminByUsernameAndPassword("testAdmin","testPassword");
        assertNotNull(retrivedAdmin);
        assertEquals("testAdmin",retrivedAdmin.getUsername());
    }


    @Test
    public void testFindAdminByUsername() {
        adminDao = new AdminDao(em);
        userDao = new UserDao(em);
        Users u = new Users("test","test","test");
        userDao.addUser(u);
        Admin admin = new Admin("testBiblioteca","test","testPassword",u);
        adminDao.addAdmin(admin);

        List<Admin> trovaAdmin = adminDao.findAdminByUsername("test");
        assertFalse(trovaAdmin.isEmpty());
        assertEquals("test",trovaAdmin.get(0).getUsername());
    }

    @Test
    public void testFindAllAdmins() {
        adminDao = new AdminDao(em);
        userDao = new UserDao(em);
        Users u1 = new Users("test","test","test");
        Users u2 = new Users("test2","test2","test2");

        userDao.addUser(u1);
        userDao.addUser(u2);
        Admin admin = new Admin("testBiblioteca","test","testPassword",u1);
        Admin admin2 = new Admin("testBiblioteca","test2","test2Password",u2);
        adminDao.addAdmin(admin);
        adminDao.addAdmin(admin2);
        List<Admin> allAdmin = adminDao.findAllAdmins();
        assertTrue(allAdmin.size() >= 2);
    }

    @Test
    public void testFindUserByUsernameandPassword() {
        adminDao = new AdminDao(em);
        userDao = new UserDao(em);
        Users u1 = new Users("test","test","test");
        Admin admin = new Admin("testBiblioteca","test","testPassword",u1);
        userDao.addUser(u1);
        adminDao.addAdmin(admin);
        Admin trovaAdmin = adminDao.findAdminByUsernameAndPassword("test","testPassword");
        assertNotNull(trovaAdmin);
        assertEquals("test",trovaAdmin.getUsername());
    }

    @Test
    public void testdoesUtenteExist(){
        adminDao = new AdminDao(em);
        userDao = new UserDao(em);
        Users u1 = new Users("test","test","test");
        Admin admin = new Admin("testBiblioteca","test","testPassword",u1);
        userDao.addUser(u1);
        adminDao.addAdmin(admin);

        assertTrue(adminDao.doesUtenteExist("test","testPassword"));
        assertFalse(adminDao.doesUtenteExist("test2","test2Password"));



    }

    @AfterEach
    public void tearDown() {
        em.getTransaction().begin();
        Query deleteAdmins = em.createQuery("DELETE FROM Admin");
        Query deleteUsers = em.createQuery("DELETE FROM Users");
        deleteAdmins.executeUpdate();
        deleteUsers.executeUpdate();
        em.getTransaction().commit();

        if (em.isOpen()) {
            em.close();
        }
    }


}
