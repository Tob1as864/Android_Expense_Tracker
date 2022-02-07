package at.ac.univie.expensetracker.moneymonkey.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import at.ac.univie.expensetracker.moneymonkey.database.entity.EntityTransactionType;

@Dao
public interface DAOTransactionType {

    @Insert
    void insertInTransactionTypes(EntityTransactionType transactionType);

    @Query("DELETE FROM categories")
    void deleteAllInTransactionTypes();

    @Query("DELETE FROM categories WHERE category_id = :transferCategoryID")
    void deleteOneCategory(int transferCategoryID);

    @Query("SELECT * FROM categories ORDER BY transaction_type_name ASC")
    LiveData<List<EntityTransactionType>> getAllTransactionTypes();

    @Query("SELECT * from categories LIMIT 1")
    LiveData<List<EntityTransactionType>> getAllActiveTransactionType();

    @Query("UPDATE categories SET transaction_type_name =:transCategoryName WHERE category_id = :transCategoryID")
    void updateCategoryName(String transCategoryName, int transCategoryID);

    @Query("UPDATE categories SET active=:transActive WHERE category_id = :transCategoryID")
    void updateCategoryName(boolean transActive, int transCategoryID);

    @Query("UPDATE categories SET active=:transActive WHERE transaction_type_name = :transCategoryName")
    void updateActiveForCategoryName(boolean transActive, String transCategoryName);

    // Query Active Transaction Types
    @Query("SELECT TT.transaction_type_name as transaction_type_name, category_id as cat_id FROM categories TT WHERE TT.active = 1")
    LiveData<List<ActiveTransactionNames>> getAllActiveTransactionTypes();

    static class ActiveTransactionNames {
        public String transaction_type_name;
        public int cat_id;
    }

    @Query("SELECT * from categories LIMIT 1")
    EntityTransactionType[] getAnyTransactionType();

    @Query("SELECT transaction_type_name as category_name FROM categories WHERE category_id=:transCategoryID")
    LiveData<List<OneSpecificCategory>> getOneCategory(int transCategoryID);

    static class OneSpecificCategory {
        public String category_name;
    }

}
