package at.ac.univie.expensetracker.moneymonkey.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import at.ac.univie.expensetracker.moneymonkey.database.contentprovider.AccountEntityContentProvider;
import at.ac.univie.expensetracker.moneymonkey.database.contentprovider.CategoryEntityContentProvider;
import at.ac.univie.expensetracker.moneymonkey.database.contentprovider.TransactionEntityContentProvider;
import at.ac.univie.expensetracker.moneymonkey.database.dao.DAOAccount;
import at.ac.univie.expensetracker.moneymonkey.database.dao.DAOTransactionType;
import at.ac.univie.expensetracker.moneymonkey.database.entity.EntityTransaction;

public class AddTransactionViewModel extends AndroidViewModel {

    private CategoryEntityContentProvider mCategoryEntityContentProvider;
    private TransactionEntityContentProvider mTransactionEntityContentProvider;
    private AccountEntityContentProvider mAccountEntityContentProvider;
    private LiveData<List<DAOTransactionType.ActiveTransactionNames>> mAllActiveCategories;

    public AddTransactionViewModel(@NonNull Application application) {
        super(application);
        mCategoryEntityContentProvider = new CategoryEntityContentProvider(application);
        mTransactionEntityContentProvider = new TransactionEntityContentProvider(application);
        mAccountEntityContentProvider = new AccountEntityContentProvider(application);
        mAllActiveCategories = mCategoryEntityContentProvider.getAllActiveTransactions();
    }

    public LiveData<List<DAOTransactionType.ActiveTransactionNames>> vmGetAllActiveCategories() {
        return mAllActiveCategories;
    }

    public LiveData<List<DAOAccount.sumForOneAccount>> vmGetSumForOneAccount(int accountID) {
        return mAccountEntityContentProvider.getSumForOneAccount(accountID);
    }

    public void vmInsertTransaction(EntityTransaction transaction) {
        mTransactionEntityContentProvider.insert(transaction);
    }
}
