package com.algonquincollege.cst8277.assignment3.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * Description: User The persistent class for the USER_ACCOUNT database table.
 * 
 * @author Vi Pham - Student 040-886-894
 */

@Entity
@Table(name = "USER_ACCOUNT")
@NamedQuery(name = "User.findAll", query = "SELECT u FROM User u")
public class User implements Serializable {
    private static final long serialVersionUID = 1L;
    private int id;
    private List<AccountBase> bankAccounts;

    public User() {
    }

    /**
     * Description: getId
     * 
     * @return id
     * @author Vi Pham
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "USER_ID")
    public int getId() {
        return this.id;
    }

    /**
     * Description: set ID
     * 
     * @param id
     * @author Vi Pham
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Description: get Bank account
     * 
     * @return bankAccounts
     * @author Vi Pham
     */
    // bi-directional many-to-many association to GenerateAccountBase
    @ManyToMany(mappedBy = "users")
    public List<AccountBase> getBankAccounts() {
        return this.bankAccounts;
    }

    /**
     * Description: set bankAccount
     * 
     * @param bankAccounts
     * @author Vi Pham
     */
    public void setBankAccounts(List<AccountBase> bankAccounts) {
        this.bankAccounts = bankAccounts;
    }

}