package at.ac.univie.expensetracker.moneymonkey.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import at.ac.univie.expensetracker.moneymonkey.R;
import at.ac.univie.expensetracker.moneymonkey.database.dao.DAOAccount;
import at.ac.univie.expensetracker.moneymonkey.database.dao.DAOTransactionType;
import at.ac.univie.expensetracker.moneymonkey.database.entity.EntityTransaction;
import at.ac.univie.expensetracker.moneymonkey.fragment.TabOverviewSummaryFragment;
import at.ac.univie.expensetracker.moneymonkey.utils.checkforinvalidinput.CheckInputStringOutputDate;
import at.ac.univie.expensetracker.moneymonkey.utils.checkforinvalidinput.CheckInputStringOutputDouble;
import at.ac.univie.expensetracker.moneymonkey.utils.checkforinvalidinput.CheckInputStringOutputString;
import at.ac.univie.expensetracker.moneymonkey.utils.observer.CustomObservable;
import at.ac.univie.expensetracker.moneymonkey.utils.observer.CustomObserver;
import at.ac.univie.expensetracker.moneymonkey.viewmodel.AddTransactionViewModel;

public class AddTransactionActivity extends AppCompatActivity {

    private static final String LOG_TAG = AddTransactionActivity.class.getSimpleName();
    private AddTransactionViewModel mAddTransactionViewModel;
    private Spinner dropdownInOrOut;
    private Spinner dropdownSelectCategory;
    private EditText editTextEnterDateDay;
    private EditText editTextEnterDateMonth;
    private EditText editTextEnterDateYear;
    private EditText editTextEnterAmount;
    protected List<String> listAllCategories;
    protected List<Integer> listAllCategoriesID;
    protected List<DAOAccount.sumForOneAccount> listSumForOneAccount;
    protected String selectedInOut;
    protected String selectedCategory;
    protected int selectedCategoryID;
    private int mAccountID;
    protected String[] arrayInOut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_add_transaction);

        Intent intent = getIntent();
        mAccountID = intent.getIntExtra("account_id_for_adding_transaction", 0);

        dropdownInOrOut = findViewById(R.id.addtransactionactivity_spinner_inout);
        dropdownSelectCategory = findViewById(R.id.addtransactionactivity_spinner_selectcategory);
        editTextEnterDateDay = findViewById(R.id.addtransactionactivity_edittext_enterdate_day);
        editTextEnterDateMonth = findViewById(R.id.addtransactionactivity_edittext_enterdate_month);
        editTextEnterDateYear = findViewById(R.id.addtransactionactivity_edittext_enterdate_year);
        editTextEnterAmount = findViewById(R.id.addtransactionactivity_edittext_enteramount);

        listAllCategories = new ArrayList<>();
        listAllCategoriesID = new ArrayList<>();

        mAddTransactionViewModel = new ViewModelProvider(this).get(AddTransactionViewModel.class);

        // Get text resource from strings.xml - in, out
        arrayInOut = getResources().getStringArray(R.array.addtransaction_selectincomeorexpense);
        // Create dropdown adapter
        ArrayAdapter dropdownInOutAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, arrayInOut);
        dropdownInOutAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ArrayAdapter<String> dropdownSelectCategoryAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, listAllCategories);
        dropdownSelectCategoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Set observer for checking new database entries
        mAddTransactionViewModel.vmGetAllActiveCategories().observe(this, new Observer<List<DAOTransactionType.ActiveTransactionNames>>() {
            @Override
            public void onChanged(@Nullable List<DAOTransactionType.ActiveTransactionNames> allCategories) {
                if(allCategories != null) {
                    for(DAOTransactionType.ActiveTransactionNames category : allCategories) {
                        listAllCategories.add(category.transaction_type_name);
                        listAllCategoriesID.add(category.cat_id);
                    }
                }
                dropdownSelectCategoryAdapter.notifyDataSetChanged();
            }
        });

        mAddTransactionViewModel.vmGetSumForOneAccount(mAccountID).observe(this, new Observer<List<DAOAccount.sumForOneAccount>>() {
            @Override
            public void onChanged(@Nullable List<DAOAccount.sumForOneAccount> sumOneAccount) {
                if(sumOneAccount != null) {
                    listSumForOneAccount = sumOneAccount;
                }
            }
        });

        // Connect xml with adapter
        dropdownInOrOut.setAdapter(dropdownInOutAdapter);
        dropdownSelectCategory.setAdapter(dropdownSelectCategoryAdapter);

        // Set listener for spinner
        dropdownInOrOut.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                        selectedInOut = arrayInOut[position];
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                        Toast toast = Toast.makeText(getApplicationContext(), "Nothing selected", Toast.LENGTH_LONG);
                        toast.setGravity(Gravity.CENTER, 0, 40);
                        toast.show();
                    }
                }
        );

        dropdownSelectCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                        selectedCategory = listAllCategories.get(position);
                        selectedCategoryID = listAllCategoriesID.get(position);
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                        Toast toast = Toast.makeText(getApplicationContext(), "Nothing selected", Toast.LENGTH_LONG);
                        toast.setGravity(Gravity.CENTER, 0, 40);
                        toast.show();
                    }
                }
        );

    }

    public void buttonAddTransaction(View view) {
        Log.d(LOG_TAG, "Enter AddNewAccount Button clicked");

        String enteredDateDay = editTextEnterDateDay.getText().toString();
        String enteredDateMonth = editTextEnterDateMonth.getText().toString();
        String enteredDateYear = editTextEnterDateYear.getText().toString();
        String enteredAmount = editTextEnterAmount.getText().toString();

        CheckInputStringOutputDate checkInStringOutDate = new CheckInputStringOutputDate();
        CheckInputStringOutputDouble checkInStringOutDouble = new CheckInputStringOutputDouble();
        List<String> listInStringOutString = new ArrayList<>();
        listInStringOutString.add(enteredDateDay);
        listInStringOutString.add(enteredDateMonth);
        listInStringOutString.add(enteredDateYear);
        List<String> listInStringOutDouble = new ArrayList<>();
        listInStringOutDouble.add(enteredAmount);

        if(!checkInStringOutDate.runCheck(listInStringOutString) || !checkInStringOutDouble.runCheck(listInStringOutDouble)) {
            Toast toast = Toast.makeText(this, "Please enter valid input data", Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        } else if(selectedInOut == null || selectedCategory == null) {
            Toast toast = Toast.makeText(this, "Please select from dropdown", Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        } else {
            if(listSumForOneAccount != null && selectedInOut.equals("out")) {
                double SumAfterTransaction = listSumForOneAccount.get(0).acc_amount-checkInStringOutDouble.getConvertedValues().get(0);
                // Condition for only giving a warning if the sum is over the limit
                if(SumAfterTransaction < listSumForOneAccount.get(0).acc_limit && listSumForOneAccount.get(0).acc_amount >= listSumForOneAccount.get(0).acc_limit) {
                    Toast toast = Toast.makeText(this, "!ATTENTION! \nYou are now under your limit of "+ listSumForOneAccount.get(0).acc_limit +" for the account with the ID "+mAccountID, Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                }
            }
            mAddTransactionViewModel.vmInsertTransaction(new EntityTransaction(mAccountID, selectedCategoryID, checkInStringOutDouble.getConvertedValues().get(0), selectedInOut, checkInStringOutDate.getConvertedValues().get(0)));
            super.finish();
        }

    }
}