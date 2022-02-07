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
import at.ac.univie.expensetracker.moneymonkey.utils.searchforentryinentity.EntityTransactionTypeLoader;
import at.ac.univie.expensetracker.moneymonkey.utils.searchforentryinentity.SearchForEntryInEntityHelper;
import at.ac.univie.expensetracker.moneymonkey.viewmodel.AddNewCategoryViewModel;
import at.ac.univie.expensetracker.moneymonkey.customexception.NullExceptionSearchForEntryInEntity;
import at.ac.univie.expensetracker.moneymonkey.database.entity.EntityTransactionType;

public class AddNewCategoryActivity extends AppCompatActivity {

    private static final String LOG_TAG = AddNewCategoryActivity.class.getSimpleName();
    EditText categoryText;
    Button addCategoryButton;
    private AddNewCategoryViewModel mAddCategoryViewModel;
    private SearchForEntryInEntityHelper<EntityTransactionType> searchDuplicate;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_category);

        categoryText = findViewById(R.id.activityaddtransactiontype_plaintext_entertype);
        addCategoryButton = findViewById(R.id.activityaddtransactiontype_button_entertype);

        // Create class for checking duplicates
        searchDuplicate = new SearchForEntryInEntityHelper<>(new EntityTransactionTypeLoader());

        mAddCategoryViewModel = new ViewModelProvider(this).get(AddNewCategoryViewModel.class);

        // Set observer for checking new database entries
        mAddCategoryViewModel.getAllTransactionTypes().observe(this, new Observer<List<EntityTransactionType>>() {
            @Override
            public void onChanged(@Nullable List<EntityTransactionType> entityTransactionTypes) {
                searchDuplicate.loadEntryFromEntity.setList(entityTransactionTypes);
            }
        });
    }

    public void addNewTypeName(View view) throws NullExceptionSearchForEntryInEntity {
        Log.d(LOG_TAG, "Button for adding new transaction type clicked");

        // Get input data
        String mTypeText = categoryText.getText().toString();

        // Check for data being valid input
        HandleStringInputHelper object = new HandleStringInputHelper(mTypeText);

        // Check for valid input, for duplicate in database, for being active and otherwise set active
        if(!object.mStringOK) {
            Toast toast = Toast.makeText(this, "Please enter valid input data", Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        } else {
            if(!searchDuplicate.compareTextAndEntry(mTypeText)) {
                mAddCategoryViewModel.insert(new EntityTransactionType(mTypeText, true));
                super.finish();
            } else if (searchDuplicate.entityObject.getMActive()) {
                Toast toast = Toast.makeText(this, "This transaction type already exist \nPlease choose another one", Toast.LENGTH_LONG);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
            } else {
                mAddCategoryViewModel.updateActivForCategoryName(true, mTypeText);
                Toast toast = Toast.makeText(this, "Transaction type activated", Toast.LENGTH_LONG);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
                super.finish();
            }
        }
    }
}