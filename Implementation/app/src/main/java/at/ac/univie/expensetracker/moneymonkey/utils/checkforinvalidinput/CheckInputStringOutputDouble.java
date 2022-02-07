package at.ac.univie.expensetracker.moneymonkey.utils.checkforinvalidinput;

import android.text.TextUtils;

import java.util.ArrayList;
import java.util.List;

public class CheckInputStringOutputDouble extends CheckForInvalidInput <String, Double>{

    // Checks for input as String and output as Double

    private List<String> valuesToCheck;
    private double temporaryDouble;


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
        if(super.convertedValues == null) {
            List<Double> createdListForSavingValues = new ArrayList<>();
            super.convertedValues = createdListForSavingValues;
        }
        for(String value : valuesToCheck) {
            try {
                temporaryDouble = Double.valueOf(value);
            } catch (Exception e) {
                e.printStackTrace();
                super.valueOK = false;
            }
            if(super.valueOK) {
                super.convertedValues.add(temporaryDouble);
            }
        }
    }

    @Override
    protected boolean getResultForCheck() {
        return super.valueOK;
    }
}
