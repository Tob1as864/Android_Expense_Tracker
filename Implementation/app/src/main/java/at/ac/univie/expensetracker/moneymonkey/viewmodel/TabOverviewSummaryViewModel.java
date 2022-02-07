package at.ac.univie.expensetracker.moneymonkey.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import at.ac.univie.expensetracker.moneymonkey.database.contentprovider.TransactionEntityContentProvider;
import at.ac.univie.expensetracker.moneymonkey.database.dao.DAOTransaction;
import at.ac.univie.expensetracker.moneymonkey.database.entity.EntityTransaction;

public class TabOverviewSummaryViewModel extends AndroidViewModel {

    private TransactionEntityContentProvider mTransactionEntityContentProvider;
    private LiveData<List<EntityTransaction>> mAllTransactions;
    private LiveData<List<DAOTransaction.AmountPerExpenseCategory>> mAmountPerExpenseCategory;
    private LiveData<List<DAOTransaction.AmountPerIncomeCategory>> mAmountPerIncomeCategory;

    public TabOverviewSummaryViewModel(@NonNull Application application) {
        super(application);
        mTransactionEntityContentProvider = new TransactionEntityContentProvider(application);
        mAllTransactions = mTransactionEntityContentProvider.getAllTransactions();
        mAmountPerExpenseCategory = mTransactionEntityContentProvider.getAmountPerExpenseCategory();
        mAmountPerIncomeCategory = mTransactionEntityContentProvider.getAmountPerIncomeCategory();
    }

    public LiveData<List<EntityTransaction>> vmGetAllTransactions(){
        return mAllTransactions;
    }

    public LiveData<List<DAOTransaction.AmountPerExpenseCategory>> vmGetAmountPerExpenseCategory() {
        return mAmountPerExpenseCategory;
    }

    public LiveData<List<DAOTransaction.AmountPerIncomeCategory>> vmGetAmountPerIncomeCategory() {
        return mAmountPerIncomeCategory;
    }
}