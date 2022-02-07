package at.ac.univie.expensetracker.moneymonkey.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import java.util.List;

import at.ac.univie.expensetracker.moneymonkey.R;
import at.ac.univie.expensetracker.moneymonkey.viewmodel.CategoryViewModel;
import at.ac.univie.expensetracker.moneymonkey.adaptercollection.CategoryRecyclerViewAdapter;
import at.ac.univie.expensetracker.moneymonkey.database.dao.DAOTransaction;
public class CategoryActivity extends AppCompatActivity {

    TextView textViewCategoryName;
    RecyclerView mRecyclerView;
    private CategoryViewModel mCategoryViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_overview);

        Intent intent = getIntent();
        int mCategoryID = intent.getIntExtra("id_category", 0);
        String mCategoryName = intent.getStringExtra("name_category");

        textViewCategoryName = findViewById(R.id.activity_categorytype_transactiontypename);
        textViewCategoryName.setText(mCategoryName);
        mRecyclerView = findViewById(R.id.activity_categorytype_recyclerview);

        //Start the recyclerview
        CategoryRecyclerViewAdapter mAdapter = new CategoryRecyclerViewAdapter(this);

        // Create new TransactionTypeViewModel
        mCategoryViewModel = new ViewModelProvider(this).get(CategoryViewModel.class);

        // Get all TransactionTypes
        mCategoryViewModel.getTransactionTypeDetail(mCategoryID).observe(this, new Observer<List<DAOTransaction.TransactionTypeDetail>>() {
            @Override
            public void onChanged(@Nullable List<DAOTransaction.TransactionTypeDetail> transactionTypes) {
                    mAdapter.setTransactionDetails(transactionTypes);
                    mAdapter.notifyDataSetChanged();
            }
        });

        //Set the recyclerview
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));


    }
}