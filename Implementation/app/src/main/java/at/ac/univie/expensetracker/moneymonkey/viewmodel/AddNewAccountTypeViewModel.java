package at.ac.univie.expensetracker.moneymonkey.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import at.ac.univie.expensetracker.moneymonkey.database.contentprovider.AccountTypeEntityContentProvider;
import at.ac.univie.expensetracker.moneymonkey.database.entity.EntityAccountType;

public class AddNewAccountTypeViewModel extends AndroidViewModel {

    private AccountTypeEntityContentProvider mAccountTypeEntityContentProvider;
    private LiveData<List<EntityAccountType>> mAllAccountTypes;

    public AddNewAccountTypeViewModel(@NonNull Application application) {
        super(application);
        mAccountTypeEntityContentProvider = new AccountTypeEntityContentProvider(application);
        mAllAccountTypes = mAccountTypeEntityContentProvider.getAllAccountTypes();
    }


    public LiveData<List<EntityAccountType>> getAllAccountTypes() {
        return mAllAccountTypes;
    }

    public void insertNewAccountType(EntityAccountType accountType) {
        mAccountTypeEntityContentProvider.insert(accountType);
    }
}
