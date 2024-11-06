package org.Library.Dto;

import java.time.LocalDate;

public class LoanBook {

    private String titolo;
    private String autore;
    private String cartId;
    private String isbn;
    private LocalDate loanDate;
    private LocalDate dueDate;
    private LocalDate returnDate;

    public LoanBook(String titolo, String autore, String cartId, String isbn, LocalDate loanDate, LocalDate dueDate, LocalDate returnDate) {
        this.titolo = titolo;
        this.autore = autore;
        this.cartId = cartId;
        this.isbn = isbn;
        this.loanDate = loanDate;
        this.dueDate = dueDate;
        this.returnDate = returnDate;
    }

    public String getTitolo() {
        return titolo;
    }

    public void setTitolo(String titolo) {
        this.titolo = titolo;
    }

    public String getAutore() {
        return autore;
    }

    public void setAutore(String autore) {
        this.autore = autore;
    }

    public String getCartId() {
        return cartId;
    }

    public void setCartId(String cartId) {
        this.cartId = cartId;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
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
}
