package at.ac.univie.expensetracker.moneymonkey.utils;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import at.ac.univie.expensetracker.moneymonkey.database.entity.EntityTransaction;
import at.ac.univie.expensetracker.moneymonkey.utils.datapreparationgraphs.DataPreparationBarChart;

@RunWith(JUnit4.class)
public class DataPreparationBarChartTest {

    List<EntityTransaction> allTransactions;

    @Before
    public void prepare() {
        allTransactions = new ArrayList<>();
        allTransactions.add(new EntityTransaction(0, 1, 1000.0, "in", new Date(1995, 04, 01)));
    }

    @Test
    public void checkBarChart() {
        DataPreparationBarChart dataPreparationBarChart = new DataPreparationBarChart();
        dataPreparationBarChart.setListBarChart(allTransactions);
        dataPreparationBarChart.selectInOutTransactions();
        Assert.assertEquals(1000.0, dataPreparationBarChart.valuesIn, 0.0);
    }
}
