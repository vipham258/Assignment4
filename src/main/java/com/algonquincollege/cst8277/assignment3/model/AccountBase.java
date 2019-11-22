package com.algonquincollege.cst8277.assignment3.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * Description: extend from ModelBase class, be the super class of Accounts
 * 
 * @author Vi Pham Student 040-886-894
 * 
 *         date: 2019-11-06
 */
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Table(name = "ACCOUNT")
@DiscriminatorColumn(name = "ACCOUNT_TYPE", length = 1)
public abstract class AccountBase extends ModelBase {
    private List<User> users;
    private Portfolio portfolio;

    /**
     * Description: getId
     * 
     * @return id
     * @author Vi Pham
     */
    @Override
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ACCOUNT_ID")
    public int getId() {
        return this.id;
    }

    /**
     * Description: get list of users
     * 
     * @return List of users
     * @author Vi Pham
     */
    // bi-directional many-to-many association to User
    @ManyToMany
    @JoinTable(name = "USER_ACCOUNT_ACCOUNT", joinColumns = { @JoinColumn(name = "ACCOUNT_ID") }, inverseJoinColumns = {
            @JoinColumn(name = "USER_ID") })
    public List<User> getUsers() {
        return this.users;
    }

    /**
     * Description: set users
     * 
     * @param users
     * @author Vi Pham
     */
    public void setUsers(List<User> users) {
        this.users = users;
    }

    /**
     * Description: get portfolio
     * 
     * @return portfolio
     * @author Vi Pham
     */
    @OneToOne
    @JoinColumn(name = "PORTFOLIO_ID")
    public Portfolio getPortfolio() {
        return this.portfolio;
    }

    /**
     * Description: set portfolio
     * 
     * @param portfolio
     * @author Vi Pham
     */
    public void setPortfolio(Portfolio portfolio) {
        this.portfolio = portfolio;
    }
}
