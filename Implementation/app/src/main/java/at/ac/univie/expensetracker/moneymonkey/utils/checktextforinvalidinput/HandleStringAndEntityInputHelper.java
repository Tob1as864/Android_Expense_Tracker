package at.ac.univie.expensetracker.moneymonkey.utils.checktextforinvalidinput;

import java.util.List;

import at.ac.univie.expensetracker.moneymonkey.database.entity.EntityAccountType;

public class HandleStringAndEntityInputHelper extends CheckTextForInvalidInput {

    // Saves result from input check
    private String mEnteredNewAccountName;
    private String mEnteredNewAccountLimit;
    private EntityAccountType mSelectedAccountType;
    public boolean mStringOK;
    public boolean mDoubleOK;
    public boolean newAccountType = true;
    public int mNewAccountTypeID;
    public List<Double> mParsedDouble;

    public HandleStringAndEntityInputHelper(String enteredNewAccountName, String enteredNewAccountLimit, EntityAccountType selectedAccountType) {


        this.mEnteredNewAccountName = enteredNewAccountName;
        this.mEnteredNewAccountLimit = enteredNewAccountLimit;
        this.mSelectedAccountType = selectedAccountType;
        super.checkStringForEmpty(mEnteredNewAccountName);
        super.checkStringForEmpty(mEnteredNewAccountLimit);
        super.checkDoubleForNumber(mEnteredNewAccountLimit);
        checkEntityForNull(mSelectedAccountType);
        mStringOK = super.StringOK;
        mDoubleOK = super.DoubleOK;
        mParsedDouble = super.parsedDouble;
    }

    void checkEntityForNull (EntityAccountType accountType) {
        if(accountType != null) {
            mNewAccountTypeID = accountType.getMAccountTypeID();
        } else {
            newAccountType = false;
        }
    }
}
