package org.Library.DaoModels;
import org.Library.models.Admin;
import org.Library.models.Users;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;


public class UserDao {

    private EntityManager em ;
    private Boolean isManaged;

    public UserDao(EntityManager em) {
        this.em = em;
        this.isManaged = true;
    }

    public UserDao() {
        this.isManaged = false;
        this.em = ConnectionDB.getInstance().getEntity();
    }



    public void addUser(Users user) {
        try {
            em.getTransaction().begin();
            em.persist(user);
            em.getTransaction().commit();
        } finally {
            if (!isManaged) {
                em.close();
            }
        }
    }



    public Users findUserByCartId(String cartId) {
        try {
            return em.find(Users.class, cartId);
        } finally {
            if (!isManaged) {
                em.close();
            }
        }
    }

    public boolean controllUser(String cartId, String name, String lastName) {
        try {
            Query query = em.createQuery("SELECT u FROM Users u WHERE u.cartId = :cartId AND u.name = :name AND u.lastname = :lastName");
            query.setParameter("cartId", cartId);
            query.setParameter("name", name);
            query.setParameter("lastName", lastName);
            return !query.getResultList().isEmpty();
        } finally {
            if (!isManaged) {
                em.close();
            }
        }
    }

    public boolean findUser(String cartId) {
        try {
            Query query = em.createQuery("SELECT u FROM Users u WHERE u.cartId = :cartId");
            query.setParameter("cartId", cartId);
            return !query.getResultList().isEmpty();
        } finally {
            if (!isManaged) {
                em.close();
            }
        }
    }

    public List<Users> findUsersById(String cartId) {
        try {
            Query query = em.createQuery("SELECT u FROM Users u WHERE u.cartId = :cartId");
            query.setParameter("cartId", cartId);
            return query.getResultList();
        } finally {
            if (!isManaged) {
                em.close();
            }
        }
    }

    public Users usersCall(String username){

        AdminDao adminDao = new AdminDao();
        List<Admin> utenteList = adminDao.findAdminByUsername(username);
        if(!utenteList.isEmpty()){
            Admin admin = utenteList.get(0);
            Users users = admin.getUsers();
            return users;
        }
        return null;

    }
}

