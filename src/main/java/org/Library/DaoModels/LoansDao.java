package org.Library.DaoModels;
import org.Library.Dto.LoanBook;
import org.Library.models.*;
import org.Library.Dto.LoanBook;
import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

public class LoansDao {

    private EntityManager em;
    private Boolean isManaged;

    public LoansDao(EntityManager entityManager) {
        this.em = entityManager;
        this.isManaged = true;
    }


    public LoansDao() {
        this.isManaged = false;
        this.em = ConnectionDB.getInstance().getEntity();
    }

    public void addLoan(Loans loan) {
        try {
            em.getTransaction().begin();
            em.persist(loan);
            em.getTransaction().commit();
        } finally {
            if (!isManaged) {
                em.close();
            }
        }
    }

    public List<Loans> findAllLoans() {
        try {
            Query query = em.createQuery("SELECT l FROM Loans l");
            return query.getResultList();
        } finally {
            if (!isManaged) {
                em.close();
            }
        }
    }

    public List<Loans> loansByCartId(String cartId) {
        try {
            Query query = em.createQuery("SELECT l FROM Loans l WHERE l.user.cartId = :cartId");
            query.setParameter("cartId", cartId);
            return query.getResultList();
        } finally {
            if (!isManaged) {
                em.close();
            }
        }
    }

    public boolean findLoanbyIsbn(String isbn) {
        if(!isManaged){
            this.em=ConnectionDB.getInstance().getEntity();
        }
        try {
            Query query = em.createQuery("SELECT l FROM Loans l WHERE l.book.isbn = :isbn");
            query.setParameter("isbn", isbn);
            try {
                query.getResultList();
                return true;
            } catch (NoResultException e) {
                return false;
            }

        } finally {
            if (!isManaged) {
                em.close();
            }
        }
    }

    public List<Loans> findLoan(String isbn) {
        try {
            Query query = em.createQuery("SELECT l FROM Loans l WHERE l.book.isbn = :isbn");
            query.setParameter("isbn", isbn);
            return query.getResultList();
        } finally {
            if (!isManaged) {
                em.close();
            }
        }
    }

    public Boolean setDueDate(String isbn, String cartID, LocalDate returnDate) {
        try {
            Query findQuery = em.createQuery("SELECT l FROM Loans l WHERE l.book.isbn = :isbn");
            findQuery.setParameter("isbn", isbn);
            boolean trova = !findQuery.getResultList().isEmpty();

            if (trova) {
                em.getTransaction().begin();
                Query query = em.createQuery("UPDATE Loans l SET l.returnDate = :returnDate WHERE l.book.isbn = :isbn and l.user.cartId = :cartId");
                query.setParameter("cartId", cartID);
                query.setParameter("returnDate", returnDate);
                query.setParameter("isbn", isbn);

                int valore = query.executeUpdate();

                em.getTransaction().commit();


                return valore >= 1;
            }
            return false;

        } catch (Exception e) {
            em.getTransaction().rollback();
            throw e;
        } finally {
            if (!isManaged) {
                em.close();
            }
        }

    }

    public Boolean controlReturnDate(String isbn) {
        boolean control = false;
        List<Loans> loans = findLoan(isbn);
        for (Loans loan : loans) {
            if (loan.getReturnDate() == null) {
                control = true;
            }

        }
        return control;

    }



    //Metodo che permette di eliminare un Utente tenendo conto delle loro chiavi primary ed eliminando in ordine prima utente, poi
    // i Loans e in fine users.
    public void removeUserAndAssociations(String cartId) {
        if (!isManaged) {
            em = ConnectionDB.getInstance().getEntity();
        }

        try {
            em.getTransaction().begin();

            // Rimuovi il record `Utente` associato al `Users` specificato
            Query deleteUtenteQuery = em.createQuery("DELETE FROM Utente u WHERE u.users.cartId = :cartId");
            deleteUtenteQuery.setParameter("cartId", cartId);
            deleteUtenteQuery.executeUpdate();

            // Rimuovi tutti i Loans associati al Users specificato
            Query deleteLoansQuery = em.createQuery("DELETE FROM Loans l WHERE l.user.cartId = :cartId");
            deleteLoansQuery.setParameter("cartId", cartId);
            deleteLoansQuery.executeUpdate();

            // Infine, elimina il record `Users`
            Query deleteUserQuery = em.createQuery("DELETE FROM Users u WHERE u.cartId = :cartId");
            deleteUserQuery.setParameter("cartId", cartId);
            deleteUserQuery.executeUpdate();

            em.getTransaction().commit();
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

    public List<LoanBook> getLibriLoan(String cartId){
        if(!isManaged){
            em = ConnectionDB.getInstance().getEntity();
        }
        try{
            Query query = em.createQuery("SELECT new org.Library.Dto.LoanBook(b.title,b.autore,l.user.cartId,l.book.isbn,l.loanDate,l.dueDate,l.returnDate)"+
                    "from Loans l JOIN Books b on l.book.isbn = b.isbn  WHERE l.user.cartId = :cartI",LoanBook.class);
            query.setParameter("cartI", cartId);
            return query.getResultList();
        }finally {
            if(!isManaged){
                em.close();
            }
        }
    }


}

