package at.ac.univie.expensetracker.moneymonkey.database.entity;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.Date;

@RunWith(JUnit4.class)
public class EntityTransactionTest {

    @Test
    public void checkEntityTransactionMethods() {
        EntityTransaction entityTransaction = new EntityTransaction(1, 1, 100.0, "in", new Date(2000, 11, 1));
        Assert.assertEquals(1, entityTransaction.getMAccountID());
        Assert.assertEquals(1, entityTransaction.getMCategoryID());
        Assert.assertEquals(100.0, entityTransaction.getMAmount(), 0);
        Assert.assertEquals("in", entityTransaction.getMTransactionInOut());
        Assert.assertEquals(new Date(2000, 11, 1), entityTransaction.getMTime());
    }
}
