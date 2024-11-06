package org.Library.models;
import javax.persistence.*;

@Entity
@Table (name= "admin")
public class Admin {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column (name = "username",length = 50,nullable = false)
    private String username;

    @Column(name = "password", length = 50,nullable = false)
    private String password;

    @Column (name = "idBiblioteca",length = 50,nullable = false)
    private String idBiblioteca;


    @OneToOne (cascade = CascadeType.REMOVE)
    @JoinColumn(name ="userCart",referencedColumnName = "cartid",nullable = false)
    private Users users;

    public Long getId() {
        return id;
    }

    public String getIdBiblotecca() {
        return idBiblioteca;
    }

    public void setIdBiblotecca(String idBiblotecca) {
        this.idBiblioteca = idBiblotecca;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Users getUsers() {
        return users;
    }

    public void setUsers(Users users) {
        this.users = users;
    }

    public Admin(String idBiblioteca, String username, String password, Users users) {
        this.username = username;
        this.password = password;
        this.idBiblioteca = idBiblioteca;
        this.users = users;
    }

    public Admin() {}
}
