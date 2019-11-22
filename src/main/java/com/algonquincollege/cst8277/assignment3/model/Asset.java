package com.algonquincollege.cst8277.assignment3.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;

/**
 * Description: Asset The persistent class for the ASSET database table.
 * 
 * @author Vi Pham - Student 040-886-894
 */

@Entity
@NamedQuery(name = "Asset.findAll", query = "SELECT a FROM Asset a")
public class Asset implements Serializable {
    private static final long serialVersionUID = 1L;
    private int id;
    private Portfolio owingPortfolio;

    public Asset() {
    }

    /**
     * Description: getId
     * 
     * @return id
     * @author Vi Pham
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ASSET_ID")
    public int getId() {
        return this.id;
    }

    /**
     * Description: setId
     * 
     * @param id
     * @author Vi Pham
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Description: get portfolio
     * 
     * @return portfolio
     * @author Vi Pham
     */
    // bi-directional many-to-one association to GeneratePortfolio
    @ManyToOne
    @JoinColumn(name = "PORTFOLIO_ID")
    public Portfolio getOwingPortfolio() {
        return this.owingPortfolio;
    }

    /**
     * Description: set portfolio
     * 
     * @param owingPortfolio
     * @author Vi Pham
     */
    public void setOwingPortfolio(Portfolio owingPortfolio) {
        this.owingPortfolio = owingPortfolio;
    }

}