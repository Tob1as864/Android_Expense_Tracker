package at.ac.univie.expensetracker.moneymonkey.utils;

import static org.junit.Assert.*;

import androidx.test.filters.SmallTest;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.ArrayList;
import java.util.List;

import at.ac.univie.expensetracker.moneymonkey.utils.checkforinvalidinput.CheckInputStringOutputDouble;

@RunWith(JUnit4.class)
@SmallTest
public class CheckInputStringOutputDoubleTest {

    @Test
    public void whenInputIsValid(){
        List<Double> expectedResult = new ArrayList<>();
        expectedResult.add(100.0);
        List<String> inputList = new ArrayList<>();
        inputList.add("100");
        CheckInputStringOutputDouble checker = new CheckInputStringOutputDouble();
        boolean checkResult = checker.runCheck(inputList);
        assertTrue(checkResult);
        List<Double> returnedResult = checker.getConvertedValues();
        for (int i = 0; i == expectedResult.size(); ++i) {
            assertEquals(expectedResult.get(i), returnedResult.get(i));
        }
    }

    @Test
    public void whenInputIsInvalid() {
        List<String> inputList = new ArrayList<>();
        inputList.add("10/0");
        CheckInputStringOutputDouble checker = new CheckInputStringOutputDouble();
        boolean checkResult = checker.runCheck(inputList);
        assertFalse(checkResult);
    }
}