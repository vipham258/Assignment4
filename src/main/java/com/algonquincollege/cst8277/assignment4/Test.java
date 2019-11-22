/**************************************************************G*********o****o****g**o****og**joob*********************
 * File: Test.java
 * Course materials (19F) CST 8277
 * @author Mike Norman
 *
 * @date 2019 10
 */
package com.algonquincollege.cst8277.assignment4;

import java.lang.invoke.MethodHandles;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Test {

    private static final Class<?> _thisClaz = MethodHandles.lookup().lookupClass();
    private static final Logger logger = LoggerFactory.getLogger(_thisClaz);

    public static final String ASSIGNMENT4_PU_NAME = "Assignment4-main-PU";

    public static void main(String[] args) {
        logger.info("setup JPA EntityManagerFactory, create EntityManager (Session)");
        EntityManagerFactory emf = Persistence.createEntityManagerFactory(ASSIGNMENT4_PU_NAME);
        EntityManager em = emf.createEntityManager();
        em.close();
        emf.close();
    }
}
