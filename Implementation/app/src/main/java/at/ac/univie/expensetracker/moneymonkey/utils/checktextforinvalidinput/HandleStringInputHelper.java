package at.ac.univie.expensetracker.moneymonkey.utils.checktextforinvalidinput;

public class HandleStringInputHelper extends CheckTextForInvalidInput {

    // Saves result from input check
    private String mInputOne;
    public boolean mStringOK;

    public HandleStringInputHelper(String inputOne) {

        this.mInputOne = inputOne;
        super.checkStringForEmpty(mInputOne);
        mStringOK = super.StringOK;
    }
}
