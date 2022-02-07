package at.ac.univie.expensetracker.moneymonkey.database.contentprovider;

import android.os.AsyncTask;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.Mock;

import at.ac.univie.expensetracker.moneymonkey.database.dao.DAOTransactionType;
import at.ac.univie.expensetracker.moneymonkey.database.entity.EntityTransactionType;

@RunWith(JUnit4.class)
public class CategoryEntityContentProviderAsyncTest {

    @Mock DAOTransactionType mDAOTransactionType;
    EntityTransactionType transactionType = new EntityTransactionType("Salary", true);

    @Test
    public void checkInsertAsync() {
        insert(transactionType);
    }

    public void insert(EntityTransactionType transactionType) {
        new InsertAsyncTask(mDAOTransactionType).execute(transactionType);
    }

    private static class InsertAsyncTask extends AsyncTask<EntityTransactionType, Void, Void> {

        private DAOTransactionType mAsyncTaskDao;

        InsertAsyncTask(DAOTransactionType dao) {
            mAsyncTaskDao = dao;
        }

        public void execute(EntityTransactionType transferCategory) {
            doInBackground(transferCategory);
        }

        @Override
        protected Void doInBackground(final EntityTransactionType... entityTransactionTypes) {
            Assert.assertEquals("Salary", entityTransactionTypes[0].getMTransactionTypeName());
            //mAsyncTaskDao.insertInTransactionTypes(entityTransactionTypes[0]);
            return null;
        }
    }

}
