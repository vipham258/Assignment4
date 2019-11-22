/**************************************************************G*********o****o****g**o****og**joob*********************
 * File: ModelBase.java
 * Course materials (19F) CST 8277
 * @author Mike Norman
 *
 * @date 2019 10
 */
package com.algonquincollege.cst8277.assignment4.model;

import javax.persistence.MappedSuperclass;
import javax.persistence.Version;

/**
 * Description: Abstract class that is base for all
 * com.algonquincollege.cst8277.assignment4 @Entity classes table.
 * Author(original): Mike Norman, Course materials (19F) CST 8277
 * 
 * Modified Date: November 2019
 * 
 * @author Vi Pham - Student 040-886-894
 */

@MappedSuperclass
public abstract class ModelBase implements BankEntity {

    protected int id;
    protected double balance;
    protected int version;

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getBalance() {
        return this.balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    @Version
    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }
}