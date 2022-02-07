package at.ac.univie.expensetracker.moneymonkey.utils;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.ArrayList;
import java.util.List;

import at.ac.univie.expensetracker.moneymonkey.customexception.NullExceptionSearchForEntryInEntity;
import at.ac.univie.expensetracker.moneymonkey.database.entity.EntityAccountType;
import at.ac.univie.expensetracker.moneymonkey.database.entity.EntityTransactionType;
import at.ac.univie.expensetracker.moneymonkey.utils.searchforentryinentity.EntityAccountTypeLoader;
import at.ac.univie.expensetracker.moneymonkey.utils.searchforentryinentity.EntityTransactionTypeLoader;
import at.ac.univie.expensetracker.moneymonkey.utils.searchforentryinentity.SearchForEntryInEntityHelper;

@RunWith(JUnit4.class)
public class SearchForEntryInEntityHelperTest {

    List<EntityAccountType> inputListAccountType;
    List<EntityTransactionType> inputListCategory;

    @Before
    public void prepare(){
        inputListAccountType = new ArrayList<>();
        inputListAccountType.add(new EntityAccountType("Account"));
        inputListAccountType.add(new EntityAccountType("Cash"));

        inputListCategory = new ArrayList<>();
        inputListCategory.add(new EntityTransactionType("Household", true));
        inputListCategory.add(new EntityTransactionType("Salary", true));
    }

    @Test
    public void runTest() throws NullExceptionSearchForEntryInEntity {
        SearchForEntryInEntityHelper searchForEntryInEntityHelper = new SearchForEntryInEntityHelper(new EntityAccountTypeLoader());
        searchForEntryInEntityHelper.loadEntryFromEntity.setList(inputListAccountType);

        boolean result = searchForEntryInEntityHelper.compareTextAndEntry("Cash");
        Assert.assertTrue(result);
    }

    @Test
    public void runTest1() throws NullExceptionSearchForEntryInEntity {
        SearchForEntryInEntityHelper searchForEntryInEntityHelper = new SearchForEntryInEntityHelper(new EntityAccountTypeLoader());
        searchForEntryInEntityHelper.loadEntryFromEntity.setList(inputListAccountType);

        boolean result = searchForEntryInEntityHelper.compareTextAndEntry("Money");
        Assert.assertFalse(result);
    }

    @Test
    public void runTest3() throws NullExceptionSearchForEntryInEntity {
        SearchForEntryInEntityHelper searchForEntryInEntityHelper = new SearchForEntryInEntityHelper(new EntityTransactionTypeLoader());
        searchForEntryInEntityHelper.loadEntryFromEntity.setList(inputListCategory);

        boolean result = searchForEntryInEntityHelper.compareTextAndEntry("Salary");
        Assert.assertTrue(result);
    }
}
