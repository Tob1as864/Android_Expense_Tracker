package at.ac.univie.expensetracker.moneymonkey.database.contentprovider;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

import at.ac.univie.expensetracker.moneymonkey.database.LocalRoomDatabase;
import at.ac.univie.expensetracker.moneymonkey.database.dao.DAOTransactionType;
import at.ac.univie.expensetracker.moneymonkey.database.entity.EntityTransactionType;
import at.ac.univie.expensetracker.moneymonkey.utils.decorator.UpdateActiveActive;
import at.ac.univie.expensetracker.moneymonkey.utils.decorator.UpdateActiveActiveDecorator;
import at.ac.univie.expensetracker.moneymonkey.utils.decorator.UpdateCategoryName;
import at.ac.univie.expensetracker.moneymonkey.utils.decorator.UpdateCategoryNameDecorator;

public class CategoryEntityContentProvider {

    private final DAOTransactionType mDAOTransactionType;
    public LiveData<List<EntityTransactionType>> mAllTransactionTypes;
    public LiveData<List<DAOTransactionType.ActiveTransactionNames>> mAllActiveTransactions;

    public CategoryEntityContentProvider(Application application) {
        LocalRoomDatabase database = LocalRoomDatabase.getDatabase(application);
        mDAOTransactionType = database.daoTransactionTypes();
        mAllTransactionTypes = mDAOTransactionType.getAllTransactionTypes();
        mAllActiveTransactions = mDAOTransactionType.getAllActiveTransactionTypes();
    }

    public LiveData<List<EntityTransactionType>> getAllTransactionTypes() {
        return mAllTransactionTypes;
    }

    public LiveData<List<DAOTransactionType.ActiveTransactionNames>> getAllActiveTransactions() {
        return mAllActiveTransactions;
    }

    public void insert(EntityTransactionType transactionType) {
        new insertAsyncTask(mDAOTransactionType).execute(transactionType);
    }

    public void updateCategoryName(String transCategoryName, int transCategoryID) {
     new UpdateCategoryNameDecorator(new UpdateCategoryName()).updateField(transCategoryName, transCategoryID, mDAOTransactionType);
    }

    public void updateActiveActive(boolean transActiv, int transCategoryID) {
        new UpdateActiveActiveDecorator(new UpdateActiveActive()).updateField(transActiv, transCategoryID, mDAOTransactionType);
    }

    public void updateActiveForCategoryName(boolean transActiv, String transCategoryName) {
        new updateActiveForCategoryNameAsyncTask(mDAOTransactionType).execute(transActiv, transCategoryName);
    }

    public LiveData<List<DAOTransactionType.OneSpecificCategory>> getOneCategory(int transCategoryID) {
        return mDAOTransactionType.getOneCategory(transCategoryID);
    }

    public void deleteOneCategory(int transferCategoryID) {
        mDAOTransactionType.deleteOneCategory(transferCategoryID);
    }

    private static class insertAsyncTask extends AsyncTask<EntityTransactionType, Void, Void> {

        private DAOTransactionType mAsyncTaskDao;

        insertAsyncTask(DAOTransactionType dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final EntityTransactionType... entityTransactionTypes) {
            mAsyncTaskDao.insertInTransactionTypes(entityTransactionTypes[0]);
            return null;
        }
    }

    private static class updateActiveForCategoryNameAsyncTask extends AsyncTask<Object, Void, Void> {

        private DAOTransactionType mAsyncTaskDao;

        updateActiveForCategoryNameAsyncTask(DAOTransactionType dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Object... params) {
            boolean transActiv = (Boolean) params[0];
            String transCategoryName = (String) params[1];
            mAsyncTaskDao.updateActiveForCategoryName(transActiv, transCategoryName);
            return null;
        }
    }
}
