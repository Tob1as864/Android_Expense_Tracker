package at.ac.univie.expensetracker.moneymonkey.utils.datapreparationgraphs;

import java.util.List;

import at.ac.univie.expensetracker.moneymonkey.database.dao.DAOTransaction;

public class DataPreparationPieChart {
    private List<DAOTransaction.AmountPerExpenseCategory> listPieChart;
    public float values;

    public void setListPieChart(List<DAOTransaction.AmountPerExpenseCategory> amountPerExpenseCategory) {
        listPieChart = amountPerExpenseCategory;
    }

    public void selectCategories() {
        double sumCategory = 0;
        for (DAOTransaction.AmountPerExpenseCategory amountPerExpenseCategory : listPieChart) {
            sumCategory = sumCategory + amountPerExpenseCategory.amount;
        }
        values = (float) sumCategory;
    }
}

