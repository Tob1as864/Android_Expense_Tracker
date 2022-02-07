package at.ac.univie.expensetracker.moneymonkey.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.Date;
import java.util.List;

import at.ac.univie.expensetracker.moneymonkey.database.entity.EntityTransaction;

@Dao
public interface DAOTransaction {

    @Query("SELECT * FROM transactions")
    LiveData<List<EntityTransaction>> getAllTransactions();

    // Query for Dashboard
    @Query("SELECT EA.account_id as acc_id, EA.account_name as acc_name, EAT.account_type_name as acc_type_name, SUM( CASE WHEN ET.transaction_in_out = 'in' then ET.amount when ET.transaction_in_out = 'out' THEN ET.amount *-1 END) as acc_amount FROM account EA LEFT JOIN transactions  ET on  EA.Account_ID = ET.Account_ID LEFT JOIN account_types EAT on EA.account_type_id = EAT.account_type_id GROUP BY EA.Account_ID")
    LiveData<List<AllAccountSums>> getAllAccountSums();

    static class AllAccountSums {
        public int acc_id;
        public String acc_name;
        public String acc_type_name;
        public double acc_amount;
    }

    // Query Account Details
    @Query("SELECT ET.transaction_id as trans_id, ET.category_id as trans_type_name,CASE WHEN ET.Transaction_In_Out = 'in' then ET.amount WHEN ET.Transaction_In_Out = 'out' then ET.amount *-1 END AS trans_amount FROM transactions ET  WHERE ET.Account_ID = :accId")
    LiveData<List<TransactionAccountDetail>> getSpecificAccountTransDetail(int accId);

    static class TransactionAccountDetail {
        public int trans_id;
        public String trans_type_name;
        public double trans_amount;
    }

    // Query Graph - Get percentage of transaction types
    @Query("SELECT ROUND(SUM(CASE WHEN ET.transaction_in_out = 'in' then ET.amount WHEN ET.transaction_in_out = 'out' then ET.amount *-1 END) / (SELECT SUM(CASE WHEN ETT.transaction_in_out = 'in' then ETT.amount WHEN ETT.transaction_in_out = 'out' then ETT.amount *-1 END) AS sum_total FROM transactions ETT),2) as percentage_per_type, ET.category_id as trans_type_name FROM transactions ET GROUP BY ET.category_id")
    LiveData<List<TransactionsGraph>> getAllTransTypePercentage();

    static class TransactionsGraph {
    public float percentage_per_type;
    public String trans_type_name;
    }

    @Insert
    void insertTransaction(EntityTransaction entityTransaction);

    @Query("DELETE FROM transactions")
    void deleteAllTransactions();

    @Query("DELETE FROM transactions WHERE transaction_id=:transferTransactionID")
    void deleteOneTransaction(int transferTransactionID);

    @Query("UPDATE transactions SET amount=:transferNewAmount, transaction_in_out=:transferNewInOut WHERE transaction_id=:transferTransactionID")
    void updateOneTransaction(int transferTransactionID, double transferNewAmount, String transferNewInOut);

    // Query Transaction Details
    @Query("SELECT ET.transaction_id as trans_id, EA.account_name as acc_name, CASE WHEN ET.transaction_in_out = 'in' then ET.amount WHEN ET.transaction_in_out = 'out' then ET.amount *-1 END AS trans_amount FROM transactions ET LEFT JOIN account EA on ET.account_id = EA.account_id  WHERE ET.category_id = :transCategoryID")
    LiveData<List<TransactionTypeDetail>> getAllTypeTransDetail(int transCategoryID);

    static class TransactionTypeDetail {
        public int trans_id;
        public String acc_name;
        public double trans_amount;
    }

    @Query("SELECT * from transactions LIMIT 1")
    EntityTransaction[] getAnyTransaction();

    @Query("SELECT transactions.category_id as cat_id, categories.transaction_type_name as cat_name FROM transactions LEFT JOIN categories ON transactions.category_id=categories.category_id WHERE transactions.transaction_id=:transTransactionID")
    LiveData<List<CategoryForTransaction>> getCategoryForTransaction(int transTransactionID);

    static class CategoryForTransaction {
        public int cat_id;
        public String cat_name;
    }

    @Query("SELECT transactions.amount as amount, categories.transaction_type_name as cat_name, transactions.time as time FROM transactions LEFT JOIN categories ON transactions.category_id=categories.category_id WHERE transactions.transaction_in_out = 'out' GROUP BY cat_name")
    LiveData<List<AmountPerExpenseCategory>> getAmountPerExpenseCategory();

    static class AmountPerExpenseCategory {
        public double amount;
        public String cat_name;
        public Date time;
    }

    @Query("SELECT transactions.amount as amount, categories.transaction_type_name as cat_name, transactions.time as time FROM transactions LEFT JOIN categories ON transactions.category_id=categories.category_id WHERE transactions.transaction_in_out = 'in' GROUP BY cat_name")
    LiveData<List<AmountPerIncomeCategory>> getAmountPerIncomeCategory();

    static class AmountPerIncomeCategory {
        public double amount;
        public String cat_name;
        public Date time;
    }

}

