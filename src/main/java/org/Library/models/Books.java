package org.Library.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table (name = "books")
public class Books {



    @Id
    @Column(name ="isbn",length = 50,nullable = false)
    private String isbn;

    @Column (name = "titolo",length = 250,nullable = false)
    private String title;

    @Column (name = "autore",length = 250)
    private String autore;

    @Column (name = "genere", length = 250)
    private String genre;

    @Column(name = "lingua", length = 250)
    private String lingua;

    @Column(name = "disponibili", length = 250,nullable = false)
    private int disponibili;

    @Column(name = "edizione", length = 250)
    private String edizione;

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getAutore() {
        return autore;
    }

    public void setAutore(String autore) {
        this.autore = autore;
    }

    public String getTitle() {
        return title;
    }

    public Books(String isbn,String autore, String title, String genre, String lingua, int disponibili, String edizione) {
        this.isbn = isbn;
        this.title = title;
        this.autore = autore;
        this.genre = genre;
        this.lingua = lingua;
        this.disponibili = disponibili;
        this.edizione = edizione;
    }

    public Books() {}

    public void setTitle(String title) {
        this.title = title;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getLingua() {
        return lingua;
    }

    public void setLingua(String lingua) {
        this.lingua = lingua;
    }

    public int getDisponibili() {
        return disponibili;
    }

    public void setDisponibili(int disponibili) {
        this.disponibili = disponibili;
    }

    public String getEdizione() {
        return edizione;
    }

    public void setEdizione(String edizione) {
        this.edizione = edizione;
    }



}
