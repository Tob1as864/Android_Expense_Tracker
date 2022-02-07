package at.ac.univie.expensetracker.moneymonkey.database.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "categories")
public class EntityTransactionType {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "category_id")
    private int mCategoryID;

    @ColumnInfo(name = "transaction_type_name")
    private String mTransactionTypeName;
    // Active if it is possible to choose this transaction type
    @ColumnInfo(name = "active")
    private boolean mActive;

    public EntityTransactionType(String transactionTypeName, boolean active) {
        this.mTransactionTypeName = transactionTypeName;
        this.mActive = active;
    }

    public int getMCategoryID() {
        return mCategoryID;
    }
    public void setMCategoryID(int categoryID) {
        this.mCategoryID = categoryID;
    }

    public String getMTransactionTypeName() {
        return mTransactionTypeName;
    }
    public void setMTransactionTypeName(String transactionTypeName) {
        this.mTransactionTypeName = transactionTypeName;
    }

    public boolean getMActive() {
        return mActive;
    }
    public void setMActive(boolean active) {
        this.mActive = active;
    }

}
