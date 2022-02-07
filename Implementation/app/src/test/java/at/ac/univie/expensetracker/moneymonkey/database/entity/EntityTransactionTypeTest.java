package at.ac.univie.expensetracker.moneymonkey.database.entity;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class EntityTransactionTypeTest {

    @Test
    public void checkEntityTransactionTypeMethods() {
        EntityTransactionType entityTransactionType = new EntityTransactionType("Salary", true);
        Assert.assertEquals("Salary", entityTransactionType.getMTransactionTypeName());
        Assert.assertEquals(true, entityTransactionType.getMActive());
    }
}
