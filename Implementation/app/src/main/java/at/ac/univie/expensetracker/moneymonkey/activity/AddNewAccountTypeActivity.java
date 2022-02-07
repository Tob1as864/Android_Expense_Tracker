package at.ac.univie.expensetracker.moneymonkey.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

import at.ac.univie.expensetracker.moneymonkey.R;
import at.ac.univie.expensetracker.moneymonkey.utils.checktextforinvalidinput.HandleStringInputHelper;
import at.ac.univie.expensetracker.moneymonkey.utils.searchforentryinentity.EntityAccountTypeLoader;
import at.ac.univie.expensetracker.moneymonkey.utils.searchforentryinentity.SearchForEntryInEntityHelper;
import at.ac.univie.expensetracker.moneymonkey.viewmodel.AddNewAccountTypeViewModel;
import at.ac.univie.expensetracker.moneymonkey.customexception.NullExceptionSearchForEntryInEntity;
import at.ac.univie.expensetracker.moneymonkey.database.entity.EntityAccountType;

public class AddNewAccountTypeActivity extends AppCompatActivity {

    private static final String LOG_TAG = AddNewAccountTypeActivity.class.getSimpleName();
    EditText editTextNewAccountType;
    Button buttonAddNewAccountType;
    AddNewAccountTypeViewModel mAddNewAccountTypeViewModel;
    SearchForEntryInEntityHelper searchDuplicate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_account_type);

        editTextNewAccountType = findViewById(R.id.activityaddnewaccounttype_edittext_enteredaccounttype);
        buttonAddNewAccountType = findViewById(R.id.activityaddnewaccounttype_button_addaccounttype);

        // Create class for checking duplicates
        searchDuplicate = new SearchForEntryInEntityHelper<>(new EntityAccountTypeLoader());

        mAddNewAccountTypeViewModel = new ViewModelProvider(this).get(AddNewAccountTypeViewModel.class);

        // Set observer for checking new database entries
        mAddNewAccountTypeViewModel.getAllAccountTypes().observe(this, new Observer<List<EntityAccountType>>() {
            @Override
            public void onChanged(@Nullable List<EntityAccountType> accountTypes) {
                searchDuplicate.loadEntryFromEntity.setList(accountTypes);
            }
        });
    }

    public void addNewAccountType(View view) throws NullExceptionSearchForEntryInEntity {
        Log.d(LOG_TAG, "Enter AddNewAccount Button clicked");

        // Get input data
        String textNewAccountType = editTextNewAccountType.getText().toString();
        // Check for data being valid input
        HandleStringInputHelper object = new HandleStringInputHelper(textNewAccountType);

        // Check for valid input, for duplicate in database and otherwise insert in database
        if(!object.mStringOK) {
            Toast toast = Toast.makeText(this, "Please enter valid input data", Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        } else if(searchDuplicate.compareTextAndEntry(textNewAccountType)){
            Toast toast = Toast.makeText(this, "This account type already exist \nPlease choose another one", Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        } else {
            mAddNewAccountTypeViewModel.insertNewAccountType(new EntityAccountType(textNewAccountType));
            super.finish();
        }
    }
}