/**
 * 
 * Created by :Parth Patel
 * Student Number : 040751954
 * class: Portfolio.java
 */
package com.algonquincollege.cst8277.assignment3.model;

import java.io.Serializable;
import javax.persistence.*;

import java.util.ArrayList;
import java.util.List;

/**
 * The persistent class for the PORTFOLIO database table.
 * 
 */
@Entity
//@NamedQuery(name="Portfolio.findAll", query="SELECT p FROM Portfolio p")
public class Portfolio extends ModelBase implements Serializable {
    private static final long serialVersionUID = 1L;
    // Initialize the assets List
    private List<Asset> assets = new ArrayList<>();

    public Portfolio() {
    }

    /**
     * Create the Column PORTFOLIO_ID in Portfolio Table
     */
    @Override
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PORTFOLIO_ID")
    public int getId() {
        return this.id;
    }

    /**
     * bi-directional many-to-one association to Asset
     * 
     * @return assets - Getting Assets related to Portfolio
     */
    @OneToMany(mappedBy = "portfolio", cascade = CascadeType.ALL)
    public List<Asset> getAssets() {
        return this.assets;
    }

    /**
     * set Assets
     * 
     * @param assets
     */
    public void setAssets(List<Asset> assets) {
        this.assets = assets;
    }

}