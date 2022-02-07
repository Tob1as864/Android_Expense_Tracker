package at.ac.univie.expensetracker.moneymonkey.utils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.ArrayList;
import java.util.List;

import at.ac.univie.expensetracker.moneymonkey.utils.checkforinvalidinput.CheckInputStringOutputString;

@RunWith(JUnit4.class)
public class CheckInputStringOutputStringTest {

    @Test
    public void whenInputIsValid(){
        List<String> expectedResult = new ArrayList<>();
        expectedResult.add("Hello I am a valid String");
        expectedResult.add("And here is another one");

        List<String> inputList = new ArrayList<>();
        inputList.add("Hello I am a valid String");
        inputList.add("And here is another one");

        CheckInputStringOutputString checker = new CheckInputStringOutputString();
        boolean checkResult = checker.runCheck(inputList);
        assertTrue(checkResult);
        List<String> returnedResult = checker.getConvertedValues();
        for (int i = 0; i == expectedResult.size(); ++i) {
            assertEquals(expectedResult.get(i), returnedResult.get(i));
        }
    }
}
