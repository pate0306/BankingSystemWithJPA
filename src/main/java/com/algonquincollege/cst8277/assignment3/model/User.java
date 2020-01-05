/**
 * Created by : Parth Patel
 * Student number: 040751954
 * class: User.java
 * Date: 2019-11-15
 */

package com.algonquincollege.cst8277.assignment3.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;

/**
 * The persistent class for the USER_ACCOUNT database table.
 * 
 */
@Entity
@Table(name = "USER_ACCOUNT")
@NamedQuery(name = "User.findAll", query = "SELECT u FROM User u")
public class User extends ModelBase implements Serializable {
    private static final long serialVersionUID = 1L;
    // Declare variable id with type int
    private int id;
    // Declare the accounts List wity type AccountBase
    private List<AccountBase> accounts;

    public User() {
    }

    /**
     * Create the Column USER_ID in User Table
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "USER_ID")
    public int getId() {
        return this.id;
    }

    /**
     * bi-directional many-to-many association to InvestmentAccount
     * 
     * @return accounts
     */
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "USER_ACCOUNT_ACCOUNT", joinColumns = { @JoinColumn(name = "USER_ID") }, inverseJoinColumns = {
            @JoinColumn(name = "ACCOUNT_ID") })
    public List<AccountBase> getAccounts() {
        return this.accounts;
    }

    /**
     * Set User Accounts
     * 
     * @param accounts
     */
    public void setAccounts(List<AccountBase> accounts) {
        this.accounts = accounts;
    }

}