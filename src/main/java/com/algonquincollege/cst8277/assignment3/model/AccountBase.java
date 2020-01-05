/**
 * Created by: Parth Patel
 * Student Number: 040-751-954
 * Class Name : AccountBase.java
 * Date: 2019-11-15
 */

package com.algonquincollege.cst8277.assignment3.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

/**
 * The persistent class for the ACCOUNT database table.
 * 
 */
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Table(name = "ACCOUNT")
@DiscriminatorColumn(name = "ACCOUNT_TYPE", length = 1)
public abstract class AccountBase extends ModelBase {
    // Initialize the userAccounts List
    private List<User> userAccounts = new ArrayList<User>();

    /**
     * Create the Column ACCOUNT_ID in Account Table
     */
    @Override
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ACCOUNT_ID")
    public int getId() {
        return this.id;
    }

    /**
     * Bi-directional many-to-many association to User Table
     * 
     * @return userAccounts
     */
    @ManyToMany(mappedBy = "accounts", cascade = CascadeType.ALL)
    public List<User> getUserAccounts() {
        return this.userAccounts;
    }

    /**
     * Set User Accounts
     * 
     * @param userAccounts
     */
    public void setUserAccounts(List<User> userAccounts) {
        this.userAccounts = userAccounts;
    }

}
