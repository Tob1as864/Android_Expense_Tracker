package at.ac.univie.expensetracker.moneymonkey.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import at.ac.univie.expensetracker.moneymonkey.R;
import at.ac.univie.expensetracker.moneymonkey.utils.checktextforinvalidinput.HandleStringAndEntityInputHelper;
import at.ac.univie.expensetracker.moneymonkey.viewmodel.AddNewAccountViewModel;
import at.ac.univie.expensetracker.moneymonkey.adaptercollection.AddNewAccountRecyclerViewAdapter;
import at.ac.univie.expensetracker.moneymonkey.database.entity.EntityAccount;
import at.ac.univie.expensetracker.moneymonkey.database.entity.EntityAccountType;

public class AddNewAccountActivity extends AppCompatActivity {

    private static final String LOG_TAG = AddNewAccountActivity.class.getSimpleName();
    private RecyclerView mRecyclerView;
    private AddNewAccountRecyclerViewAdapter mAdapter;
    private AddNewAccountViewModel mAddNewAccountViewModel;
    private EditText mNewAccountName;
    private EditText mNewAccountLimit;
    private FloatingActionButton mFloatingActionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_account);
        mNewAccountName = findViewById(R.id.activityaddnewaccount_edittext_accountname);
        mNewAccountLimit = findViewById(R.id.activityaddnewaccount_edittext_accountlimit);

        mAddNewAccountViewModel = new ViewModelProvider(this).get(AddNewAccountViewModel.class);

        // Set observer for checking new database entries
        mAddNewAccountViewModel.getAllAccountTypes().observe(this, new Observer<List<EntityAccountType>>() {
            @Override
            public void onChanged(@Nullable List<EntityAccountType> accountTypes) {
                mAdapter.setAccountTypes(accountTypes);
            }
        });

        mFloatingActionButton = findViewById(R.id.activityaddnewaccount_floatingactionbutton_addnewaccounttype);

        mFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Call new activity
                Intent intent = new Intent(AddNewAccountActivity.this, AddNewAccountTypeActivity.class);
                startActivity(intent);
            }
        });

        // Start the recyclerview
        mRecyclerView = findViewById(R.id.addnewaccount_recyclerview);
        mAdapter = new AddNewAccountRecyclerViewAdapter(this);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    public void addNewAccount(View view) {
        Log.d(LOG_TAG, "Enter AddNewAccount Button clicked");

        // Get input data
        String enteredNewAccountName = mNewAccountName.getText().toString();
        String enteredNewAccountLimit = mNewAccountLimit.getText().toString();

        // Check for data being valid input
        HandleStringAndEntityInputHelper object = new HandleStringAndEntityInputHelper(
                enteredNewAccountName, enteredNewAccountLimit, mAdapter.getSelectedAccountType());

        if(object.mStringOK == false || object.mDoubleOK == false || object.newAccountType == false) {
            Toast toast = Toast.makeText(this, "Please enter valid input data", Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        } else {
            // Insert data in database
            mAddNewAccountViewModel.insertAccount(new EntityAccount(enteredNewAccountName, object.mNewAccountTypeID, object.mParsedDouble.get(0)));
            super.finish();
        }
    }
}