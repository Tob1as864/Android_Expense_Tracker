package at.ac.univie.expensetracker.moneymonkey.utils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import androidx.test.filters.SmallTest;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import at.ac.univie.expensetracker.moneymonkey.utils.checkforinvalidinput.CheckInputStringOutputDate;

@RunWith(JUnit4.class)
@SmallTest
public class CheckInputStringOutputDateTest {

    private CheckInputStringOutputDate checker = new CheckInputStringOutputDate();
    private List<String> inputList1 = new ArrayList<>();
    private List<String> inputList2 = new ArrayList<>();
    private Date date1 = new Date(1996-1900, 01, 01);
    private Date date2 = new Date(2021-1900, 01, 29);

    @Before
    public void createInputList() {
        inputList1.add("1");
        inputList1.add("2");
        inputList1.add("1996");
        inputList2.add("29");
        inputList2.add("2");
        inputList2.add("2021");
    }

    @Test
    public void runChecker1() {
        assertTrue(checker.runCheck(inputList1));
        assertEquals(date1, checker.getConvertedValues().get(0));
    }

    // Check input 29.02 for non leap year
    @Test
    public void runChecker2() {
        assertTrue(checker.runCheck(inputList2));
        assertEquals(date2, checker.getConvertedValues().get(0));
    }

}
