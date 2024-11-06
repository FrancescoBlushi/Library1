package org.Library.models;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table (name = "users")
public class Users {
    @Id
    @Column (name = "cartId", length = 50, nullable = false)
    String cartId;

    @Column (name = "name", length = 250,nullable = false)
    String name;

    @Column (name ="lastname", length = 250,nullable = false)
    String lastname;

    public Users(String cartId,String name, String lastname) {
        this.name = name;
        this.lastname = lastname;
        this.cartId = cartId;
    }
    public Users() {}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }



    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getCartId() {
        return cartId;
    }

    public void setCartId(String cartId) {
        this.cartId = cartId;
    }
}


