package at.ac.univie.expensetracker.moneymonkey.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import at.ac.univie.expensetracker.moneymonkey.database.contentprovider.AccountEntityContentProvider;

public class AlterAccountViewModel extends AndroidViewModel {

    private AccountEntityContentProvider mAccountEntityContentProvider;

    public AlterAccountViewModel(@NonNull Application application) {
        super(application);
        mAccountEntityContentProvider = new AccountEntityContentProvider(application);
    }

    public void vmUpdateAccountName(String accountName, int accountID) {
        mAccountEntityContentProvider.updateAccountName(accountName, accountID);
    }

    public void vmDeleteAccount(int accountID) {
        mAccountEntityContentProvider.deleteSpecificAccount(accountID);
    }

}
