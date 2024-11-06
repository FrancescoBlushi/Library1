package org.Library.DaoModels;

import org.Library.models.*;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

public class AdminDao {

    private EntityManager em;
    private Boolean isManaged;

    public AdminDao(EntityManager em) {
        this.em = em;
        this.isManaged = true;
    }


    public AdminDao() {
        this.isManaged = false;
        this.em = ConnectionDB.getInstance().getEntity();
    }

    public void addAdmin(Admin admin) {
        if(!isManaged){
            this.em = ConnectionDB.getInstance().getEntity();
        }
        try {
            em.getTransaction().begin();
            em.persist(admin);
            em.getTransaction().commit();
        } finally {
            if (!isManaged) {
                em.close();
            }
        }
    }

    public List<Admin> findAdminByUsername(String username) {
        try {
            Query query = em.createQuery("SELECT a FROM Admin a WHERE a.username = :username");
            query.setParameter("username", username);
            return query.getResultList();
        } finally {
            if (!isManaged && em.isOpen()) {
                em.close();
            }
        }
    }

    public List<Admin> findAllAdmins() {
        try {
            Query query = em.createQuery("SELECT a FROM Admin a");
            return query.getResultList();
        } finally {
            if (!isManaged) {
                em.close();
            }
        }
    }

    public Admin findAdminByUsernameAndPassword(String username, String password) {
        try {
            Query query = em.createQuery("SELECT a FROM Admin a WHERE a.username = :username AND a.password = :password");
            query.setParameter("username", username);
            query.setParameter("password", password);
            return (Admin) query.getSingleResult();
        } finally {
            if (!isManaged) {
                em.close();
            }
        }
    }

    public boolean doesUtenteExist(String username, String password) {
        try {
            Query query = em.createQuery("SELECT u FROM Admin u WHERE u.username = :username AND u.password = :password");
            query.setParameter("username", username);
            query.setParameter("password", password);
            return !query.getResultList().isEmpty();
        } finally {
            if (!isManaged) {
                em.close();
            }
        }
    }

    public boolean usernameEsiste(String username) {
        try {
            Query query = em.createQuery("SELECT u FROM Admin u WHERE u.username = :username");
            query.setParameter("username", username);
            return !query.getResultList().isEmpty();
        }finally {
            if (!isManaged) {
                em.close();
            }
        }
    }
}
