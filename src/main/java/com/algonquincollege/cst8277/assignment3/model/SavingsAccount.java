
/**
 * Created by : Parth Patel
 * Student number: 040751954
 * class: SavingsAccount.java
 * Date: 2019-11-15
 */
package com.algonquincollege.cst8277.assignment3.model;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The persistent class for the ACCOUNT database table.
 * 
 */
@Entity
@DiscriminatorValue(value = "S")
//@NamedQuery(name="InvestmentAccount.findAll", query="SELECT i FROM InvestmentAccount i")
public class SavingsAccount extends AccountBase implements Serializable {
    private static final long serialVersionUID = 1L;
    // Declare savinRate variable with type double
    protected double savingRate;

    /**
     * Create the Column RATE in Account Table
     * 
     * @return savingRate
     */
    @Column(name = "RATE")
    public double getSavingRate() {
        return savingRate;
    }

    /**
     * Set savingRate
     * 
     * @param savingRate
     */

    public void setSavingRate(double savingRate) {
        this.savingRate = savingRate;
    }

}