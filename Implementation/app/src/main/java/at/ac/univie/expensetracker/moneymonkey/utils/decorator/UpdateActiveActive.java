package at.ac.univie.expensetracker.moneymonkey.utils.decorator;

import android.os.AsyncTask;
import at.ac.univie.expensetracker.moneymonkey.database.dao.DAOTransactionType;

public class UpdateActiveActive implements ChangeField {

    private static class UpdateAsyncTask extends AsyncTask<Object, Void, Void> {

        private DAOTransactionType mAsyncTaskDao;

        UpdateAsyncTask(DAOTransactionType dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Object... params) {
            boolean transActiv = (Boolean) params[0];
            int transCategoryID = (int) params[1];
            mAsyncTaskDao.updateCategoryName(transActiv, transCategoryID);
            return null;
        }
    }

    @Override
    public void updateField(Object data_1, Object data_2, DAOTransactionType mDAOTransactionType) {
        boolean transActiv = (Boolean) data_1;
        int transCategoryID = (Integer) data_2;
        new UpdateAsyncTask(mDAOTransactionType).execute(transActiv, transCategoryID);
    }
}
