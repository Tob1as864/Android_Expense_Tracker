package at.ac.univie.expensetracker.moneymonkey.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import at.ac.univie.expensetracker.moneymonkey.R;
import at.ac.univie.expensetracker.moneymonkey.database.dao.DAOAccount;
import at.ac.univie.expensetracker.moneymonkey.database.dao.DAOTransaction;
import at.ac.univie.expensetracker.moneymonkey.utils.checktextforinvalidinput.HandleDoubleInputHelper;
import at.ac.univie.expensetracker.moneymonkey.viewmodel.AlterTransactionDetailsViewModel;

public class AlterTransactionDetailsActivity extends AppCompatActivity {

    private AlterTransactionDetailsViewModel mAlterTransactionDetailsViewModel;
    private TextView textViewShowTransactionID;
    private TextView textViewShowCategory;
    private EditText editTextNewTransactionValue;
    private Switch switchInOrOut;
    private int mTransactionID;
    private double mTransactionAmount;
    private int mTransactionAccountID;
    protected List<DAOAccount.sumForOneAccount> listSumForOneAccount;
    protected List<DAOTransaction.CategoryForTransaction> listCategoryForTransaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alter_transaction_details);

        mAlterTransactionDetailsViewModel = new ViewModelProvider(this).get(AlterTransactionDetailsViewModel.class);

        Intent intent = getIntent();
        mTransactionID = intent.getIntExtra("clicked_transaction_id", 0);
        mTransactionAmount = intent.getDoubleExtra("clicked_transaction_amount", 0);
        mTransactionAccountID = intent.getIntExtra("entity_account_id", 0);

        textViewShowTransactionID = findViewById(R.id.transactiondetailsactivity_textview_showtransactionid);
        textViewShowCategory = findViewById(R.id.transactiondetailsactivity_textview_showcategory);
        editTextNewTransactionValue = findViewById(R.id.transactiondetailsactivity_edittext_newtransactionvalue);
        switchInOrOut = findViewById(R.id.altertransactiondetailsactivity_switch_inorout);

        String mAccountIDString = Integer.toString(mTransactionID);
        textViewShowTransactionID.setText(mAccountIDString);

        // Set observer for checking new database entries
        mAlterTransactionDetailsViewModel.vmGetSumForOneAccount(mTransactionAccountID).observe(this, new Observer<List<DAOAccount.sumForOneAccount>>() {
            @Override
            public void onChanged(@Nullable List<DAOAccount.sumForOneAccount> sumOneAccount) {
                if(sumOneAccount != null) {
                    listSumForOneAccount = sumOneAccount;
                }
            }
        });

        mAlterTransactionDetailsViewModel.vmGetCategoryForAccount(mTransactionID).observe(this, new Observer<List<DAOTransaction.CategoryForTransaction>>() {
            @Override
            public void onChanged(@Nullable List<DAOTransaction.CategoryForTransaction> categoryForTransaction) {
                listCategoryForTransaction = categoryForTransaction;
                if(listCategoryForTransaction != null) {
                    if(listCategoryForTransaction.size() != 0) {
                        textViewShowCategory.setText(listCategoryForTransaction.get(0).cat_name);
                    }
                }
            }
        });
    }

    public void setNewTransactionValue(View view) {
        String enteredNewTransactionValue = editTextNewTransactionValue.getText().toString();
        HandleDoubleInputHelper checker = new HandleDoubleInputHelper(enteredNewTransactionValue);
        if(!checker.mStringOK || !checker.mDoubleOK) {
            Toast toast = Toast.makeText(this, "Please enter valid input data", Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        } else {
            boolean switchStateOut = switchInOrOut.isChecked();
            if(switchStateOut) {
                // Following if are checks for the account limit
                if(listSumForOneAccount != null) {
                    double accountAmountAfterTransactionUpdate = listSumForOneAccount.get(0).acc_amount - mTransactionAmount - checker.mParsedDouble.get(0);
                    if(accountAmountAfterTransactionUpdate < listSumForOneAccount.get(0).acc_limit && listSumForOneAccount.get(0).acc_amount >= listSumForOneAccount.get(0).acc_limit) {
                        Toast toast = Toast.makeText(this, "!ATTENTION! \nYou are under your limit of "+ listSumForOneAccount.get(0).acc_limit +" for the account with the ID "+mTransactionAccountID, Toast.LENGTH_LONG);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();
                    }
                }
                mAlterTransactionDetailsViewModel.vmUpdateOneTransaction(mTransactionID, checker.mParsedDouble.get(0), "out");
            } else {
                if(listSumForOneAccount != null) {
                    double accountAmountAfterTransactionUpdate = listSumForOneAccount.get(0).acc_amount - mTransactionAmount + checker.mParsedDouble.get(0);
                    if(accountAmountAfterTransactionUpdate < listSumForOneAccount.get(0).acc_limit && listSumForOneAccount.get(0).acc_amount >= listSumForOneAccount.get(0).acc_limit) {
                        Toast toast = Toast.makeText(this, "!ATTENTION! \nYou are under your limit of "+ listSumForOneAccount.get(0).acc_limit +" for the account with the ID "+mTransactionAccountID, Toast.LENGTH_LONG);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();
                    }
                }
                mAlterTransactionDetailsViewModel.vmUpdateOneTransaction(mTransactionID, checker.mParsedDouble.get(0), "in");
            }
            super.finish();
        }
    }

    public void deleteSpecificTransaction(View view) {
        mAlterTransactionDetailsViewModel.vmDeleteOneTransaction(mTransactionID);
        super.finish();
    }
}