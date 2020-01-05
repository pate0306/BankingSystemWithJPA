/**
 * 
 * Created by: Parth Patel
 * Student Number: 040751954
 * Class: InvestmentAccount.java
 * Date: 2019-11-15
 * 
 */

package com.algonquincollege.cst8277.assignment3.model;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The persistent class for the ACCOUNT database table.
 * 
 */
@Entity
@DiscriminatorValue(value = "I")
//@NamedQuery(name="InvestmentAccount.findAll", query="SELECT i FROM InvestmentAccount i")
public class InvestmentAccount extends AccountBase implements Serializable {
    private static final long serialVersionUID = 1L;
    // Declare accountType variable with type String
    private String accountType;
    // Declare portfolio variable with type Portfolio
    private Portfolio portfolio;

    public InvestmentAccount() {
    }

    /**
     * Create the Column ACCOUNT_TYPE in Account Table
     * 
     * @return accounttype
     */
    @Column(name = "ACCOUNT_TYPE", insertable = false, updatable = false)
    public String getAccountType() {
        return this.accountType;
    }

    /**
     * Set the Accounttype
     * 
     * @param accountType
     */
    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    /**
     * Uni-directional one-to-one association to Portfolio Table
     * 
     * @return portfolio
     */
    @OneToOne
    @JoinColumn(name = "PORTFOLIO_ID")
    public Portfolio getPortfolio() {
        return this.portfolio;
    }

    /**
     * Set the Portfolio
     * 
     * @param portfolio
     */
    public void setPortfolio(Portfolio portfolio) {
        this.portfolio = portfolio;
    }

}