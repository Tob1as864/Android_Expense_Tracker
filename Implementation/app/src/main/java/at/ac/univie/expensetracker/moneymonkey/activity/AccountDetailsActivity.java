package at.ac.univie.expensetracker.moneymonkey.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import at.ac.univie.expensetracker.moneymonkey.R;
import at.ac.univie.expensetracker.moneymonkey.adaptercollection.AccountDetailsRecyclerViewAdapter;
import at.ac.univie.expensetracker.moneymonkey.database.dao.DAOTransaction;
import at.ac.univie.expensetracker.moneymonkey.utils.testmaterial.ExampleDataTest;
import at.ac.univie.expensetracker.moneymonkey.viewmodel.AccountDetailsViewModel;

public class AccountDetailsActivity extends AppCompatActivity {

    TextView textViewAccountName;
    FloatingActionButton floatingActionButtonAddTransaction;
    RecyclerView mRecyclerView;
    AccountDetailsViewModel mAccountDetailsViewModel;
    int mAccountID;
    String mAccountName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_details);

        Intent intent = getIntent();
        mAccountID = intent.getIntExtra("entity_account_id", 0);
        mAccountName = intent.getStringExtra("entity_account_name");

        textViewAccountName = findViewById(R.id.activity_accountdetails_accountname);
        textViewAccountName.setText(mAccountName);
        floatingActionButtonAddTransaction = findViewById(R.id.activityaccountdetails_floatingactionbutton_addtransaction);
        mRecyclerView = findViewById(R.id.activity_accountdetails_recyclerview);

        mAccountDetailsViewModel = new ViewModelProvider(this).get(AccountDetailsViewModel.class);

        // Start the recyclerview
        AccountDetailsRecyclerViewAdapter mAdapter = new AccountDetailsRecyclerViewAdapter(this);

        // Get all transactions for one specific account
        mAccountDetailsViewModel.getAllTransactionsForAccount(mAccountID).observe(this, new Observer<List<DAOTransaction.TransactionAccountDetail>>() {
            @Override
            public void onChanged(@Nullable List<DAOTransaction.TransactionAccountDetail> allTransactionForAccount) {
                mAdapter.setTransactionsForAccount(allTransactionForAccount, mAccountID);
            }
        });

        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        floatingActionButtonAddTransaction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Call new activity
                Intent intent = new Intent(AccountDetailsActivity.this, AddTransactionActivity.class);
                intent.putExtra("account_id_for_adding_transaction", mAccountID);
                startActivity(intent);
            }
        });

    }
}