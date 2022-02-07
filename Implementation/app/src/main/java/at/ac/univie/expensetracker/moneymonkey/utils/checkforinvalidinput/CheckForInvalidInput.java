package at.ac.univie.expensetracker.moneymonkey.utils.checkforinvalidinput;

import java.util.List;

public abstract class  CheckForInvalidInput <T, T2> {

    // This class is for checking the input of different sources and for different data types.
    // It is based on the template method pattern.

    protected boolean valueOK = true;
    protected List<T2> convertedValues;
    protected abstract void setInputForChecking(List<T> input);
    protected abstract void checkInputForBeingEmpty();
    protected abstract void convertInputToGoalDatatype();
    protected abstract boolean getResultForCheck();

    // Runs all necessary checks and converts the data
    public final boolean runCheck(List<T> input) {
        setInputForChecking(input);
        checkInputForBeingEmpty();
        if(valueOK) {
            convertInputToGoalDatatype();
        }
        return getResultForCheck();
    }

    // Returns the data after conversion
    public final List<T2> getConvertedValues() {
        return convertedValues;
    }
}
