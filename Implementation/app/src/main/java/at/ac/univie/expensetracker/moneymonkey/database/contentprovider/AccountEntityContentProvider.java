package at.ac.univie.expensetracker.moneymonkey.database.contentprovider;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

import at.ac.univie.expensetracker.moneymonkey.database.LocalRoomDatabase;
import at.ac.univie.expensetracker.moneymonkey.database.dao.DAOAccount;
import at.ac.univie.expensetracker.moneymonkey.database.entity.EntityAccount;

public class AccountEntityContentProvider {

    private final DAOAccount mDAOAccount;
    public LiveData<List<EntityAccount>> mAllAccounts;
    public LiveData<List<DAOAccount.sumPerAccount>> mAllSumPerAccount;
    public LiveData<List<DAOAccount.sumForOneAccount>> mSumForOneAccount;

    public AccountEntityContentProvider(Application application) {
        LocalRoomDatabase database = LocalRoomDatabase.getDatabase(application);
        mDAOAccount = database.daoAccount();
        mAllAccounts = mDAOAccount.getAllAccounts();
        mAllSumPerAccount = mDAOAccount.getAllSumPerAccount();
    }

    public LiveData<List<EntityAccount>> getAllAccounts() {
        return mAllAccounts;
    }

    public void insert (EntityAccount entityAccount) {
        new InsertAsyncTask(mDAOAccount).execute(entityAccount);
    }

    public void updateAccountName(String transferAccountName, int transferAccountID) {
        new UpdateAccountNameAsyncTask(mDAOAccount).execute(transferAccountName, transferAccountID);
    }

    public void deleteSpecificAccount(int transferAccountID) {
        new DeleteSpecificAccountAsyncTask(mDAOAccount).execute(transferAccountID);
    }

    public LiveData<List<DAOAccount.sumPerAccount>> getAllSumPerAccount() {
        return mAllSumPerAccount;
    }

    public LiveData<List<DAOAccount.sumForOneAccount>> getSumForOneAccount(int accountID) {
        return mDAOAccount.getSumForOneAccount(accountID);
    }

    private static class InsertAsyncTask extends AsyncTask<EntityAccount, Void, Void> {

        private DAOAccount mAsyncTaskDao;

        InsertAsyncTask(DAOAccount dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final EntityAccount... entityAccounts) {
            mAsyncTaskDao.insertAccount(entityAccounts[0]);
            return null;
        }
    }

    private static class UpdateAccountNameAsyncTask extends AsyncTask<Object, Void, Void> {

        private DAOAccount mAsyncTaskDao;

        UpdateAccountNameAsyncTask(DAOAccount dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Object... params) {
            String transferAccountName = (String) params[0];
            int transferAccountID = (Integer) params[1];
            mAsyncTaskDao.updateAccountName(transferAccountName, transferAccountID);
            return null;
        }
    }

    private static class DeleteSpecificAccountAsyncTask extends AsyncTask<Object, Void, Void> {

        private DAOAccount mAsyncTaskDao;

        DeleteSpecificAccountAsyncTask(DAOAccount dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Object... params) {
            int transferAccountID = (Integer) params[0];
            mAsyncTaskDao.deleteSpecificAccount(transferAccountID);
            return null;
        }
    }
}
