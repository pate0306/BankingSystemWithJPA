/**
 * Created by: Parth Patel
 * Student Number: 040-751-954
 * Class Name : Asset.java
 * Date: 2019-11-15
 */

package com.algonquincollege.cst8277.assignment3.model;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The persistent class for the ASSET database table.
 * 
 */
@Entity
@Table(name = "ASSET")
//@NamedQuery(name="Asset.findAll", query="SELECT a FROM Asset a")
public class Asset extends ModelBase implements Serializable {
    private static final long serialVersionUID = 1L;
    protected int id;
    protected Portfolio portfolio;

    public Asset() {
    }

    /**
     * Create the Column ASSET_ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ASSET_ID")
    public int getId() {

        return this.id;
    }

    /**
     * Set Asset Id
     * 
     * @param id - AssetId
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * bi-directional many-to-one association to Portfolio Table
     * 
     * @return Portfolio
     */
    @ManyToOne()
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