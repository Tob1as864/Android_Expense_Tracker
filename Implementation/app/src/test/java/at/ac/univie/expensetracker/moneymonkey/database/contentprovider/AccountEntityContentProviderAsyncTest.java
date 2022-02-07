package at.ac.univie.expensetracker.moneymonkey.database.contentprovider;

import android.os.AsyncTask;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.Mock;

import at.ac.univie.expensetracker.moneymonkey.database.dao.DAOAccount;

@RunWith(JUnit4.class)
public class AccountEntityContentProviderAsyncTest {

    @Mock private DAOAccount mDAOAccount;
    private String transferAccountName = "DKB";
    private int transferAccountID = 1;

    @Test
    public void checkInputInAsyncDao() {
        updateAccountName();
    }

    private void updateAccountName() {
        new UpdateAccountNameAsyncTask(mDAOAccount).execute(transferAccountName, transferAccountID);
    }

    private static class UpdateAccountNameAsyncTask extends AsyncTask<Object, Void, Void> {

        private DAOAccount mAsyncTaskDao;

        UpdateAccountNameAsyncTask(DAOAccount dao) {
            mAsyncTaskDao = dao;
        }

        public void execute(String transferAccountName, int transferAccountID) {
            doInBackground(transferAccountName, transferAccountID);
        }

        @Override
        protected Void doInBackground(final Object... params) {
            String transferAccountName = (String) params[0];
            int transferAccountID = (Integer) params[1];
            Assert.assertEquals("DKB", transferAccountName);
            Assert.assertEquals(1, transferAccountID);
            //mAsyncTaskDao.updateAccountName(transferAccountName, transferAccountID);
            return null;
        }
    }
}
