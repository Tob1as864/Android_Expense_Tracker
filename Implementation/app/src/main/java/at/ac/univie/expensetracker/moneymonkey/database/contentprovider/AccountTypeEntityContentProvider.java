package at.ac.univie.expensetracker.moneymonkey.database.contentprovider;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

import at.ac.univie.expensetracker.moneymonkey.viewmodel.AddNewAccountViewModel;
import at.ac.univie.expensetracker.moneymonkey.database.LocalRoomDatabase;
import at.ac.univie.expensetracker.moneymonkey.database.dao.DAOAccountType;
import at.ac.univie.expensetracker.moneymonkey.database.entity.EntityAccountType;

public class AccountTypeEntityContentProvider {

    private final DAOAccountType mDAOAccountType;
    public LiveData<List<EntityAccountType>> mAllAccountTypes;
    static Long accountTypeID;

    public AccountTypeEntityContentProvider(Application application) {
        LocalRoomDatabase database = LocalRoomDatabase.getDatabase(application);
        mDAOAccountType = database.daoAccountType();
        mAllAccountTypes = mDAOAccountType.getAllAccountTypes();
    }

    public LiveData<List<EntityAccountType>> getAllAccountTypes() {
        return mAllAccountTypes;
    }

    public void insert(EntityAccountType accountType) {
        new insertAsyncTask(mDAOAccountType).execute(accountType);
    }

    public void updateAccountTypeName(String transferAccountTypeName, int transferAccountTypeID) {
        new updateAsyncTask(mDAOAccountType).execute(transferAccountTypeName, transferAccountTypeID);
    }

    private static class insertAsyncTask extends AsyncTask<EntityAccountType, Void, Long> {

        private DAOAccountType mAsyncTaskDao;

        insertAsyncTask(DAOAccountType dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Long doInBackground(final EntityAccountType... entityAccountType) {
            Long returnAccountTypeID = mAsyncTaskDao.insertAccountType(entityAccountType[0]);
            return returnAccountTypeID;
        }

        @Override
        protected void onPostExecute(Long accountTypeID) {
            setReturnValueFromInsertAsyncTask(accountTypeID);
        }
    }

    private static void setReturnValueFromInsertAsyncTask(Long returnValue) {
        AddNewAccountViewModel.insertReturnNewAccountTypeID = returnValue;
    }

    private static class updateAsyncTask extends AsyncTask<Object, Void, Void> {

        private DAOAccountType mAsyncTaskDao;

        updateAsyncTask(DAOAccountType dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Object... params) {
            String transferTypeName = (String) params[0];
            int transferTypeID = (Integer) params[1];
            mAsyncTaskDao.updateAccountName(transferTypeName, transferTypeID);
            return null;
        }
    }
}
