package com.algonquincollege.cst8277.assignment3.model;

import java.io.Serializable;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * Description: Investment account The persistent class for the ACCOUNT database
 * table. Author(original): Mike Norman, Course materials (19F) CST 8277
 * 
 * Modified Date: November 2019
 * 
 * @author Vi Pham - Student 040-886-894
 */

@Entity
@Table(name = "ACCOUNT")
@DiscriminatorValue(value = "I")
//@NamedQuery(name = "InvestmentAccount.findAll", query = "SELECT i FROM InvestmentAccount i")
public class InvestmentAccount extends AccountBase implements Serializable {
    private static final long serialVersionUID = 1L;

    private Portfolio portfolio;

    public InvestmentAccount() {
    }

    // uni-directional one-to-one association to Portfolio
    @OneToOne
    @JoinColumn(name = "PORTFOLIO_ID")
    public Portfolio getPortfolio() {
        return this.portfolio;
    }

    public void setPortfolio(Portfolio portfolio) {
        this.portfolio = portfolio;
    }

}