package com.algonquincollege.cst8277.assignment3.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

/**
 * Description: The persistent class for the PORTFOLIO database table.
 * Author(original): Mike Norman, Course materials (19F) CST 8277
 * 
 * Modified Date: November 2019
 * 
 * @author Vi Pham - Student 040-886-894
 */

@Entity
//@NamedQuery(name = "Portfolio.findAll", query = "SELECT p FROM Portfolio p")
public class Portfolio extends ModelBase implements Serializable {
    private static final long serialVersionUID = 1L;

    private List<Asset> assets;

    public Portfolio() {
    }

    /**
     * Description: getId
     * 
     * @return id
     * @author Vi Pham
     */
    @Override
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PORTFOLIO_ID")
    public int getId() {
        return this.id;
    }

    /**
     * Description: getAssets
     * 
     * @return assets
     * @author Vi Pham
     */
    // bi-directional many-to-one association to Asset
    @OneToMany(mappedBy = "owingPortfolio")
    public List<Asset> getAssets() {
        return this.assets;
    }

    /**
     * Description: setAssets
     * 
     * @param assets
     * @author Vi Pham
     */
    public void setAssets(List<Asset> assets) {
        this.assets = assets;
    }

    /**
     * Description: add asset to portfolio
     * 
     * @return assets
     * @author Vi Pham
     */
    public Asset addAsset(Asset asset) {
        getAssets().add(asset);
        asset.setOwingPortfolio(this);

        return asset;
    }

    /**
     * Description: delete asset from portfolio
     * 
     * @return assets
     * @author Vi Pham
     */
    public Asset removeAsset(Asset asset) {
        getAssets().remove(asset);
        asset.setOwingPortfolio(null);

        return asset;
    }

}