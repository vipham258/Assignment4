package com.algonquincollege.cst8277.assignment4.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.NamedQuery;

/**
 * Description: Saving account The persistent class for the ACCOUNT database
 * table. Author(original): Mike Norman, Course materials (19F) CST 8277
 * 
 * Modified Date: November 2019
 * 
 * @author Vi Pham - Student 040-886-894
 */
@Entity
@DiscriminatorValue(value = "S")
@NamedQuery(name = "SavingAccount.findAll", query = "SELECT i FROM InvestmentAccount i")
public class SavingAccount extends AccountBase implements Serializable {
    private static final long serialVersionUID = 1L;

    protected double savingRate;

    /**
     * Description: get savingrate
     * 
     * @return savingRate
     * @author Vi Pham
     */
    @Column(name = "RATE")
    public double getSavingRate() {
        return savingRate;
    }

    /**
     * Description: set savingRate
     * 
     * @param savingRate
     * @author Vi Pham
     */
    public void setSavingRate(double savingRate) {
        this.savingRate = savingRate;
    }

}