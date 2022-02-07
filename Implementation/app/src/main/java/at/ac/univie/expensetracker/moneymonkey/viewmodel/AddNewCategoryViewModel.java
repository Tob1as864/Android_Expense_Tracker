package at.ac.univie.expensetracker.moneymonkey.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import at.ac.univie.expensetracker.moneymonkey.database.contentprovider.CategoryEntityContentProvider;
import at.ac.univie.expensetracker.moneymonkey.database.entity.EntityTransactionType;

public class AddNewCategoryViewModel extends AndroidViewModel {

    private CategoryEntityContentProvider mCategoryEntityContentProvider;
    private LiveData<List<EntityTransactionType>> mAllTransactionTypes;

    public AddNewCategoryViewModel(@NonNull Application application) {
        super(application);
        mCategoryEntityContentProvider = new CategoryEntityContentProvider(application);
        mAllTransactionTypes = mCategoryEntityContentProvider.getAllTransactionTypes();
    }


    public LiveData<List<EntityTransactionType>> getAllTransactionTypes() {
        return mAllTransactionTypes;
    }

    public void insert(EntityTransactionType TransactionType) {
        mCategoryEntityContentProvider.insert(TransactionType);
    }

    public void updateActivForCategoryName(boolean activity, String typeName) {
        mCategoryEntityContentProvider.updateActiveForCategoryName(activity, typeName);
    }
}
