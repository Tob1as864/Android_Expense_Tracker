package at.ac.univie.expensetracker.moneymonkey.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import at.ac.univie.expensetracker.moneymonkey.database.contentprovider.AccountEntityContentProvider;
import at.ac.univie.expensetracker.moneymonkey.database.contentprovider.TransactionEntityContentProvider;
import at.ac.univie.expensetracker.moneymonkey.database.dao.DAOAccount;
import at.ac.univie.expensetracker.moneymonkey.database.dao.DAOTransaction;

public class AlterTransactionDetailsViewModel extends AndroidViewModel {

    private TransactionEntityContentProvider mTransactionEntityContentProvider;
    private AccountEntityContentProvider mAccountEntityContentProvider;

    public AlterTransactionDetailsViewModel(@NonNull Application application) {
        super(application);
        mTransactionEntityContentProvider = new TransactionEntityContentProvider(application);
        mAccountEntityContentProvider = new AccountEntityContentProvider(application);
    }

    public void vmUpdateOneTransaction(int transactionID, double transactionAmount, String transactionInOut) {
        mTransactionEntityContentProvider.updateOneTransaction(transactionID, transactionAmount, transactionInOut);
    }

    public void vmDeleteOneTransaction(int transactionID) {
        mTransactionEntityContentProvider.deleteOneTransaction(transactionID);
    }

    public LiveData<List<DAOAccount.sumForOneAccount>> vmGetSumForOneAccount(int accountID) {
        return mAccountEntityContentProvider.getSumForOneAccount(accountID);
    }

    public LiveData<List<DAOTransaction.CategoryForTransaction>> vmGetCategoryForAccount(int transactionID) {
        return mTransactionEntityContentProvider.getCategoryForAccount(transactionID);
    }

}
