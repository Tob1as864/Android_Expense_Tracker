package at.ac.univie.expensetracker.moneymonkey.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import at.ac.univie.expensetracker.moneymonkey.database.entity.EntityAccount;

@Dao
public interface DAOAccount {

    @Query("SELECT * FROM account")
    LiveData<List<EntityAccount>> getAllAccounts();

    @Insert
    void insertAccount(EntityAccount account);

    @Query("DELETE FROM account")
    void deleteAllAccounts();

    @Query("DELETE FROM account WHERE account_id=:transferAccountID")
    void deleteSpecificAccount(int transferAccountID);

    @Query("UPDATE account SET account_name=:transferName WHERE account_id = :transferAccountID")
    void updateAccountName(String transferName, int transferAccountID);

    @Query("SELECT account_id as acc_id, SUM(amount) as acc_amount FROM transactions GROUP BY account_id")
    LiveData<List<sumPerAccount>> getAllSumPerAccount();

    static class sumPerAccount {
        public int acc_id;
        public double acc_amount;
    }

    @Query("SELECT account.account_id as acc_id, SUM(CASE WHEN TA.transaction_in_out = 'in' then TA.amount WHEN TA.transaction_in_out = 'out' then TA.amount *-1 END) AS acc_amount, account.account_limit as acc_limit FROM transactions TA INNER JOIN account ON TA.account_id=account.account_id WHERE account.account_id=:transferAccountID")
    LiveData<List<sumForOneAccount>> getSumForOneAccount(int transferAccountID);

    static class sumForOneAccount {
        public int acc_id;
        public double acc_amount;
        public double acc_limit;
    }

    @Query("SELECT * from account LIMIT 1")
    EntityAccount[] getAnyAcc();
}
