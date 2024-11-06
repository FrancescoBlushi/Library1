package org.Library.models;
import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table (name = "loans")
public class Loans {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name="loandate",nullable=false)
    LocalDate loanDate;

    @Column(name = "duedate",nullable=false)
    LocalDate dueDate;

    @Column(name = "returndate")
    LocalDate returnDate;

    @ManyToOne (cascade = CascadeType.REMOVE)
    @JoinColumn(name = "cartid",referencedColumnName = "cartid")
    private Users user;

    @ManyToOne (cascade=CascadeType.REMOVE)
    @JoinColumn(name="isbn",referencedColumnName = "isbn")
    Books book;

    public Loans(LocalDate loanDate, LocalDate dueDate, Users user, Books book) {
        this.loanDate = loanDate;
        this.dueDate = dueDate;
        this.returnDate = null;
        this.user = user;
        this.book = book;
    }
    public Loans() {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public LocalDate getLoanDate() {
        return loanDate;
    }

    public void setLoanDate(LocalDate loanDate) {
        this.loanDate = loanDate;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;
    }


    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }

    public Books getBook() {
        return book;
    }

    public void setBook(Books book) {
        this.book = book;
    }
}
