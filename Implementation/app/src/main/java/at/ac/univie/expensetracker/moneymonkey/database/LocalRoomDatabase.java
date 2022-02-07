package at.ac.univie.expensetracker.moneymonkey.database;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;

import at.ac.univie.expensetracker.moneymonkey.database.dao.DAOAccount;
import at.ac.univie.expensetracker.moneymonkey.database.dao.DAOAccountType;
import at.ac.univie.expensetracker.moneymonkey.database.dao.DAOTransaction;
import at.ac.univie.expensetracker.moneymonkey.database.dao.DAOTransactionType;
import at.ac.univie.expensetracker.moneymonkey.database.entity.EntityAccount;
import at.ac.univie.expensetracker.moneymonkey.database.entity.EntityAccountType;
import at.ac.univie.expensetracker.moneymonkey.database.entity.EntityTransactionType;
import at.ac.univie.expensetracker.moneymonkey.database.entity.EntityTransaction;
import at.ac.univie.expensetracker.moneymonkey.database.typeconverter.DateConverter;

@Database(entities = {EntityAccount.class,
        EntityAccountType.class,
        EntityTransaction.class,
        EntityTransactionType.class},
        version = 12, exportSchema = false)
@TypeConverters({DateConverter.class})
public abstract class LocalRoomDatabase extends RoomDatabase {

    //Method necessary for every DAO interface
    //--------------Important-----------------
    public abstract DAOTransactionType daoTransactionTypes();
    public abstract DAOAccount daoAccount();
    public abstract DAOTransaction daoTransaction();
    public abstract DAOAccountType daoAccountType();
    //----------------------------------------

    private static LocalRoomDatabase INSTANCE;

    public static LocalRoomDatabase getDatabase(final Context context) {
        //Check for existing instances of the database
        if (INSTANCE == null) {
            synchronized (LocalRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), LocalRoomDatabase.class, "money_monkey_database").allowMainThreadQueries().fallbackToDestructiveMigration().build();
                }
            }
        }
        return INSTANCE;
    }


    //Reset database during every restart on the same state
    private static RoomDatabase.Callback sRoomDatabaseCallback =
            new RoomDatabase.Callback(){

        @Override
                public void onOpen (@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);
            new PopulateDBAsync(INSTANCE).execute();
        }
    };



    //Fills the database with random data for testing
    private static class PopulateDBAsync extends AsyncTask<Void, Void, Void> {
        private final DAOTransactionType mDAOTransactionType;
        private final DAOAccount mDAOAccount;
        private final DAOAccountType mDAOAccountType;
        private final DAOTransaction mDAOTransaction;
        //Entity transaction
        String[] transactionTypeName = {"Household", "Clothes", "Car"};
        boolean[] transactionTypeBoolean = {true, true, true};
        int [] sampleTransAccTypeIds = {1,2,1,2};
        int [] sampleTransTypeNames = {1, 2, 3};
        String [] sampleTransInOut = {"in","in","out","out"};
        Double [] sampleTransAmounts = {500.00,2000.00,50.00,1000.00};
        String [] sampleTransTime = {"2021-01-01 01:01","2021-02-01 01:01","2021-03-01 01:01","2021-04-01 01:01"};
        //Entity account
        String[] accountName = {"DKB", "ING DiBa", "Sparkasse"};
        int[] accountType = {1, 2, 3};
        double[] accountLimit = {1000, 1111111, 2222222};
        //Entity Account type
        String[] accountTypeName = {"Card", "Cash", "account"};



        PopulateDBAsync(LocalRoomDatabase db) {
            mDAOTransactionType = db.daoTransactionTypes();
            mDAOAccount = db.daoAccount();
            mDAOAccountType = db.daoAccountType();
            mDAOTransaction = db.daoTransaction();
        }

        @Override
        protected Void doInBackground(final Void... params) {
            //mDAOTransactionType.deleteAllInTransactionTypes();
            //mDAOAccount.deleteAllAccounts();
            //mDAOAccountType.deleteAllAccountTypes();

            if (mDAOTransactionType.getAnyTransactionType().length < 1) {
                for (int i = 0; i <= transactionTypeName.length - 1; i++) {
                    EntityTransactionType transactionTypes = new EntityTransactionType(transactionTypeName[i], transactionTypeBoolean[i]);
                    mDAOTransactionType.insertInTransactionTypes(transactionTypes);
                }
            }

            if (mDAOAccountType.getAnyAccType().length < 1) {
                for (int i = 0; i <= accountType.length - 1; i++) {
                    EntityAccountType accountType = new EntityAccountType(accountTypeName[i]);
                    mDAOAccountType.insertAccountType(accountType);
                }
            }

            if (mDAOAccount.getAnyAcc().length < 1) {
                for (int i = 0; i <= accountName.length - 1; i++) {
                    EntityAccount account = new EntityAccount(accountName[i], accountType[i], accountLimit[i]);
                    mDAOAccount.insertAccount(account);
                }
            }
            /*
            if (mDAOTransaction.getAnyTransaction().length < 1) {
                for (int i = 0; i <= sampleTransAccTypeIds.length - 1; i++) {
                    EntityTransaction transaction = new EntityTransaction(
                            0,
                            sampleTransAccTypeIds[i],
                            sampleTransTypeNames[i],
                            sampleTransAmounts[i],
                            sampleTransInOut[i],
                            sampleTransTime[i]);
                    mDAOTransaction.insertTransaction(transaction);
                }
            }
             */

            return null;
        }
    }

}
