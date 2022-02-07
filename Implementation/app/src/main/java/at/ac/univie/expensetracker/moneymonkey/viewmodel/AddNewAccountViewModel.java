package at.ac.univie.expensetracker.moneymonkey.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import at.ac.univie.expensetracker.moneymonkey.database.contentprovider.AccountEntityContentProvider;
import at.ac.univie.expensetracker.moneymonkey.database.contentprovider.AccountTypeEntityContentProvider;
import at.ac.univie.expensetracker.moneymonkey.database.entity.EntityAccount;
import at.ac.univie.expensetracker.moneymonkey.database.entity.EntityAccountType;

public class AddNewAccountViewModel extends AndroidViewModel {

    private AccountTypeEntityContentProvider mAccountTypeEntityContentProvider;
    private AccountEntityContentProvider mAccountEntityContentProvider;
    private LiveData<List<EntityAccountType>> mAllAccountTypes;
    public static long insertReturnNewAccountTypeID;

    public AddNewAccountViewModel(@NonNull Application application) {
        super(application);
        mAccountTypeEntityContentProvider = new AccountTypeEntityContentProvider(application);
        mAccountEntityContentProvider = new AccountEntityContentProvider(application);
        mAllAccountTypes = mAccountTypeEntityContentProvider.getAllAccountTypes();
    }


    public LiveData<List<EntityAccountType>> getAllAccountTypes() {
        return mAllAccountTypes;
    }

    public void insertAccountType(EntityAccountType accountType) {
        mAccountTypeEntityContentProvider.insert(accountType);
    }

    public void insertAccount(EntityAccount account) {
        mAccountEntityContentProvider.insert(account);
    }
}
