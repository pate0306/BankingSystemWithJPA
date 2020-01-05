/**
 * Name : Parth Patel
 * Student number: 040751954
 * class: BankingTestSuite.java
 * Date: 2019-11-15
 */

package com.algonquincollege.cst8277.assignment3.model;

import static org.junit.Assert.assertEquals;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaDelete;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import com.algonquincollege.cst8277.assignment3.TestSuiteBase;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class BankingTestSuite extends TestSuiteBase {

    /**
     * Created one data insert into database to check that one raw is created
     */

    @Test
    public void test01() {
        EntityManager entityManager = emf.createEntityManager();
        entityManager.getTransaction().begin();
        SavingsAccount savingsAccount = new SavingsAccount();
        savingsAccount.setBalance(101);
        entityManager.persist(savingsAccount);
        entityManager.getTransaction().commit();

        entityManager.close();
    }

    /**
     * 
     * Store the balance at chequing acocunt and check the balace get stored
     */

    @Test
    public void test02() {
        EntityManager entityManager1 = emf.createEntityManager();
        entityManager1.getTransaction().begin();
        ChequingAccount chequingAccount = new ChequingAccount();
        chequingAccount.setBalance(700);
        entityManager1.persist(chequingAccount);
        entityManager1.getTransaction().commit();
        ChequingAccount test = entityManager1.find(ChequingAccount.class, 2);
        double balance = test.getBalance();
        assertEquals(700.0, balance, 0.9);
        entityManager1.close();

    }

    /**
     * Update InvestmentAccount Balance
     */

    @Test
    public void test03() {
        EntityManager entityManager = emf.createEntityManager();
        entityManager.getTransaction().begin();
        InvestmentAccount investmentAccount = new InvestmentAccount();
        investmentAccount.setBalance(100);
        entityManager.persist(investmentAccount);
        entityManager.getTransaction().commit();
        entityManager.getTransaction().begin();
        InvestmentAccount copyOfAccount1 = entityManager.find(InvestmentAccount.class, 3);
        copyOfAccount1.setBalance(300.0);
        entityManager.merge(copyOfAccount1);
        entityManager.getTransaction().commit();
        InvestmentAccount secondCopyOfAccount1 = entityManager.find(InvestmentAccount.class, 3);
        assertEquals(300, secondCopyOfAccount1.getBalance(), 0.1);
        entityManager.close();

    }

    /**
     * Delete the InvestmentAccount InvestmentAccount ---> Portfolio
     */

    @Test
    public void test04() {

        EntityManager entityManager1 = emf.createEntityManager();
        entityManager1.getTransaction().begin();
        InvestmentAccount account12 = new InvestmentAccount();
        account12.setBalance(200);
        entityManager1.persist(account12);
        entityManager1.getTransaction().commit();
        entityManager1.getTransaction().begin();
        int NumberOfObjectDeleted = entityManager1.createQuery(" DELETE FROM InvestmentAccount u ").executeUpdate();
        entityManager1.getTransaction().commit();
        assertEquals(2, NumberOfObjectDeleted);
        entityManager1.close();

    }

    /**
     * Checking Portfolio has many Assets
     */

    @Test
    public void test05() {

        EntityManager entityManager = emf.createEntityManager();
        entityManager.getTransaction().begin();
        Portfolio portfolio = new Portfolio();
        portfolio.setId(1);
        List<Asset> assets = new ArrayList<Asset>();
        Asset asset = new Asset();
        Asset asset2 = new Asset();
        assets.add(asset);
        assets.add(asset2);
        portfolio.setAssets(assets);
        entityManager.persist(portfolio);
        Portfolio portfolio2 = entityManager.find(Portfolio.class, 1);
        int fromPortfolioAssertSize = portfolio2.getAssets().size();
        assertEquals(assets.size(), fromPortfolioAssertSize);
        entityManager.close();
    }

    /**
     * Checking Asset has one portfolio
     */

    @Test
    public void test06() {

        EntityManager entityManager = emf.createEntityManager();
        entityManager.getTransaction().begin();
        Portfolio portfolio = new Portfolio();
        Asset asset = new Asset();
        asset.setPortfolio(portfolio);
        entityManager.persist(portfolio);
        entityManager.persist(asset);
        entityManager.getTransaction().commit();
        Asset asset2 = entityManager.find(Asset.class, 1);
        Portfolio portfolio2 = asset2.getPortfolio();
        assertEquals(portfolio, portfolio2);

    }

    /**
     * Testing User as a many Accounts
     */

    @Test
    public void test07() {
        EntityManager entityManager = emf.createEntityManager();
        entityManager.getTransaction().begin();
        AccountBase accountBase = new ChequingAccount();
        AccountBase accountBase2 = new SavingsAccount();
        List<AccountBase> account = new ArrayList<AccountBase>();
        account.add(accountBase);
        account.add(accountBase2);
        User user = new User();
        user.setAccounts(account);
        entityManager.persist(user);
        entityManager.getTransaction().commit();
        User user2 = entityManager.find(User.class, 1);
        assertEquals(2, user2.getAccounts().size());
        entityManager.close();
    }

    /**
     * Account as per version
     */

    @Test
    public void test08() {

        EntityManager entityManager = emf.createEntityManager();
        entityManager.getTransaction().begin();
        User user1 = new User();
        User user2 = new User();
        User user3 = new User();
        List<User> userList = new ArrayList<User>();
        userList.add(user1);
        userList.add(user2);
        userList.add(user3);
        SavingsAccount account = new SavingsAccount();
        account.setUserAccounts(userList);
        entityManager.persist(account);
        entityManager.getTransaction().commit();
        Query query = entityManager.createQuery("SELECT s FROM SavingsAccount s WHERE s.version = 1");
        assertEquals(3, query.getResultList().size());
        entityManager.close();
    }

    /**
     * Delete the users
     */

    @Test
    public void test09() {

        EntityManager entityManager = emf.createEntityManager();
        entityManager.getTransaction().begin();
        Query query = entityManager.createQuery(" DELETE FROM User u ");
        int deletedUserNumbers = query.executeUpdate();
        entityManager.getTransaction().commit();
        assertEquals(4, deletedUserNumbers);

    }

    /**
     * Getting List of the users using JPQL
     */

    @Test
    public void test10() {
        EntityManager entityManager = emf.createEntityManager();
        entityManager.getTransaction().begin();
        User user11 = new User();
        User user2 = new User();
        User user3 = new User();
        List<User> userList = new ArrayList<User>();
        userList.add(user11);
        userList.add(user2);
        userList.add(user3);
        SavingsAccount account = new SavingsAccount();
        account.setUserAccounts(userList);
        entityManager.persist(account);
        entityManager.getTransaction().commit();
        entityManager.getTransaction().begin();
        Query query = entityManager.createQuery("SELECT u FROM User u");
        List<User> user = (List<User>) query.getResultList();
        entityManager.close();
        assertEquals(3, user.size());

    }

    /**
     * Count portfolio
     */

    @Test
    public void test11() {

        EntityManager entityManager = emf.createEntityManager();
        entityManager.getTransaction().begin();
        Portfolio portfolio = new Portfolio();
        Portfolio portfolio1 = new Portfolio();
        Portfolio portfolio2 = new Portfolio();
        entityManager.persist(portfolio);
        entityManager.persist(portfolio1);
        entityManager.persist(portfolio2);
        entityManager.getTransaction().commit();
        entityManager.getTransaction().begin();
        Query query = entityManager.createQuery("SELECT u.id, COUNT(u) FROM Portfolio u GROUP BY u.id");
        List<Portfolio> portfolioCount = (List<Portfolio>) query.getResultList();
        assertEquals(4, portfolioCount.size());
    }

    /**
     * Delete Portfolios
     */

    @Test
    public void test12() {

        EntityManager entityManager = emf.createEntityManager();
        entityManager.getTransaction().begin();
        Portfolio portfolio = new Portfolio();
        Portfolio portfolio1 = new Portfolio();
        Portfolio portfolio2 = new Portfolio();
        entityManager.persist(portfolio);
        entityManager.persist(portfolio1);
        entityManager.persist(portfolio2);
        entityManager.getTransaction().commit();
        entityManager.getTransaction().begin();
        Query query = entityManager.createQuery(" DELETE FROM Asset u ");
        query.executeUpdate();
        entityManager.getTransaction().commit();
        entityManager.getTransaction().begin();
        Query query1 = entityManager.createQuery(" DELETE FROM Portfolio u ");
        int portfolioCount = query1.executeUpdate();
        entityManager.getTransaction().commit();
        assertEquals(7, portfolioCount);

    }

    /**
     * InvestmentAccount has One Portfolio. Portfolio has one Account. delete the
     * account, it will delete protfolio too
     */

    @Test
    public void test13() {

        EntityManager entityManager = emf.createEntityManager();
        entityManager.getTransaction().begin();
        InvestmentAccount account = new InvestmentAccount();
        Portfolio portfolio = new Portfolio();
        account.setPortfolio(portfolio);
        entityManager.persist(portfolio);
        entityManager.persist(account);
        entityManager.getTransaction().commit();
        entityManager.getTransaction().begin();
        Query query = entityManager.createQuery("DELETE FROM InvestmentAccount u ");
        assertEquals(0, query.getFirstResult());

    }

    /**
     * Read Account has one Portfilio
     */

    @Test
    public void test14() {

        EntityManager entityManager = emf.createEntityManager();
        entityManager.getTransaction().begin();
        InvestmentAccount account1 = new InvestmentAccount();
        Portfolio portfolio = new Portfolio();
        account1.setPortfolio(portfolio);
        entityManager.persist(portfolio);
        entityManager.persist(account1);
        entityManager.getTransaction().commit();
        entityManager.getTransaction().begin();
        Query query = entityManager.createQuery("SELECT i FROM InvestmentAccount i");
        assertEquals(2, query.getResultList().size());

    }

    /**
     * Update the CheckingAccount balance
     */
    @Test
    public void test15() {

        EntityManager entityManager = emf.createEntityManager();
        entityManager.getTransaction().begin();
        ChequingAccount account = new ChequingAccount();
        account.setBalance(150);
        entityManager.persist(account);
        entityManager.getTransaction().commit();
        entityManager.getTransaction().begin();
        ChequingAccount account2 = entityManager.find(ChequingAccount.class, 11);
        account2.setBalance(1000);
        entityManager.merge(account2);
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    /**
     * Update Portfolio balance
     */

    @Test
    public void test16() {

        EntityManager entityManager = emf.createEntityManager();
        entityManager.getTransaction().begin();
        Portfolio portfolio = new Portfolio();
        portfolio.setBalance(100);
        entityManager.persist(portfolio);
        entityManager.getTransaction().commit();
        entityManager.getTransaction().begin();
        Portfolio copyOfPortfolio1 = entityManager.find(Portfolio.class, 10);
        copyOfPortfolio1.setBalance(300.0);
        entityManager.merge(copyOfPortfolio1);
        entityManager.getTransaction().commit();
        Portfolio portfolioDetails = entityManager.find(Portfolio.class, 10);
        assertEquals(300, portfolioDetails.getBalance(), 0.1);

    }

    /**
     * Find the Portfolio balance from the Investment Account id
     */

    @Test
    public void test17() {

        EntityManager entityManager = emf.createEntityManager();
        entityManager.getTransaction().begin();
        Portfolio portfolio = new Portfolio();
        portfolio.setBalance(500);
        portfolio.setVersion(70);
        entityManager.persist(portfolio);
        InvestmentAccount account = new InvestmentAccount();
        account.setPortfolio(portfolio);
        entityManager.persist(account);
        Query query = entityManager.createQuery("SELECT s FROM InvestmentAccount s WHERE s.id = 12");
        List<InvestmentAccount> investmentAccount = query.getResultList();
        assertEquals(500.0, investmentAccount.get(0).getPortfolio().getBalance(), 0.1);
    }

    /**
     * Find the asset balance from using InvestmentAcount id InvestmentAccount,
     * Portfolio and Asset
     */

    @Test
    public void test18() {

        EntityManager entityManager = emf.createEntityManager();
        entityManager.getTransaction().begin();
        InvestmentAccount account = new InvestmentAccount();
        Portfolio portfolio = new Portfolio();
        portfolio.setBalance(333);
        Asset asset = new Asset();
        asset.setBalance(700);
        List<Asset> assets = new ArrayList<Asset>();
        assets.add(asset);
        portfolio.setAssets(assets);
        account.setPortfolio(portfolio);
        entityManager.persist(portfolio);
        entityManager.persist(account);
        entityManager.getTransaction().commit();
        Query query = entityManager.createQuery("SELECT s FROM InvestmentAccount s WHERE s.id = 13");
        InvestmentAccount accounts = (InvestmentAccount) query.getSingleResult();
        double assertBalanace = ((InvestmentAccount) accounts).getPortfolio().getAssets().get(0).getBalance();
        assertEquals(700.0, assertBalanace, 1.0);
    }

    /**
     * Count Asset
     */

    @Test
    public void test19() {

        EntityManager name = emf.createEntityManager();
        name.getTransaction().begin();
        List<Asset> assets = new ArrayList<Asset>();
        Asset asset = new Asset();
        Asset asset2 = new Asset();
        Asset asset3 = new Asset();
        assets.add(asset);
        assets.add(asset2);
        assets.add(asset3);
        Portfolio portfolio = new Portfolio();
        portfolio.setAssets(assets);
        name.persist(portfolio);
        name.getTransaction().commit();
        Query query = name.createQuery("SELECT COUNT(p) FROM Asset p ");
        long count = (long) query.getSingleResult();
        assertEquals(4L, count);

    }

    /**
     * find the Average the balance in Account
     */

    @Test
    public void test20() {

        EntityManager entityManager = emf.createEntityManager();
        entityManager.getTransaction().begin();
        InvestmentAccount account = new InvestmentAccount();
        account.setBalance(200);
        entityManager.persist(account);
        InvestmentAccount account2 = new InvestmentAccount();
        account2.setBalance(400);
        entityManager.persist(account2);
        Query query = entityManager.createQuery("SELECT AVG(p.balance) FROM InvestmentAccount p");
        Double result = (Double) query.getSingleResult();
        assertEquals(120.0, result, 0.2);
    }

    /**
     * Find the maximum account balance
     */

    @Test
    public void test21() {

        EntityManager entityManager = emf.createEntityManager();
        entityManager.getTransaction().begin();
        InvestmentAccount investmentAccount = new InvestmentAccount();
        investmentAccount.setBalance(100);
        entityManager.persist(investmentAccount);
        InvestmentAccount investmentAccount2 = new InvestmentAccount();
        investmentAccount2.setBalance(550);
        entityManager.persist(investmentAccount2);
        InvestmentAccount investmentAccount3 = new InvestmentAccount();
        investmentAccount3.setBalance(2000);
        entityManager.persist(investmentAccount3);
        Query query = entityManager.createQuery("SELECT MAX(e.balance) FROM InvestmentAccount e");
        Double result = (Double) query.getSingleResult();
        assertEquals(2000.0, result, 0.1);

    }

    /**
     * Find the balance more than 600
     */

    @Test
    public void test22() {

        EntityManager entityManager = emf.createEntityManager();
        entityManager.getTransaction().begin();
        InvestmentAccount investmentAccount = new InvestmentAccount();
        investmentAccount.setBalance(100);
        entityManager.persist(investmentAccount);
        InvestmentAccount investmentAccount2 = new InvestmentAccount();
        investmentAccount2.setBalance(550);
        entityManager.persist(investmentAccount2);
        InvestmentAccount account3 = new InvestmentAccount();
        account3.setBalance(1111);
        entityManager.persist(account3);
        entityManager.getTransaction().commit();
        entityManager.getTransaction().begin();
        Query query = entityManager.createQuery("SELECT e FROM InvestmentAccount e WHERE e.balance >= 600");
        entityManager.getTransaction().commit();
        InvestmentAccount result = (InvestmentAccount) query.getSingleResult();
        assertEquals(1111.0, result.getBalance(), 0.1);
    }

    /**
     * Using CriteriaQuery
     */

    @Test
    public void test23() {

        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        InvestmentAccount investmentAccount = new InvestmentAccount();
        investmentAccount.setBalance(100);
        em.persist(investmentAccount);
        em.getTransaction().commit();
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<InvestmentAccount> q = cb.createQuery(InvestmentAccount.class);
        Root<InvestmentAccount> c = q.from(InvestmentAccount.class);
        q.select(c);
        List<InvestmentAccount> investmentAccounts = em.createQuery(q).getResultList();
        assertEquals(7, investmentAccounts.size());

    }

    /**
     * First Create two user create accoutn connect single account with many users
     */

    @Test
    public void test24() {
        EntityManager em = emf.createEntityManager();

        em.getTransaction().begin();
        SavingsAccount savingAccount = new SavingsAccount();
        User user = new User();
        User user2 = new User();
        List<User> userList = new ArrayList<User>();
        userList.add(user);
        userList.add(user2);
        savingAccount.setUserAccounts(userList);
        em.persist(savingAccount);
        em.getTransaction().commit();
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<SavingsAccount> q = cb.createQuery(SavingsAccount.class);
        Root<SavingsAccount> c = q.from(SavingsAccount.class);
        q.select(c);
        List<SavingsAccount> savingAccountList = em.createQuery(q).getResultList();
        assertEquals(5, savingAccountList.size());
    }

    /**
     * Portfolio balance is equal or more than 5000 INSER operation
     * InvestmentAccount ---> Portfolio
     */

    @Test
    public void test25() {

        EntityManager entityManager = emf.createEntityManager();
        entityManager.getTransaction().begin();
        Portfolio portfolio = new Portfolio();
        portfolio.setBalance(5000);
        InvestmentAccount investmentAccount = new InvestmentAccount();
        investmentAccount.setPortfolio(portfolio);
        entityManager.persist(portfolio);
        Query query = entityManager.createQuery("SELECT e FROM Portfolio e WHERE e.balance >= 5000");
        Portfolio portfolios = (Portfolio) query.getSingleResult();
        assertEquals(investmentAccount.getPortfolio().getBalance(), portfolios.getBalance(), 0.1);
    }

    /**
     * Delete InvestmentAccounts
     * 
     */

    @Test

    public void test26() {
        EntityManager entityManager = emf.createEntityManager();
        entityManager.getTransaction().begin();
        Query query = entityManager.createQuery("DELETE FROM InvestmentAccount p ");
        int numberOfInvestmentAccountDeleted = query.executeUpdate();
        entityManager.getTransaction().commit();
        assertEquals(7, numberOfInvestmentAccountDeleted);

    }

    /**
     * Delete the Portfolio where balance is 500 using criteria Query
     */

    @Test
    public void test27() {

        EntityManager entityManager = emf.createEntityManager();
        entityManager.getTransaction().begin();
        Portfolio portfolio = new Portfolio();
        portfolio.setBalance(500);
        InvestmentAccount investmentAccount = new InvestmentAccount();
        investmentAccount.setPortfolio(portfolio);
        entityManager.persist(portfolio);
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaDelete<Portfolio> criteriaDelete = criteriaBuilder.createCriteriaDelete(Portfolio.class);
        Root<Portfolio> root = criteriaDelete.from(Portfolio.class);
        criteriaDelete.where(criteriaBuilder.equal(root.get(Portfolio_.balance), "500"));
        int rowsDeleted = entityManager.createQuery(criteriaDelete).executeUpdate();
        entityManager.getTransaction().commit();
        assertEquals(1, rowsDeleted);

    }

    /**
     * Delete the the Portfolio which as many Assets using Criteria Query
     */

    @Test

    public void test28() {

        EntityManager entityManager = emf.createEntityManager();
        entityManager.getTransaction().begin();
        List<Asset> assetList = new ArrayList<Asset>();
        Asset asset = new Asset();
        Asset asset2 = new Asset();
        Asset asset3 = new Asset();
        assetList.add(asset);
        assetList.add(asset2);
        assetList.add(asset3);
        Portfolio portfolio = new Portfolio();
        portfolio.setAssets(assetList);
        entityManager.persist(portfolio);
        entityManager.getTransaction().commit();
        entityManager.getTransaction().begin();
        CriteriaQuery<Portfolio> cq = entityManager.getCriteriaBuilder().createQuery(Portfolio.class);
        Root<Portfolio> portfolio1 = cq.from(Portfolio.class);
        cq.select(portfolio1);
        Query q = entityManager.createQuery(cq);
        int portfolioCount = q.getResultList().size();
        entityManager.getTransaction().commit();
        assertEquals(6, portfolioCount);
    }

    /**
     * Using Equal opertator check that how many account has balance is 0
     */
    @Test
    public void test29() {

        EntityManager entityManager = emf.createEntityManager();
        entityManager.getTransaction().begin();
        InvestmentAccount investmentAccount = new InvestmentAccount();
        investmentAccount.setBalance(0);
        entityManager.persist(investmentAccount);
        Query query = entityManager.createQuery("SELECT p FROM InvestmentAccount p WHERE p.balance = 0 ");
        entityManager.getTransaction().commit();
        assertEquals(1, query.getResultList().size());

    }

    /**
     * Update the SavingAccount
     */
    @Test
    public void test30() {

        EntityManager entityManager = emf.createEntityManager();
        entityManager.getTransaction().begin();
        SavingsAccount savingsAccount = entityManager.find(SavingsAccount.class, 1);
        savingsAccount.setBalance(501);
        entityManager.merge(savingsAccount);
        entityManager.getTransaction().commit();
        assertEquals(501.0, savingsAccount.getBalance(), 0.1);
    }

    /**
     * Update the Users
     */

    @Test
    public void test31() {

        EntityManager entityManager = emf.createEntityManager();
        entityManager.getTransaction().begin();
        User user = entityManager.find(User.class, 5);
        user.setBalance(2);
        entityManager.merge(user);
        entityManager.getTransaction().commit();
        assertEquals(2.0, user.getBalance(), 0.1);
    }

    /**
     * Update the Assests balance
     */

    @Test
    public void test32() {

        EntityManager entityManager = emf.createEntityManager();
        entityManager.getTransaction().begin();
        Asset asset = entityManager.find(Asset.class, 7);
        asset.setBalance(77);
        entityManager.merge(asset);
        entityManager.getTransaction().commit();
        assertEquals(77.0, asset.getBalance(), 0.1);
    }

    /**
     * Find the number of Account ids whose balance between is between 1000 and 3000
     */

    @Test
    public void test33() {

        EntityManager entityManager = emf.createEntityManager();
        entityManager.getTransaction().begin();
        InvestmentAccount investmentAccount = new InvestmentAccount();
        investmentAccount.setBalance(1500);
        entityManager.persist(investmentAccount);
        InvestmentAccount investmentAccount2 = new InvestmentAccount();
        investmentAccount2.setBalance(400);
        entityManager.persist(investmentAccount2);
        entityManager.getTransaction().commit();
        entityManager.getTransaction().begin();
        Query query = entityManager
                .createQuery("SELECT p FROM InvestmentAccount p WHERE p.balance BETWEEN 1000 and 3000");
        entityManager.getTransaction().commit();
        assertEquals(1, query.getResultList().size());

    }

    /**
     * 
     * InvestmentAccount balance more than 1000
     */
    @Test
    public void test34() {

        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<InvestmentAccount> q = cb.createQuery(InvestmentAccount.class);
        Root<InvestmentAccount> c = q.from(InvestmentAccount.class);
        CriteriaQuery<InvestmentAccount> a = q.where(cb.gt(c.get("balance"), 1000));
        List<InvestmentAccount> invest = em.createQuery(a).getResultList();
        em.getTransaction().commit();
        assertEquals(1500.0, invest.get(0).getBalance(), 0.1);
    }

    /**
     * 
     * Geting number of investmentAccountNumber whose balance is more than 100
     */
    @Test
    public void test35() {

        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<InvestmentAccount> q = cb.createQuery(InvestmentAccount.class);
        Root<InvestmentAccount> c = q.from(InvestmentAccount.class);
        q.select(c);
        CriteriaQuery<InvestmentAccount> balance = q.where(cb.gt(c.get("balance"), 100));
        int investmentAccountNumber = em.createQuery(balance).getResultList().size();
        assertEquals(2, investmentAccountNumber);
    }

    /**
     * Number of SavingAccount balance more than 500
     */
    @Test
    public void test36() {

        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        SavingsAccount savingsAccount = new SavingsAccount();
        savingsAccount.setBalance(123);
        em.persist(savingsAccount);
        SavingsAccount savingsAccount2 = new SavingsAccount();
        savingsAccount2.setBalance(124);
        em.persist(savingsAccount2);
        em.getTransaction().commit();
        em.getTransaction().begin();
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<SavingsAccount> q = cb.createQuery(SavingsAccount.class);
        Root<SavingsAccount> c = q.from(SavingsAccount.class);
        q.select(c);
        CriteriaQuery<SavingsAccount> balance = q.where(cb.le(c.get("balance"), 500));
        int savingtAccountNumber = em.createQuery(balance).getResultList().size();
        assertEquals(6, savingtAccountNumber);

    }

    /**
     * Saving account balance is lsess than 3000 and version is less than 2
     */
    @Test
    public void test37() {

        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<SavingsAccount> q = cb.createQuery(SavingsAccount.class);
        Root<SavingsAccount> c = q.from(SavingsAccount.class);
        q.select(c);
        CriteriaQuery<SavingsAccount> balance = q.where(cb.le(c.get("balance"), 3000), cb.lt(c.get("version"), 2));
        assertEquals(6, em.createQuery(balance).getResultList().size());
    }

    /**
     * Using Having count savingAccount count more than 1
     */
    @Test
    public void test38() {

        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<SavingsAccount> q = cb.createQuery(SavingsAccount.class);
        Root<SavingsAccount> c = q.from(SavingsAccount.class);
        q.select(c);
        CriteriaQuery<SavingsAccount> balance = q.having(cb.gt(cb.count(c), 1));
        int savingtAccountCount = em.createQuery(balance).getResultList().size();
        assertEquals(7, savingtAccountCount);
    }

    /**
     * Using Where clause get accounts number whose balance is less than 50
     */
    @Test
    public void test39() {

        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<SavingsAccount> q = cb.createQuery(SavingsAccount.class);
        Root<SavingsAccount> c = q.from(SavingsAccount.class);
        q.select(c);
        CriteriaQuery<SavingsAccount> balance = q.where(cb.le(c.get("balance"), 50));
        assertEquals(4, em.createQuery(balance).getResultList().size());
    }

    /**
     * Delete the SavingAccounts
     * 
     */

    @Test
    public void test40() {

        EntityManager entityManager = emf.createEntityManager();
        entityManager.getTransaction().begin();
        Query query = entityManager.createQuery("DELETE FROM SavingsAccount p ");
        int numberOfSavingsAccountDeleted = query.executeUpdate();
        entityManager.getTransaction().commit();
        assertEquals(7, numberOfSavingsAccountDeleted);
    }
}
