package at.ac.univie.expensetracker.moneymonkey.utils.checktextforinvalidinput;

import android.text.TextUtils;

import java.util.ArrayList;
import java.util.List;


// Class validates the input data from any source for beeing valid to input it in the database
// Also converts Strings to specific formats
public abstract class CheckTextForInvalidInput<T> {

    protected boolean StringOK = true;
    protected boolean DoubleOK = true;
    protected List<Double> parsedDouble = new ArrayList<>();
    double temporaryDouble;

    void checkStringForEmpty(String stringForChecking) {
        if (TextUtils.isEmpty(stringForChecking.trim())) {
            StringOK = false;
        }
    }
    void checkDoubleForNumber(String stringToDouble) {
        try {
            temporaryDouble = Double.valueOf(stringToDouble);
        } catch (Exception e) {
            e.printStackTrace();
            DoubleOK = false;
        }
        if(DoubleOK) {
            parsedDouble.add(temporaryDouble);
        }
    }
}
