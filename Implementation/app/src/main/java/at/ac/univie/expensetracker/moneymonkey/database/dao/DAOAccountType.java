package at.ac.univie.expensetracker.moneymonkey.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import at.ac.univie.expensetracker.moneymonkey.database.entity.EntityAccountType;

@Dao
public interface DAOAccountType {

    @Query("SELECT * FROM account_types")
    LiveData<List<EntityAccountType>> getAllAccountTypes();

    @Insert
    long insertAccountType(EntityAccountType entityAccountType);

    @Query("DELETE FROM account_types")
    void deleteAllAccountTypes();

    @Query("UPDATE account_types SET account_type_name=:transferAccountTypeName WHERE account_type_id = :transferAccountTypeID")
    void updateAccountName(String transferAccountTypeName, int transferAccountTypeID);

    @Query("SELECT * from account_types LIMIT 1")
    EntityAccountType[] getAnyAccType();
}
