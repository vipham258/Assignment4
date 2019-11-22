package com.algonquincollege.cst8277.assignment4.model;

/**
 * Description: BankAccount interface
 * 
 * @author: Mike Norman, Course materials (19F) CST 8277
 */
public interface BankAccount extends BankEntity {

    public double getBalance();

    public void setBalance(double balance);

}