/**************************************************************G*********o****o****g**o****og**joob*********************
 * File: ModelBase.java
 * Course materials (19F) CST 8277
 * @author Mike Norman
 *
 * @date 2019 10
 */
/**
 * 
 * Updated by : Parth Patel
 * Student Number: 040751954
 * Class: InvestmentAccount.java
 * Date: 2019-11-15
 * 
 */

package com.algonquincollege.cst8277.assignment3.model;

import javax.persistence.MappedSuperclass;
import javax.persistence.Version;

/**
 * Abstract class that is base for all
 * com.algonquincollege.cst8277.assignment3 @Entity classes
 */
@MappedSuperclass
public abstract class ModelBase implements BankEntity {
    // Declare id variable with type int
    protected int id;
    // Declare balance variable with type double
    protected double balance;
    // Declare version variable with type int
    protected int version;

    /**
     * Get id
     * 
     * @return id
     */
    public int getId() {
        return this.id;
    }

    /**
     * Set id
     * 
     * @param id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Get balance
     * 
     * @return balance
     */
    public double getBalance() {
        return this.balance;
    }

    /**
     * Set balance
     * 
     * @param balance
     */
    public void setBalance(double balance) {
        this.balance = balance;
    }

    /**
     * Get version
     * 
     * @return version
     */
    @Version
    public int getVersion() {
        return version;
    }

    /**
     * Set version
     * 
     * @param version
     */
    public void setVersion(int version) {
        this.version = version;
    }
}