package at.ac.univie.expensetracker.moneymonkey.utils.decorator;

import android.os.AsyncTask;

import at.ac.univie.expensetracker.moneymonkey.database.dao.DAOTransactionType;

public class UpdateCategoryName implements ChangeField {

    private static class UpdateCategoryNameAsyncTask extends AsyncTask<Object, Void, Void> {

        private DAOTransactionType mAsyncTaskDao;

        UpdateCategoryNameAsyncTask(DAOTransactionType dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Object... params) {
            String transCategoryName = (String) params[0];
            int transCategoryID = (int) params[1];
            mAsyncTaskDao.updateCategoryName(transCategoryName, transCategoryID);
            return null;
        }
    }

    @Override
    public void updateField(Object data_1, Object data_2, DAOTransactionType mDAOTransactionType) {
        String transCategoryName = (String) data_1;
        int transCategoryID = (Integer) data_2;
        new UpdateCategoryName.UpdateCategoryNameAsyncTask(mDAOTransactionType).execute(transCategoryName, transCategoryID);
    }
}
