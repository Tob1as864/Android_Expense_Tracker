package at.ac.univie.expensetracker.moneymonkey.utils;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.ArrayList;
import java.util.List;

import at.ac.univie.expensetracker.moneymonkey.utils.iterator.Iterator;
import at.ac.univie.expensetracker.moneymonkey.utils.iterator.IteratorEnvironmentHelper;

@RunWith(JUnit4.class)
public class IteratorEnvironmentHelperTest {

    @Test
    public void checkIteratorImplementation() {

        List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(3);
        list.add(10);

        IteratorEnvironmentHelper iteratorEnvironment  = new IteratorEnvironmentHelper(list);
        Iterator iterator = iteratorEnvironment.createIterator();
        int lastNumberInList = 0;
        for(int i = 0; i < list.size(); i++) {
            lastNumberInList = (int)iterator.getNextElement();
        }
        Assert.assertEquals(10, lastNumberInList);

    }
}
