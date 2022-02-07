package at.ac.univie.expensetracker.moneymonkey.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import at.ac.univie.expensetracker.moneymonkey.database.contentprovider.TransactionEntityContentProvider;
import at.ac.univie.expensetracker.moneymonkey.database.dao.DAOTransaction;

public class AccountDetailsViewModel extends AndroidViewModel {

    private TransactionEntityContentProvider mTransactionEntityContentProvider;

    public AccountDetailsViewModel(@NonNull Application application) {
        super(application);
        mTransactionEntityContentProvider = new TransactionEntityContentProvider(application);
    }

    public LiveData<List<DAOTransaction.TransactionAccountDetail>> getAllTransactionsForAccount(int accountID) {
        return mTransactionEntityContentProvider.getSpecificAccountTransactions(accountID);
    }
}
