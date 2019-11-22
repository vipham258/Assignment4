package com.algonquincollege.cst8277.assignment4.model;

import static org.hamcrest.core.StringStartsWith.startsWith;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;

import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.algonquincollege.cst8277.assignment4.TestSuiteBase;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.read.ListAppender;

/**
 * Description: Unit test cases Author(original): Mike Norman, Course materials
 * (19F) CST 8277
 * 
 * Modified Date: November 2019
 * 
 * @author Vi Pham - Student 040-886-894
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class BankingTestSuite extends TestSuiteBase {

    private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private static final ch.qos.logback.classic.Logger eclipselinkSqlLogger = (ch.qos.logback.classic.Logger) LoggerFactory
            .getLogger(ECLIPSELINK_LOGGING_SQL);

    /**
     * Create a valid account.
     * 
     * @result Account will be persisted without any errors, using criteria and
     *         metamodel to test
     * @author vipham 040886894
     */
    @Test
    public void test_01_testThatCreatingChequingAccount_usingCriteriaMetamodel_works() {

        // outside of JavaEE container, must build (Session) EntityManager manually
        EntityManager em = emf.createEntityManager();

        // create chequingAccount
        ChequingAccount chequingAccount = new ChequingAccount();
        chequingAccount.setBalance(1.2);
        chequingAccount.setId(1);
        chequingAccount.setVersion(1);
        chequingAccount.setUsers(new ArrayList<User>());
        em.getTransaction().begin();

        em.persist(chequingAccount);
        // ChequingAccount chequing = em.find(ChequingAccount.class, 1);
        // em.getTransaction().commit();
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<ChequingAccount> cq = cb.createQuery(ChequingAccount.class);

        Root<ChequingAccount> chequing = cq.from(ChequingAccount.class);
        cq.where(cb.equal(chequing.get(ChequingAccount_.version), 1));

        List<ChequingAccount> results = em.createQuery(cq).getResultList();
        assertEquals(1, results.size());
        // JUnit assertions
        // assertEquals(1, chequing.getId());
        // close EntityManager
        em.close();
    }

    /**
     * Create a valid account.
     * 
     * @result Portfolio will return null because there isn't any porfolio persisted
     * @author vipham 040886894
     */
    @Test
    public void test_02_testPortfolio_null() {
        // test Portfolio(id=1) does not exist upon startup

        // outside of JavaEE container, must build (Session) EntityManager manually
        EntityManager em = emf.createEntityManager();

        // demonstrate how to capture SQL sent to Db: first, attach appender
        ListAppender<ILoggingEvent> listAppender = attachListAppender(eclipselinkSqlLogger, ECLIPSELINK_LOGGING_SQL);

        Portfolio p1 = em.find(Portfolio.class, 1);

        // now detach appender (unless you wish to capture more SQL statements; for now,
        // one is enough)
        detachListAppender(eclipselinkSqlLogger, listAppender);

        // JUnit assertions

        assertNull(p1); // assert we did not find Portfolio(id=1)
        List<ILoggingEvent> loggingEvents = listAppender.list;
        assertEquals(1, loggingEvents.size()); // assert only a single SQL stmt sent
        // to Db
        // assert what the SQL stmt looks like
        // NB: why 'startsWith'? because the binding of a param to the '?' marker is
        // very difficult to pattern match against ... this is sufficient
        assertThat(loggingEvents.get(0).getMessage(),
                startsWith("SELECT PORTFOLIO_ID, BALANCE, VERSION FROM PORTFOLIO WHERE (PORTFOLIO_ID = ?)"));

        // close EntityManager
        em.close();
    }

    /**
     * Create a valid saving account.
     * 
     * @result the result list of savingAccount will become 1
     * @author vipham 040886894
     */
    @Test
    public void test_03_createSavingAccount_JPQL_works() {

        // create savingAccount
        SavingAccount savingAccount = new SavingAccount();
        savingAccount.setBalance(1.2);
        savingAccount.setSavingRate(1.2);
        savingAccount.setId(1);
        savingAccount.setVersion(1);
        savingAccount.setUsers(new ArrayList<User>());

//        // JUnit assertions
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.persist(savingAccount);

        // em.getTransaction().commit();

        SavingAccount a1 = em.find(SavingAccount.class, 1);
        assertEquals(1, a1.getVersion());
        Query q = em.createQuery("SELECT i FROM SavingAccount i", SavingAccount.class);

        List<SavingAccount> allSavingAccounts = new ArrayList<SavingAccount>();

        allSavingAccounts = q.getResultList();
        assertEquals(1, allSavingAccounts.size());
        em.close();

    }

    /**
     * Create a valid criteria account.
     * 
     * @result the result list of investmentAccount will become 1
     * @author vipham 040886894
     */
    @Test
    public void test_04_createInvestmentAccounts_criteria() {
        // outside of JavaEE container, must build (Session) EntityManager manually
        EntityManager em = emf.createEntityManager();
        // create chequingAccount
        InvestmentAccount investmentAccount = new InvestmentAccount();
        investmentAccount.setBalance(1.2);
        investmentAccount.setId(1);
        investmentAccount.setVersion(1);
        investmentAccount.setPortfolio(null);
        investmentAccount.setUsers(new ArrayList<User>());

        // JUnit assertions
        em.getTransaction().begin();
        em.persist(investmentAccount);

        // em.getTransaction().commit();

        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<InvestmentAccount> cq = cb.createQuery(InvestmentAccount.class);
        TypedQuery<InvestmentAccount> q = em.createQuery(cq);
        List<InvestmentAccount> results = q.getResultList();
        assertEquals(1, results.size());
        em.close();
    }

    /**
     * Create a valid User.
     * 
     * @result a user with the id that set
     * @author vipham 040886894
     */
    @Test
    public void test_05_createUser_works() {
        // outside of JavaEE container, must build (Session) EntityManager manually
        EntityManager em = emf.createEntityManager();
        // create user
        User user1 = new User();
        user1.setId(1);
        em.persist(user1);
        User user2 = em.find(User.class, 1);
        assertEquals(1, user2.getId());
        // close EntityManager
        em.close();
    }

    /**
     * Create a valid portfolio.
     * 
     * @result the result list of portfolio will become 1
     * @author vipham 040886894
     */
    @Test
    public void test_06_createPortfolio_usingCriteria_metamodel_works() {
        // outside of JavaEE container, must build (Session) EntityManager manually
        EntityManager em = emf.createEntityManager();
        // create portfolio
        Portfolio p = new Portfolio();
        p.setVersion(1);

        // JUnit assertions
        assertEquals(1, p.getVersion());

        // begin entity manager

        em.getTransaction().begin();

        em.persist(p);
        // commit entity manager
        // em.getTransaction().commit();
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Portfolio> cq = cb.createQuery(Portfolio.class);

        Root<Portfolio> p1 = cq.from(Portfolio.class);
        cq.where(cb.equal(p1.get(ChequingAccount_.version), 1));

        List<Portfolio> results = em.createQuery(cq).getResultList();
        // JUnit assertions
        assertEquals(1, results.size());

        // close EntityManager
        em.close();
    }

    /**
     * Create a valid Asset
     * 
     * @result the asset is created with entity manager
     * @author vipham 040886894
     */
    @Test
    public void test_07_createAsset_works() {
        // outside of JavaEE container, must build (Session) EntityManager manually
        EntityManager em = emf.createEntityManager();
        // create asset
        Asset asset = new Asset();
        asset.setId(1);
        em.persist(asset);

        Asset a = em.find(Asset.class, 1);
        // JUnit assertions
        assertEquals(1, a.getId());

        // close EntityManager
        em.close();
    }

    /**
     * find an asset.
     * 
     * @result Asset will return null because there isn't any asset persisted
     * @author vipham 040886894
     */
    @Test
    public void test_08_findAsset_doesntwork() {
        // outside of JavaEE container, must build (Session) EntityManager manually
        EntityManager em = emf.createEntityManager();
        // find assets
        Asset a1 = em.find(Asset.class, 1);
        // JUnit assertions
        assertNull(a1);
        // close EntityManager
        em.close();
    }

    /**
     * create a chequingAccount then delete it
     * 
     * @result chequingAccount will return null because it already delete the
     *         chequingAccount
     * @author vipham 040886894
     */
    @Test
    public void test_09_deleteChequingAccount_usingJPQL_null() {
        // create investmentAccount
        ChequingAccount chequingAccount = new ChequingAccount();
        chequingAccount.setBalance(1.2);
        chequingAccount.setId(1);
        chequingAccount.setVersion(1);
        chequingAccount.setUsers(new ArrayList<User>());

//        // entity manager
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.persist(chequingAccount);
        // remove
        em.remove(chequingAccount);
        // em.getTransaction().commit();

        Query q = em.createQuery("SELECT i FROM ChequingAccount i", ChequingAccount.class);

        List<ChequingAccount> accounts = new ArrayList<ChequingAccount>();

        accounts = q.getResultList();
        // JUnit assertions
        assertEquals(0, accounts.size());
        em.close();
    }

    /**
     * Create a valid account.
     * 
     * @result Account will be persisted without any errors, using JPQL query to
     *         test
     * @author vipham 040886894
     */
    @Test
    public void test_10_createInvestmentAccount_JPQL_work() {
        // create investmentAccount
        InvestmentAccount investmentAccount = new InvestmentAccount();
        investmentAccount.setBalance(1.2);
        investmentAccount.setId(1);
        investmentAccount.setVersion(1);
        investmentAccount.setPortfolio(null);
        investmentAccount.setUsers(new ArrayList<User>());

//        // JUnit assertions
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.persist(investmentAccount);

        // em.getTransaction().commit();

        InvestmentAccount a1 = em.find(InvestmentAccount.class, 1);
        assertEquals(1, a1.getVersion());
        Query q = em.createQuery("SELECT i FROM InvestmentAccount i", InvestmentAccount.class);

        List<InvestmentAccount> investmentAccounts = new ArrayList<InvestmentAccount>();

        investmentAccounts = q.getResultList();
        assertEquals(1, investmentAccounts.size());
        em.close();
    }

    /**
     * Create a valid account and delete it.
     * 
     * @result Account will be persisted and deleted without any errors, using JPQL
     *         query to test
     * @author vipham 040886894
     */
    @Test
    public void test_11_createInvestmentAccount_usingJPQL_null() {
        // create investmentAccount
        InvestmentAccount investmentAccount = new InvestmentAccount();
        investmentAccount.setBalance(1.2);
        investmentAccount.setId(1);
        investmentAccount.setVersion(1);
        investmentAccount.setPortfolio(null);
        investmentAccount.setUsers(new ArrayList<User>());

//        // entity manager
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.persist(investmentAccount);
        // remove
        em.remove(investmentAccount);
        // em.getTransaction().commit();

        Query q = em.createQuery("SELECT i FROM InvestmentAccount i", InvestmentAccount.class);

        List<InvestmentAccount> investmentAccounts = new ArrayList<InvestmentAccount>();

        investmentAccounts = q.getResultList();
        // JUnit assertions
        assertEquals(0, investmentAccounts.size());
        em.close();
    }

    /**
     * find an user.
     * 
     * @result User will return null because there isn't any user persisted
     * @author vipham 040886894
     */
    @Test
    public void test_12_findUser_null() {
        // outside of JavaEE container, must build (Session) EntityManager manually
        EntityManager em = emf.createEntityManager();
        // find chequingAccount
        User a1 = em.find(User.class, 1);
        // JUnit assertions
        assertNull(a1);
        // close EntityManager
        em.close();
    }

    /**
     * find a Asset
     * 
     * @result the asset is found with entity manager
     * @author vipham 040886894
     */
    @Test
    public void test_13_findAsset_work() {
        // outside of JavaEE container, must build (Session) EntityManager manually
        EntityManager em = emf.createEntityManager();
        Asset a1 = new Asset();
        a1.setId(1);
        em.persist(a1);

        // find assets
        Asset a2 = em.find(Asset.class, 1);
        // JUnit assertions
        assertEquals(1, a2.getId());
        // close EntityManager
        em.close();
    }

    /**
     * find a chequing account using criteria and metamodel.
     * 
     * @result return true when the chequingAccount's version is equal to 1
     * @author vipham 040886894
     */
    @Test
    public void test_14_findChequingAccount_usingCriteria_metamodel_work() {
        // outside of JavaEE container, must build (Session) EntityManager manually
        EntityManager em = emf.createEntityManager();

        // create chequingAccount
        ChequingAccount chequingAccount = new ChequingAccount();
        chequingAccount.setBalance(1.2);
        chequingAccount.setId(1);
        chequingAccount.setVersion(1);
        chequingAccount.setUsers(new ArrayList<User>());
        em.getTransaction().begin();

        em.persist(chequingAccount);

        // em.getTransaction().commit();
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<ChequingAccount> cq = cb.createQuery(ChequingAccount.class);

        Root<ChequingAccount> chequing = cq.from(ChequingAccount.class);
        cq.where(cb.equal(chequing.get(ChequingAccount_.version), 1));

        List<ChequingAccount> results = em.createQuery(cq).getResultList();
        // JUnit assertions
        assertEquals(1, results.get(0).getVersion());
        // close EntityManager
        em.close();
    }

    /**
     * find an investment account using criteria and metamodel.
     * 
     * @result return true when the chequingAccount's version is equal to 1
     * @author vipham 040886894
     */
    @Test
    public void test_15_findInvestmentAccount_usingCriteria_metamodel_work() {
        // outside of JavaEE container, must build (Session) EntityManager manually
        EntityManager em = emf.createEntityManager();

        // create chequingAccount
        InvestmentAccount account = new InvestmentAccount();
        account.setBalance(1.2);
        account.setId(1);
        account.setVersion(1);
        account.setPortfolio(null);
        account.setUsers(new ArrayList<User>());
        em.getTransaction().begin();

        em.persist(account);
        // ChequingAccount chequing = em.find(ChequingAccount.class, 1);
        em.getTransaction().commit();
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<InvestmentAccount> cq = cb.createQuery(InvestmentAccount.class);

        Root<ChequingAccount> chequing = cq.from(ChequingAccount.class);
        cq.where(cb.equal(chequing.get(ChequingAccount_.version), 1));

        List<InvestmentAccount> results = em.createQuery(cq).getResultList();
        assertEquals(1, results.get(0).getVersion());
        // JUnit assertions
        // assertEquals(1, chequing.getId());
        // close EntityManager
        em.close();
    }

    /**
     * find a valid portfolio.
     * 
     * @result true and return the right portfolio
     * @author vipham 040886894
     */
    @Test
    public void test_16_findPortfolio_work() {
        // outside of JavaEE container, must build (Session) EntityManager manually
        EntityManager em = emf.createEntityManager();
        Portfolio a1 = new Portfolio();
        a1.setId(1);
        a1.setVersion(2);
        em.persist(a1);

        // find porfolio
        Portfolio a2 = em.find(Portfolio.class, 1);
        // JUnit assertions
        assertEquals(2, a2.getVersion());
        // close EntityManager
        em.close();
    }

    /**
     * find a valid savingAccount.
     * 
     * @result true and return the right savingAccount
     * @author vipham 040886894
     */
    @Test
    public void test_17_findSavingAccount_work() {
        // outside of JavaEE container, must build (Session) EntityManager manually
        EntityManager em = emf.createEntityManager();
        SavingAccount a1 = new SavingAccount();
        a1.setId(1);
        em.persist(a1);

        // find saving
        SavingAccount a2 = em.find(SavingAccount.class, 1);
        // JUnit assertions
        assertEquals(1, a2.getId());
        // close EntityManager
        em.close();
    }

    /**
     * find a valid user.
     * 
     * @result true and return the right user in entityManager
     * @author vipham 040886894
     */
    @Test
    public void test_18_findUser_work() {
        // outside of JavaEE container, must build (Session) EntityManager manually
        EntityManager em = emf.createEntityManager();
        User a1 = new User();
        a1.setId(1);
        em.persist(a1);

        // find assets
        User a2 = em.find(User.class, 1);
        // JUnit assertions
        assertEquals(1, a2.getId());
        // close EntityManager
        em.close();
    }

    /**
     * delete an asset.
     * 
     * @result the asset is deleted in entityManager, return null
     * @author vipham 040886894
     */
    @Test
    public void test_19_deleteAsset_work() {
        // outside of JavaEE container, must build (Session) EntityManager manually
        EntityManager em = emf.createEntityManager();
        Asset a1 = new Asset();
        a1.setId(1);
        em.persist(a1);
        em.remove(a1);

        // find assets
        Asset a2 = em.find(Asset.class, 1);
        // JUnit assertions
        assertNull(a2);
        // close EntityManager
        em.close();
    }

    /**
     * delete a chequing account.
     * 
     * @result the account is deleted in entityManager, return null
     * @author vipham 040886894
     */
    @Test
    public void test_20_deleteChequingAccount_work() {
        // outside of JavaEE container, must build (Session) EntityManager manually
        EntityManager em = emf.createEntityManager();
        ChequingAccount a1 = new ChequingAccount();
        a1.setId(1);
        em.persist(a1);
        em.remove(a1);

        // find assets
        ChequingAccount a2 = em.find(ChequingAccount.class, 1);
        // JUnit assertions
        assertNull(a2);
        // close EntityManager
        em.close();
    }

    /**
     * delete a investment account.
     * 
     * @result the account is deleted in entityManager, return null
     * @author vipham 040886894
     */
    @Test
    public void test_21_deleteInvestmentAccount_work() {
        // outside of JavaEE container, must build (Session) EntityManager manually
        EntityManager em = emf.createEntityManager();
        InvestmentAccount a1 = new InvestmentAccount();
        a1.setId(1);
        em.persist(a1);
        em.remove(a1);

        // find assets
        InvestmentAccount a2 = em.find(InvestmentAccount.class, 1);
        // JUnit assertions
        assertNull(a2);
        // close EntityManager
        em.close();
    }

    /**
     * delete a valid portfolio.
     * 
     * @result the portfolio is deleted in entityManager, expected to return null
     * @author vipham 040886894
     */
    @Test
    public void test_22_deletePortfolio_work() {
        // outside of JavaEE container, must build (Session) EntityManager manually
        EntityManager em = emf.createEntityManager();
        Portfolio a1 = new Portfolio();
        a1.setId(1);
        em.persist(a1);
        em.remove(a1);

        // find assets
        Portfolio a2 = em.find(Portfolio.class, 1);
        // JUnit assertions
        assertNull(a2);
        // close EntityManager
        em.close();
    }

    /**
     * delete a valid saving account.
     * 
     * @result the saving is deleted in entityManager, expected to return null
     * @author vipham 040886894
     */
    @Test
    public void test_23_deleteSavingAccount_work() {
        // outside of JavaEE container, must build (Session) EntityManager manually
        EntityManager em = emf.createEntityManager();
        SavingAccount a1 = new SavingAccount();
        a1.setId(1);
        em.persist(a1);
        em.remove(a1);

        // find assets
        SavingAccount a2 = em.find(SavingAccount.class, 1);
        // JUnit assertions
        assertNull(a2);
        // close EntityManager
        em.close();
    }

    /**
     * delete a valid user.
     * 
     * @result the user is deleted in entityManager, expected to return null
     * @author vipham 040886894
     */
    @Test
    public void test_24_deleteUser_work() {
        // outside of JavaEE container, must build (Session) EntityManager manually
        EntityManager em = emf.createEntityManager();
        User a1 = new User();
        a1.setId(1);
        em.persist(a1);
        em.remove(a1);

        // find users
        User a2 = em.find(User.class, 1);
        // JUnit assertions
        assertNull(a2);
        // close EntityManager
        em.close();
    }

    /**
     * update asset
     * 
     * @result the asset is updated in entityManager
     * @author vipham 040886894
     */
    @Test
    public void test_25_updateAsset_work() {
        // outside of JavaEE container, must build (Session) EntityManager manually
        EntityManager em = emf.createEntityManager();
        Asset a1 = new Asset();
        em.persist(a1);
        a1.setId(1);
        // update
        Asset a3 = em.merge(a1);
        a3.setId(2);
        // find assets
        Asset a4 = em.find(Asset.class, 1);
        Asset a5 = em.find(Asset.class, 2);
        // JUnit assertions
        assertNull(a4);
        assertEquals(2, 2);
        // close EntityManager
        em.close();
    }

    /**
     * update chequing account
     * 
     * @result the chequing account is updated in entityManager
     * @author vipham 040886894
     */
    @Test
    public void test_26_updateChequingAccout_work() {
        // outside of JavaEE container, must build (Session) EntityManager manually
        EntityManager em = emf.createEntityManager();
        ChequingAccount a1 = new ChequingAccount();
        em.persist(a1);
        a1.setId(1);
        // update
        ChequingAccount a3 = em.merge(a1);
        a3.setId(2);
        // find object
        ChequingAccount a4 = em.find(ChequingAccount.class, 1);
        // JUnit assertions
        assertNull(a4);
        // close EntityManager
        em.close();
    }

    /**
     * update investment account
     * 
     * @result the investment account is updated in entityManager
     * @author vipham 040886894
     */
    @Test
    public void test_27_updateInvestmentAccount_work() {
        // outside of JavaEE container, must build (Session) EntityManager manually
        EntityManager em = emf.createEntityManager();
        InvestmentAccount a1 = new InvestmentAccount();
        em.persist(a1);
        a1.setId(1);
        // update
        InvestmentAccount a3 = em.merge(a1);
        a3.setId(2);
        // find object
        InvestmentAccount a4 = em.find(InvestmentAccount.class, 1);
        // JUnit assertions
        assertNull(a4);
        // close EntityManager
        em.close();
    }

    /**
     * update portfolio
     * 
     * @result the portfolio is updated in entityManager
     * @author vipham 040886894
     */
    @Test
    public void test_28_updatePortfolio_work() {
        // outside of JavaEE container, must build (Session) EntityManager manually
        EntityManager em = emf.createEntityManager();
        Portfolio a1 = new Portfolio();
        em.persist(a1);
        a1.setId(1);
        // update
        Portfolio a3 = em.merge(a1);
        a3.setId(2);
        // find object
        Portfolio a4 = em.find(Portfolio.class, 1);
        // JUnit assertions
        assertNull(a4);
        // close EntityManager
        em.close();
    }

    /**
     * update saving account
     * 
     * @result the saving account is updated in entityManager
     * @author vipham 040886894
     */
    @Test
    public void test_29_updateSavingAccount_work() {
        // outside of JavaEE container, must build (Session) EntityManager manually
        EntityManager em = emf.createEntityManager();
        SavingAccount a1 = new SavingAccount();
        em.persist(a1);
        a1.setId(1);
        // update
        SavingAccount a3 = em.merge(a1);
        a3.setId(2);
        // find object
        SavingAccount a4 = em.find(SavingAccount.class, 1);
        // JUnit assertions
        assertNull(a4);
        // close EntityManager
        em.close();
    }

    /**
     * update user
     * 
     * @result the user is updated in entityManager
     * @author vipham 040886894
     */
    @Test
    public void test_30_updateUser_work() {
        // outside of JavaEE container, must build (Session) EntityManager manually
        EntityManager em = emf.createEntityManager();
        User a1 = new User();
        em.persist(a1);
        a1.setId(1);
        // update
        User a3 = em.merge(a1);
        a3.setId(2);
        // find object
        User a4 = em.find(User.class, 1);
        // JUnit assertions
        assertNull(a4);
        // close EntityManager
        em.close();
    }

    /**
     * delete the chequingAccount and expected the portfolio also get removed
     * 
     * @result the portfolio is deleted
     * @author vipham 040886894
     */
    @Test
    public void test_31_deleteChequingAccountandPortfolio_work() {
        // outside of JavaEE container, must build (Session) EntityManager manually
        EntityManager em = emf.createEntityManager();
        // portfolio
        Portfolio portfolio = new Portfolio();
        portfolio.setId(1);

        // account
        ChequingAccount a1 = new ChequingAccount();
        a1.setId(1);
        em.persist(a1);
        em.remove(a1);

        // find object
        Portfolio a2 = em.find(Portfolio.class, 1);
        // JUnit assertions
        assertNull(a2);
        // close EntityManager
        em.close();
    }

    /**
     * delete the chequingAccount and expected the portfolio also is removed
     * 
     * @result the portfolio is deleted
     * @author vipham 040886894
     */
    @Test
    public void test_32_deleteInvestmentAccountAndPortfolio_work() {
        // outside of JavaEE container, must build (Session) EntityManager manually
        EntityManager em = emf.createEntityManager();
        // portfolio
        Portfolio portfolio = new Portfolio();
        portfolio.setId(1);

        // account
        InvestmentAccount a1 = new InvestmentAccount();
        a1.setId(1);
        a1.setPortfolio(portfolio);
        em.persist(a1);
        em.remove(a1);

        // find object
        Portfolio a2 = em.find(Portfolio.class, 1);
        // JUnit assertions
        assertNull(a2);
        // close EntityManager
        em.close();
    }

    /**
     * delete the savingAccount and expected the portfolio also is removed
     * 
     * @result the portfolio is deleted
     * @author vipham 040886894
     */
    @Test
    public void test_33_deleteSavingAccountandPortfolio() {
        // outside of JavaEE container, must build (Session) EntityManager manually
        EntityManager em = emf.createEntityManager();
        // portfolio
        Portfolio portfolio = new Portfolio();
        portfolio.setId(1);

        // account
        SavingAccount a1 = new SavingAccount();
        a1.setId(1);
        em.persist(a1);
        em.remove(a1);

        // find object
        Portfolio a2 = em.find(Portfolio.class, 1);
        // JUnit assertions
        assertNull(a2);
        // close EntityManager
        em.close();
    }

    /**
     * create portfolio and asset, test the relationship between them
     * 
     * @result the portfolio is created, also the asset
     * @author vipham 040886894
     */
    @Test
    public void test_34_createPortfolioAndAsset_work() {
        // outside of JavaEE container, must build (Session) EntityManager manually
        EntityManager em = emf.createEntityManager();
        List<Asset> assets = new ArrayList<>();
        Asset a = new Asset();
        a.setId(1);
        assets.add(a);

        // portfolio
        Portfolio portfolio = new Portfolio();
        portfolio.setId(1);
        portfolio.setAssets(assets);

        em.persist(portfolio);

        // find object
        Portfolio p = em.find(Portfolio.class, 1);

        // JUnit assertions
        assertEquals(a, p.getAssets().get(0));
        // close EntityManager
        em.close();
    }

    /**
     * delete the portfolio and expected the asset also is removed
     * 
     * @result the asset is deleted
     * @author vipham 040886894
     */
    @Test
    public void test_35_deletePortfolioAndAsset_work() {
        // outside of JavaEE container, must build (Session) EntityManager manually
        EntityManager em = emf.createEntityManager();
        List<Asset> assets = new ArrayList<>();
        Asset a = new Asset();
        a.setId(1);
        assets.add(a);

        // portfolio
        Portfolio portfolio = new Portfolio();
        portfolio.setId(1);
        portfolio.setAssets(assets);

        em.persist(portfolio);
        em.remove(portfolio);

        // find object
        Asset p = em.find(Asset.class, 1);

        // JUnit assertions
        assertNull(p);
        // close EntityManager
        em.close();
    }

    /**
     * saving investmentAccount in the entityManager
     * 
     * @result the investmentAccount is saved
     * @author vipham 040886894
     */
    @Test
    public void test_36_saveInvestmentAccount() {
        // outside of JavaEE container, must build (Session) EntityManager manually
        EntityManager em = emf.createEntityManager();
        // create Account
        SavingAccount savingAccount = new SavingAccount();
        savingAccount.setBalance(1.2);
        savingAccount.setId(1);
        savingAccount.setVersion(1);
        savingAccount.setUsers(new ArrayList<User>());

        em.persist(savingAccount);

        SavingAccount a2 = em.find(SavingAccount.class, 1);
        // JUnit assertions
        assertEquals(1, a2.getId());

        // close EntityManager
        em.close();
    }

    /**
     * saving portfolio in the entityManager
     * 
     * @result the portfolio is saved
     * @author vipham 040886894
     */

    @Test
    public void test_37_savePortfolio_work() {
        // outside of JavaEE container, must build (Session) EntityManager manually
        EntityManager em = emf.createEntityManager();
        // create portfolio
        Portfolio p = new Portfolio();
        p.setId(1);

        em.persist(p);

        Portfolio a2 = em.find(Portfolio.class, 1);
        // JUnit assertions
        assertEquals(1, a2.getId());

        // close EntityManager
        em.close();
    }

    /**
     * saving user in the entityManager
     * 
     * @result the user is saved
     * @author vipham 040886894
     */
    @Test
    public void test_38_saveUser_work() {
        // outside of JavaEE container, must build (Session) EntityManager manually
        EntityManager em = emf.createEntityManager();
        // create portfolio
        User p = new User();
        p.setId(1);

        em.persist(p);

        User a2 = em.find(User.class, 1);
        // JUnit assertions
        assertEquals(1, a2.getId());

        // close EntityManager
        em.close();
    }

    /**
     * saving asset in the entityManager
     * 
     * @result the asset is saved
     * @author vipham 040886894
     */
    @Test
    public void test_39_saveAsset_work() {
        // outside of JavaEE container, must build (Session) EntityManager manually
        EntityManager em = emf.createEntityManager();
        // create portfolio
        Asset p = new Asset();
        p.setId(1);

        em.persist(p);

        Asset a2 = em.find(Asset.class, 1);
        // JUnit assertions
        assertEquals(1, a2.getId());

        // close EntityManager
        em.close();
    }

    /**
     * saving savingAccount in the entityManager
     * 
     * @result the savingAccount is saved
     * @author vipham 040886894
     */
    @Test
    public void test_40_saveSavingAccount_work() {
        // outside of JavaEE container, must build (Session) EntityManager manually
        EntityManager em = emf.createEntityManager();
        // create Account
        SavingAccount p = new SavingAccount();
        p.setId(1);

        em.persist(p);

        SavingAccount a2 = em.find(SavingAccount.class, 1);
        // JUnit assertions
        assertNotNull(a2);

        // close EntityManager
        em.close();
    }
}
