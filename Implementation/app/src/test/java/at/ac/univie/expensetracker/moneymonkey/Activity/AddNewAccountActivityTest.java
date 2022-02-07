package at.ac.univie.expensetracker.moneymonkey.Activity;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import android.view.Gravity;
import android.widget.Toast;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import at.ac.univie.expensetracker.moneymonkey.adaptercollection.AddNewAccountRecyclerViewAdapter;
import at.ac.univie.expensetracker.moneymonkey.database.entity.EntityAccount;
import at.ac.univie.expensetracker.moneymonkey.utils.checktextforinvalidinput.HandleStringAndEntityInputHelper;
import at.ac.univie.expensetracker.moneymonkey.viewmodel.AddNewAccountViewModel;

@RunWith(MockitoJUnitRunner.class)
public class AddNewAccountActivityTest {

    @Mock private AddNewAccountRecyclerViewAdapter mAdapter;
    @Mock private AddNewAccountViewModel mAddNewAccountViewModel;
    @Mock private Toast toast;
    private HandleStringAndEntityInputHelper object;

    // Get input data
    String enteredNewAccountName = "ING DiBa";
    String enteredNewAccountLimit = "1000";

    @Before
    public void createInputHelperObject() {
        // Check for data being valid input
        object = new HandleStringAndEntityInputHelper(
                enteredNewAccountName, enteredNewAccountLimit, mAdapter.getSelectedAccountType());
    }

    @Test
    public void checkInputForTest() {
        if (object.mStringOK == false || object.mDoubleOK == false || object.newAccountType == false) {
            //Toast toast = Toast.makeText(this, "Please enter valid input data", Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
            assertTrue(object.mStringOK);
        } else {
            // Insert data in database
            mAddNewAccountViewModel.insertAccount(new EntityAccount(enteredNewAccountName, object.mNewAccountTypeID, object.mParsedDouble.get(0)));
            //super.finish();
            assertEquals(new EntityAccount("ING DiBa", object.mNewAccountTypeID, 1000.0),
                    new EntityAccount(enteredNewAccountName, object.mNewAccountTypeID, object.mParsedDouble.get(0)));
        }
    }
}
