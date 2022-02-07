package at.ac.univie.expensetracker.moneymonkey.database.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "account_types")
public class EntityAccountType {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "account_type_id")
    private int mAccountTypeID;

    @ColumnInfo(name = "account_type_name")
    private String mAccountTypeName;

    public EntityAccountType(String accountTypeName) {
        this.mAccountTypeName = accountTypeName;
    }

    public int getMAccountTypeID() {
        return mAccountTypeID;
    }
    public void setMAccountTypeID(int accountTypeID) {
        this.mAccountTypeID = accountTypeID;
    }

    public String getMAccountTypeName() {
        return mAccountTypeName;
    }
    public void setMAccountTypeName(String accountTypeName) {
        this.mAccountTypeName = accountTypeName;
    }
}
