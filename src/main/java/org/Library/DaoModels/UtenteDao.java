package org.Library.DaoModels;

import org.Library.models.Users;
import org.Library.models.Utente;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import java.util.List;

public class UtenteDao {

    private EntityManager em ;
    private Boolean isManaged;

    public UtenteDao() {
        this.isManaged = false;
        this.em = ConnectionDB.getInstance().getEntity();
    }


    public UtenteDao(EntityManager em) {
        this.em = em;
        this.isManaged = true;
    }

    public void addUtente(Utente utente) {
        try {
            em.getTransaction().begin();
            em.persist(utente);
            em.getTransaction().commit();
        } finally {
            if (!isManaged) {
                em.close();
            }
        }
    }


    public Utente findUtenteUsername(String username, String password) {
        try {
            Query query = em.createQuery("SELECT u FROM Utente u WHERE u.username = :username AND u.password = :password");
            query.setParameter("username", username);
            query.setParameter("password", password);
            return (Utente) query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        } finally {
            if (!isManaged) {
                em.close();
            }
        }
    }


    public List<Utente> findUtenteByUsername(String username) {
        try {
            Query query = em.createQuery("SELECT u FROM Utente u WHERE u.username = :username");
            query.setParameter("username", username);
            return query.getResultList();
        } finally {
            if (!isManaged) {
                em.close();
            }
        }
    }

    public List<Utente> findAllUtenti() {
        try {
            Query query = em.createQuery("SELECT u FROM Utente u");
            return query.getResultList();
        } finally {
            if (!isManaged) {
                em.close();
            }
        }
    }

    public boolean doesUserExist(String username, String password) {
        try {
            em.getTransaction().begin();
            Query query = em.createQuery("SELECT u FROM Utente u WHERE u.username = :username AND u.password = :password");
            query.setParameter("username", username);
            query.setParameter("password", password);
            return !query.getResultList().isEmpty();
        } finally {
            if (!isManaged) {
                em.close();
            }
        }
    }

    public void updatePassword(String username, String newPassword) {
        try {
            em.getTransaction().begin();
            Query query = em.createQuery("SELECT u FROM Utente u WHERE u.username = :username");
            query.setParameter("username", username);
            Utente utente = (Utente) query.getSingleResult();
            if (utente != null) {
                utente.setPassword(newPassword);
                em.merge(utente);
            }
            em.getTransaction().commit();
        } catch (NoResultException e) {
            System.out.println("Utente non trovato");
        } finally {
            if (!isManaged) {
                em.close();
            }
        }
    }

    public Users usersCall(String username) {
        List<Utente> utenteList = findUtenteByUsername(username);
        if (!utenteList.isEmpty()) {
            return utenteList.get(0).getUsers();
        }
        return null;
    }

    public Boolean utenteEsistente(String username) {
        try{
            Query query = em.createQuery("SELECT u FROM Utente u WHERE u.username = :username");
            query.setParameter("username", username);
            return !query.getResultList().isEmpty();
        }finally {
            if(!isManaged){
                em.close();
            }
        }
    }


}
