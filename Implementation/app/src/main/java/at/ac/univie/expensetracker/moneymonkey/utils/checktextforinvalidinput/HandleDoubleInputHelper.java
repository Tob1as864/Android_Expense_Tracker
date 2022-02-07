package at.ac.univie.expensetracker.moneymonkey.utils.checktextforinvalidinput;

import java.util.List;

public class HandleDoubleInputHelper extends CheckTextForInvalidInput{

    private String enteredDoubleInStringFormat;
    public boolean mStringOK;
    public boolean mDoubleOK;
    public List<Double> mParsedDouble;

    public HandleDoubleInputHelper(String enteredDoubleInStringFormat) {
        this.enteredDoubleInStringFormat = enteredDoubleInStringFormat;
        super.checkStringForEmpty(enteredDoubleInStringFormat);
        super.checkDoubleForNumber(enteredDoubleInStringFormat);
        mStringOK = super.StringOK;
        mDoubleOK = super.DoubleOK;
        mParsedDouble = super.parsedDouble;
    }
}
