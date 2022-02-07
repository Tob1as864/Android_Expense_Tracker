package at.ac.univie.expensetracker.moneymonkey.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import at.ac.univie.expensetracker.moneymonkey.database.contentprovider.AccountTypeEntityContentProvider;

public class AlterExistingAccountTypeViewModel extends AndroidViewModel {

    private AccountTypeEntityContentProvider mAccountTypeEntityContentProvider;

    public AlterExistingAccountTypeViewModel(@NonNull Application application) {
        super(application);
        mAccountTypeEntityContentProvider = new AccountTypeEntityContentProvider(application);
    }

    public void updateAccountTypeName(String newAccountTypeName, int accountTypeID) {
        mAccountTypeEntityContentProvider.updateAccountTypeName(newAccountTypeName, accountTypeID);
    }
}
