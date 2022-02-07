package at.ac.univie.expensetracker.moneymonkey.database.entity;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class EntityAccountTest {

    @Test
    public void checkEntityAccountMethods() {
        EntityAccount entityAccount = new EntityAccount("DKB", 1, 100);
        Assert.assertEquals("DKB", entityAccount.getMAccountName());
        Assert.assertEquals(1, entityAccount.getMAccountType());
        Assert.assertEquals(100, entityAccount.getMAccountLimit(), 0);
    }
}
