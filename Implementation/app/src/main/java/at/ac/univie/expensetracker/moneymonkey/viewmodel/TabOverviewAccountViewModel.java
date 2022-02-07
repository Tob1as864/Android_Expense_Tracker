package at.ac.univie.expensetracker.moneymonkey.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import at.ac.univie.expensetracker.moneymonkey.database.contentprovider.AccountEntityContentProvider;
import at.ac.univie.expensetracker.moneymonkey.database.contentprovider.TransactionEntityContentProvider;
import at.ac.univie.expensetracker.moneymonkey.database.contentprovider.CategoryEntityContentProvider;
import at.ac.univie.expensetracker.moneymonkey.database.dao.DAOTransaction;
import at.ac.univie.expensetracker.moneymonkey.database.dao.DAOTransactionType;
import at.ac.univie.expensetracker.moneymonkey.database.entity.EntityAccount;

public class TabOverviewAccountViewModel extends AndroidViewModel {

    private AccountEntityContentProvider mAccountEntityContentProvider;
    private TransactionEntityContentProvider mTransactionEntityContentProvider;
    private CategoryEntityContentProvider mCategoryEntityContentProvider;
    private LiveData<List<EntityAccount>> mAllAccounts;
    private LiveData<List<DAOTransaction.AllAccountSums>> mSumPerAccount;
    private LiveData<List<DAOTransactionType.ActiveTransactionNames>> mTransactionTypeName;

    public TabOverviewAccountViewModel(@NonNull Application application) {
        super(application);
        mAccountEntityContentProvider = new AccountEntityContentProvider(application);
        mTransactionEntityContentProvider = new TransactionEntityContentProvider(application);
        mCategoryEntityContentProvider = new CategoryEntityContentProvider(application);
        mAllAccounts = mAccountEntityContentProvider.getAllAccounts();
        mSumPerAccount = mTransactionEntityContentProvider.getAllTransactionsSum();
        mTransactionTypeName = mCategoryEntityContentProvider.getAllActiveTransactions();
    }


    public LiveData<List<EntityAccount>> getAllAccounts() {
        return mAllAccounts;
    }


    public void insert(EntityAccount account) {
        mAccountEntityContentProvider.insert(account);
    }

    public LiveData<List<DAOTransaction.AllAccountSums>> getSumPerAccount() {
        return mSumPerAccount;
    }

    public LiveData<List<DAOTransactionType.ActiveTransactionNames>> getTransactionTypeName() {
        return mTransactionTypeName;
    }
}
