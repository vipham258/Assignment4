package com.algonquincollege.cst8277.assignment3.model;

import java.io.Serializable;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 * Description: Chequing account The persistent class for the ACCOUNT database
 * 
 * Author(original): Mike Norman, Course materials (19F) CST 8277
 * 
 * Modified Date: November 2019
 * 
 * @author Vi Pham - Student 040-886-894
 */
@Entity
@DiscriminatorValue(value = "C")
//@NamedQuery(name = "InvestmentAccount.findAll", query = "SELECT i FROM InvestmentAccount i")
public class ChequingAccount extends AccountBase implements Serializable {
    private static final long serialVersionUID = 1L;

}