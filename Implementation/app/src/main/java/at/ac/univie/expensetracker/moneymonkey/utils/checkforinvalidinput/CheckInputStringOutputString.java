package at.ac.univie.expensetracker.moneymonkey.utils.checkforinvalidinput;

import android.text.TextUtils;

import java.util.List;

public class CheckInputStringOutputString extends CheckForInvalidInput <String, String> {

    //Check for input as String and output as String

    private List<String> valuesToCheck;

    @Override
    protected void setInputForChecking(List<String> input) {
        valuesToCheck = input;
    }

    @Override
    protected void checkInputForBeingEmpty() {
        for(String value : valuesToCheck) {
            if (TextUtils.isEmpty(value.trim())) {
                super.valueOK = false;
            }
        }
    }

    @Override
    protected void convertInputToGoalDatatype() {
        super.convertedValues = valuesToCheck;
    }

    @Override
    protected boolean getResultForCheck() {
        return super.valueOK;
    }
}
