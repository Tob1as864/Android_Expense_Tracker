package at.ac.univie.expensetracker.moneymonkey.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import at.ac.univie.expensetracker.moneymonkey.database.contentprovider.CategoryEntityContentProvider;


public class AlterCategoryViewModel extends AndroidViewModel {

    private CategoryEntityContentProvider mCategoryEntityContentProvider;

    public AlterCategoryViewModel(@NonNull Application application) {
        super(application);
        mCategoryEntityContentProvider = new CategoryEntityContentProvider(application);
    }

    public void vmUpdateCategoryName(String categoryName, int categoryID) {
        mCategoryEntityContentProvider.updateCategoryName(categoryName, categoryID);
    }

    public void vmUpdateCategoryStatus(boolean transActive, int transCategoryID) {
        mCategoryEntityContentProvider.updateActiveActive(transActive, transCategoryID);
    }

    public void vmDeleteCategory(int categoryID) {
        mCategoryEntityContentProvider.deleteOneCategory(categoryID);
    }

}
