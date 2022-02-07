package at.ac.univie.expensetracker.moneymonkey.database.entity;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class EntityAccountTypeTest {

    @Test
    public void checkEntityAccountTypeMethods() {
        EntityAccountType entityAccountType = new EntityAccountType("Account");
        Assert.assertEquals("Account", entityAccountType.getMAccountTypeName());
    }
}
