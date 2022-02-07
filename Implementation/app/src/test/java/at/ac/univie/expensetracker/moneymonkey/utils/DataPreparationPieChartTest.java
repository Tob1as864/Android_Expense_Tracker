package at.ac.univie.expensetracker.moneymonkey.utils;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.ArrayList;
import java.util.List;

import at.ac.univie.expensetracker.moneymonkey.database.dao.DAOTransaction;
import at.ac.univie.expensetracker.moneymonkey.utils.datapreparationgraphs.DataPreparationPieChart;

@RunWith(JUnit4.class)
public class DataPreparationPieChartTest {

    List<DAOTransaction.AmountPerExpenseCategory> allAmountPerCategory;

    @Before
    public void prepare() {
        allAmountPerCategory = new ArrayList<>();
        DAOTransaction.AmountPerExpenseCategory daoObject = new DAOTransaction.AmountPerExpenseCategory();
        daoObject.cat_name = "Salary";
        daoObject.amount = 100.0;
        allAmountPerCategory.add(daoObject);
    }

    @Test
    public void checkBarChart() {
        DataPreparationPieChart dataPreparationPieChart = new DataPreparationPieChart();
        dataPreparationPieChart.setListPieChart(allAmountPerCategory);
        dataPreparationPieChart.selectCategories();
        Assert.assertEquals(100.0, dataPreparationPieChart.values, 0.0);
    }
}
