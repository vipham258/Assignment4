/**************************************************************G*********o****o****g**o****og**joob*********************
 * File: ModelBase.java
 * Course materials (19F) CST 8277
 * @author Mike Norman
 *
 * @date 2019 10
 */
package com.algonquincollege.cst8277.assignment4.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
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
    protected LocalDateTime createDate;

    @Column(name = "created_date")
    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    @Column(name = "updated_date")
    public LocalDateTime getUpdateDate() {
        return updateDate;
    }

    @PrePersist
    public void onPersist() {
        setCreateDate(LocalDateTime.now());
    }

    @PreUpdate
    public void onUpdate() {
        setUpdateDate(LocalDateTime.now());
    }

    public void setUpdateDate(LocalDateTime updateDate) {
        this.updateDate = updateDate;
    }

    protected LocalDateTime updateDate;

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