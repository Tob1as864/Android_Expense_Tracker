package at.ac.univie.expensetracker.moneymonkey.utils.barcharttransactionpreparations;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import at.ac.univie.expensetracker.moneymonkey.database.dao.DAOTransaction;

public class PrepareListForPeriodicChart {

    List<InterimResultExpenseIncome> mTotalSumPerCategoryExpense;
    List<InterimResultExpenseIncome> mTotalSumPerCategoryIncome;

    public void calculateTotalAmountPerCategoryInPeriod (List<DAOTransaction.AmountPerExpenseCategory> listExpense, List<DAOTransaction.AmountPerIncomeCategory> listIncome, Date periodeStart, Date periodeEnd) {


        List<InterimResultExpenseIncome> transactionsWithinTimePeriodExpense = getObjectsWithinTimePeriodExpense(listExpense, periodeStart, periodeEnd);
        List<InterimResultExpenseIncome> transactionsWithinTimePeriodIncome = getObjectsWithinTimePeriodIncome(listIncome, periodeStart, periodeEnd);
        List<InterimResultExpenseIncome> totalSumPerCategoryExpense = getSumPerCategory(transactionsWithinTimePeriodExpense);
        List<InterimResultExpenseIncome> totalSumPerCategoryIncome = getSumPerCategory(transactionsWithinTimePeriodIncome);

        mTotalSumPerCategoryExpense = totalSumPerCategoryExpense;
        mTotalSumPerCategoryIncome = totalSumPerCategoryIncome;
    }

    class InterimResultExpenseIncome {

        public InterimResultExpenseIncome(double amount, String cat_name) {
            this.amount = amount;
            this.categoryName = cat_name;
        }

        public InterimResultExpenseIncome(double amount, String cat_name, Date time) {
            this.amount = amount;
            this.categoryName = cat_name;
            this.time = time;
        }

        public double amount;
        public String categoryName;
        public Date time;
    }

    // Get transactions during time period expense
    private List<InterimResultExpenseIncome> getObjectsWithinTimePeriodExpense(List<DAOTransaction.AmountPerExpenseCategory> list, Date periodeStart, Date periodeEnd) {
        List<InterimResultExpenseIncome> transactionWithinTimePeriod = new ArrayList<>();
        for(DAOTransaction.AmountPerExpenseCategory transaction : list) {
            if (transaction.time.after(periodeStart) && transaction.time.before(periodeEnd)) {
                transactionWithinTimePeriod.add(new InterimResultExpenseIncome(transaction.amount, transaction.cat_name, transaction.time));
            }
        }
        return transactionWithinTimePeriod;
    }

    // Get transactions during time period income
    private List<InterimResultExpenseIncome> getObjectsWithinTimePeriodIncome(List<DAOTransaction.AmountPerIncomeCategory> list, Date periodeStart, Date periodeEnd) {
        List<InterimResultExpenseIncome> transactionWithinTimePeriod = new ArrayList<>();
        for(DAOTransaction.AmountPerIncomeCategory transaction : list) {
            if (transaction.time.after(periodeStart) && transaction.time.before(periodeEnd)) {
                transactionWithinTimePeriod.add(new InterimResultExpenseIncome(transaction.amount, transaction.cat_name, transaction.time));
            }
        }
        return transactionWithinTimePeriod;
    }

    // Get total sum per category
    private List<InterimResultExpenseIncome> getSumPerCategory(List<InterimResultExpenseIncome> list) {
        List<InterimResultExpenseIncome> resultList = new ArrayList<>();
        List<String> tempCollectionOfAllCategoryNames = new ArrayList<>();

        // Get the names from all categories in transactions
        for(InterimResultExpenseIncome transactionCatName : list) {
            if(!tempCollectionOfAllCategoryNames.contains(transactionCatName.categoryName)) {
                tempCollectionOfAllCategoryNames.add(transactionCatName.categoryName);
            }
        }

        for(String category : tempCollectionOfAllCategoryNames) {
            double totalAmountPerCategory = 0;
            for (InterimResultExpenseIncome transaction : list) {
                if(category.equals(transaction.categoryName)) {
                    totalAmountPerCategory = totalAmountPerCategory +transaction.amount;
                }
            }
            resultList.add(new InterimResultExpenseIncome(totalAmountPerCategory, category));
        }

        return resultList;
    }
}
