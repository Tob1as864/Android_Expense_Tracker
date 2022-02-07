package at.ac.univie.expensetracker.moneymonkey.database.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "account",
        foreignKeys = {@ForeignKey(
                entity = EntityAccountType.class,
                parentColumns = "account_type_id",
                childColumns = "account_type_id",
                onDelete = ForeignKey.SET_NULL)},
                indices = {@Index(value = {"account_type_id"})}
)
public class EntityAccount {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "account_id")
    private int mAccountID;

    @ColumnInfo(name = "account_name")
    private String mAccountName;
    @ColumnInfo(name = "account_type_id")
    private int mAccountType;
    @ColumnInfo(name = "account_limit")
    private double mAccountLimit;

    public EntityAccount (String accountName, int accountType, double accountLimit) {
        this.mAccountName = accountName;
        this.mAccountType = accountType;
        this.mAccountLimit = accountLimit;
    }

    public int getMAccountID() {
        return mAccountID;
    }
    public void setMAccountID(int accountID) {
        this.mAccountID = accountID;
    }

    public String getMAccountName() {
        return mAccountName;
    }
    public void setMAccountName(String accountName) {
        this.mAccountName = accountName;
    }

    public int getMAccountType() {
        return mAccountType;
    }
    public void setMAccountType(int accountType) {
        this.mAccountType = accountType;
    }

    public double getMAccountLimit() {
        return mAccountLimit;
    }
    public void setMAccountLimit(double accountLimit) {
        this.mAccountLimit = accountLimit;
    }
}
