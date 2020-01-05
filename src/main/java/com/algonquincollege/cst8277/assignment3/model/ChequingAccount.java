/**
 * Created by: Parth Patel
 * Student number: 040751954
 * class name: ChequingAccount.java
 */
package com.algonquincollege.cst8277.assignment3.model;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The persistent class for the ACCOUNT database table.
 * 
 */
@Entity
@DiscriminatorValue(value = "C")
//@NamedQuery(name="InvestmentAccount.findAll", query="SELECT i FROM InvestmentAccount i")
public class ChequingAccount extends AccountBase implements Serializable {
    private static final long serialVersionUID = 1L;
    // Declare savingRate variable with type Double
    protected double savingRate;

    /**
     * Create the Column RATE in Account Tabele
     * 
     * @return savingRate -get the saving rate for savingAcoount
     */
    @Column(name = "RATE")
    public double getSavingRate() {
        return savingRate;
    }

    /**
     * Set the saving rate for savingAccount
     * 
     * @param savingRate
     */
    public void setSavingRate(double savingRate) {
        this.savingRate = savingRate;
    }

}