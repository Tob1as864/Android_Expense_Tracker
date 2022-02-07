package at.ac.univie.expensetracker.moneymonkey.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import at.ac.univie.expensetracker.moneymonkey.database.contentprovider.TransactionEntityContentProvider;
import at.ac.univie.expensetracker.moneymonkey.database.contentprovider.CategoryEntityContentProvider;
import at.ac.univie.expensetracker.moneymonkey.database.dao.DAOTransaction;
import at.ac.univie.expensetracker.moneymonkey.database.dao.DAOTransactionType;
import at.ac.univie.expensetracker.moneymonkey.database.entity.EntityTransactionType;

public class CategoryViewModel extends AndroidViewModel {

    private TransactionEntityContentProvider mTransactionEntityContentProvider;
    private CategoryEntityContentProvider mContentProviderEntityCategory;
    private LiveData<List<EntityTransactionType>> mAllCategories;
    private LiveData<List<DAOTransactionType.ActiveTransactionNames>> mCategoryName;

    public CategoryViewModel(@NonNull Application application) {
        super(application);

        mTransactionEntityContentProvider = new TransactionEntityContentProvider(application);
        mContentProviderEntityCategory = new CategoryEntityContentProvider(application);
        mAllCategories = mContentProviderEntityCategory.getAllTransactionTypes();
    }

    public LiveData<List<EntityTransactionType>> getAllTransactionTypes() {
        return mAllCategories;
    }

    public LiveData<List<DAOTransaction.TransactionTypeDetail>> getTransactionTypeDetail(int categoryID) {
        return mTransactionEntityContentProvider.getmAllTransacionsByType(categoryID);
    }

    public void insert(EntityTransactionType TransactionType) {
        mContentProviderEntityCategory.insert(TransactionType);
    }
}
