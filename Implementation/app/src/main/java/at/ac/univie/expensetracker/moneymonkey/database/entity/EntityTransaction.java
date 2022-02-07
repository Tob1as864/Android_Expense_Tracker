package at.ac.univie.expensetracker.moneymonkey.database.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import java.util.Date;

// onDelete Cascade as we want don't want to keep transactions from deleted accounts
@Entity(tableName = "transactions",
        foreignKeys = {
        @ForeignKey(entity = EntityAccount.class,
                        parentColumns = "account_id",
                        childColumns = "account_id",
                        onDelete = ForeignKey.CASCADE),
                // onDelete Restrict as we it should not be possible to delete types which are still in use
                @ForeignKey(entity = EntityTransactionType.class,
                        parentColumns = "category_id",
                        childColumns = "category_id",
                        onDelete = ForeignKey.RESTRICT)},
        indices = {
        @Index(value = {"account_id"}),
                @Index(value = {"category_id"})
        }
)
public class EntityTransaction {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "transaction_id")
    private int mTransactionID;

    @NonNull
    @ColumnInfo(name = "account_id")
    private int mAccountID;
    @ColumnInfo(name = "category_id")
    private int mCategoryID;
    @ColumnInfo(name = "amount")
    private double mAmount;
    @ColumnInfo(name = "transaction_in_out")
    private String mTransactionInOut;
    @ColumnInfo(name = "time")
    private Date mTime;

    @Ignore
    public EntityTransaction(int accountID, int categoryID, double amount, String transactionInOut, Date time) {
        this.mAccountID = accountID;
        this.mCategoryID = categoryID;
        this.mAmount = amount;
        this.mTransactionInOut = transactionInOut;
        this.mTime = time;
    }

    public EntityTransaction(@NonNull int transactionID, int accountID, int categoryID, double amount, String transactionInOut, Date time) {
        this.mTransactionID = transactionID;
        this.mAccountID = accountID;
        this.mCategoryID = categoryID;
        this.mAmount = amount;
        this.mTransactionInOut = transactionInOut;
        this.mTime = time;
    }

    public int getMTransactionID() {
        return mTransactionID;
    }
    public void setMTransactionID(int transactionID) {
        this.mTransactionID = transactionID;
    }

    public int getMAccountID() {
        return mAccountID;
    }
    public void setMAccountID(int accountID) {
        this.mAccountID = accountID;
    }

    public int getMCategoryID() {
        return mCategoryID;
    }
    public void setMCategoryID(int categoryID) {
        this.mCategoryID = categoryID;
    }

    public double getMAmount() {
        return mAmount;
    }
    public void setMAmount(double amount) {
        this.mAmount = amount;
    }

    public String getMTransactionInOut() {
        return mTransactionInOut;
    }
    public void setMTransactionInOut(String transactionInOut){
        this.mTransactionInOut = transactionInOut; }

    public Date getMTime() {
        return mTime;
    }
    public void setMTime(Date time) {
        this.mTime = time;
    }
}
