package at.ac.univie.expensetracker.moneymonkey.database.contentprovider;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

import at.ac.univie.expensetracker.moneymonkey.database.LocalRoomDatabase;
import at.ac.univie.expensetracker.moneymonkey.database.dao.DAOTransaction;
import at.ac.univie.expensetracker.moneymonkey.database.entity.EntityTransaction;

public class TransactionEntityContentProvider {

    private final DAOTransaction mDAOTransaction;
    public LiveData<List<EntityTransaction>> mAllTransactions;
    public LiveData<List<DAOTransaction.AllAccountSums>> mAllTransactionsSum;
    public LiveData<List<DAOTransaction.AmountPerExpenseCategory>> mAmountPerCategory;

    public TransactionEntityContentProvider(Application application) {
        LocalRoomDatabase database = LocalRoomDatabase.getDatabase(application);
        mDAOTransaction = database.daoTransaction();
        mAllTransactions = mDAOTransaction.getAllTransactions();
        mAllTransactionsSum = mDAOTransaction.getAllAccountSums();
        mAmountPerCategory = mDAOTransaction.getAmountPerExpenseCategory();
    }

    public LiveData<List<EntityTransaction>> getAllTransactions() {
        return mAllTransactions;
    }

    public LiveData<List<DAOTransaction.AllAccountSums>> getAllTransactionsSum() {
        return mAllTransactionsSum;
    }

    public LiveData<List<DAOTransaction.TransactionTypeDetail>> getmAllTransacionsByType(int categoryID) {
        return mDAOTransaction.getAllTypeTransDetail(categoryID);
    }

    public LiveData<List<DAOTransaction.TransactionAccountDetail>> getSpecificAccountTransactions(int accountID) {
        return mDAOTransaction.getSpecificAccountTransDetail(accountID);
    }

    public LiveData<List<DAOTransaction.AmountPerExpenseCategory>> getAmountPerExpenseCategory() {
        return mDAOTransaction.getAmountPerExpenseCategory();
    }

    public LiveData<List<DAOTransaction.AmountPerIncomeCategory>> getAmountPerIncomeCategory() {
        return mDAOTransaction.getAmountPerIncomeCategory();
    }

    public void insert(EntityTransaction transaction) {
        new insertAsyncTask(mDAOTransaction).execute(transaction);
    }

    public void updateOneTransaction(int transactionID, double newAmount, String newInOut) {
        new UpdateOneTransactionAsyncTask(mDAOTransaction).execute(transactionID, newAmount, newInOut);
    }

    public void deleteOneTransaction(int transactionID) {
        new DeleteOneTransactionAsyncTask(mDAOTransaction).execute(transactionID);
    }

    public LiveData<List<DAOTransaction.CategoryForTransaction>> getCategoryForAccount(int transTransactionID) {
        return mDAOTransaction.getCategoryForTransaction(transTransactionID);
    }

    private static class insertAsyncTask extends AsyncTask<EntityTransaction, Void, Void> {

        private DAOTransaction mAsyncTaskDao;

        insertAsyncTask(DAOTransaction dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final EntityTransaction... entityTransaction) {
            mAsyncTaskDao.insertTransaction(entityTransaction[0]);
            return null;
        }
    }

    private static class UpdateOneTransactionAsyncTask extends AsyncTask<Object, Void, Void> {

        private DAOTransaction mAsyncTaskDao;

        UpdateOneTransactionAsyncTask(DAOTransaction dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(Object... params) {
            int transferTransactionID = (Integer) params[0];
            double transferNewTransactionAmount = (Double) params[1];
            String transferNewInOut = (String) params[2];
            mAsyncTaskDao.updateOneTransaction(transferTransactionID, transferNewTransactionAmount, transferNewInOut);
            return null;
        }
    }

    private static class DeleteOneTransactionAsyncTask extends AsyncTask<Object, Void, Void> {

        private DAOTransaction mAsyncTaskDao;

        DeleteOneTransactionAsyncTask(DAOTransaction dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(Object... params) {
            int transferTransactionID = (Integer) params[0];
            mAsyncTaskDao.deleteOneTransaction(transferTransactionID);
            return null;
        }
    }
}
