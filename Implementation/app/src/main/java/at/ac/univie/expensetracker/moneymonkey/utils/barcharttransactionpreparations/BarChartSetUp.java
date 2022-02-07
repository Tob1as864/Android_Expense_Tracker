package at.ac.univie.expensetracker.moneymonkey.utils.barcharttransactionpreparations;

import android.graphics.Color;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import at.ac.univie.expensetracker.moneymonkey.database.dao.DAOTransaction;
import at.ac.univie.expensetracker.moneymonkey.database.entity.EntityTransaction;
import at.ac.univie.expensetracker.moneymonkey.utils.datapreparationgraphs.DataPreparationBarChart;

public class BarChartSetUp {

    BarChart mBarChart;

    public BarChart barChartSetSettings(BarChart mBarChart) {
        mBarChart.setDrawGridBackground(false);
        mBarChart.setDrawBarShadow(false);
        mBarChart.setDrawBorders(false);

        Legend l = mBarChart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        l.setOrientation(Legend.LegendOrientation.VERTICAL);
        l.setDrawInside(false);
        l.setEnabled(true);

        return mBarChart;
    }

    public BarChart barChartLoadData(BarChart mBarChart, List<DAOTransaction.AmountPerExpenseCategory> listExpense, List<DAOTransaction.AmountPerIncomeCategory> listIncome, Date periodStart, Date periodEnd) {

        // Get input in right format for bar chart
        PrepareListForPeriodicChart prepareListForPeriodicChart = new PrepareListForPeriodicChart();
        prepareListForPeriodicChart.calculateTotalAmountPerCategoryInPeriod(listExpense, listIncome, periodStart, periodEnd);
        List<PrepareListForPeriodicChart.InterimResultExpenseIncome>ExpensesInPeriodPerCategory = prepareListForPeriodicChart.mTotalSumPerCategoryExpense;
        List<PrepareListForPeriodicChart.InterimResultExpenseIncome>IncomeInPeriodPerCategory = prepareListForPeriodicChart.mTotalSumPerCategoryIncome;

        // Create Array for x-axis names
        int totalSizeEntries = ExpensesInPeriodPerCategory.size() + IncomeInPeriodPerCategory.size();
        String[] xAxisNames = new String[totalSizeEntries];

        // Color setup
        ArrayList<BarEntry> entries1 = new ArrayList<>();
        int i = 0;
        for(PrepareListForPeriodicChart.InterimResultExpenseIncome category : ExpensesInPeriodPerCategory) {
            entries1.add(new BarEntry(i, (float) category.amount));
            xAxisNames[i] = category.categoryName;
            ++i;
        }

        ArrayList<BarEntry> entries2 = new ArrayList<>();
        for(PrepareListForPeriodicChart.InterimResultExpenseIncome category : IncomeInPeriodPerCategory) {
            entries2.add(new BarEntry(i, (float) category.amount));
            xAxisNames[i] = category.categoryName;
            ++i;
        }




        ArrayList<Integer> colors = new ArrayList<>();
        for (int color: ColorTemplate.MATERIAL_COLORS) {
            colors.add(color);
        }

        for (int color: ColorTemplate.VORDIPLOM_COLORS) {
            colors.add(color);
        }

        BarDataSet dataSet1 = new BarDataSet(entries1, "Expenses");
        BarDataSet dataSet2 = new BarDataSet(entries2, "Income");
        //dataSet1.setColors(colors);

        BarData data = new BarData(dataSet1, dataSet2);
        data.setBarWidth(0.3f); // set custom bar width
        data.setValueTextSize(12);
        data.setValueTextColor(Color.BLACK);

        mBarChart.setData(data);
        mBarChart.getXAxis().setValueFormatter(new IndexAxisValueFormatter(xAxisNames));
        mBarChart.setFitBars(true); // make the x-axis fit exactly all bars
        mBarChart.invalidate(); // refresh
        mBarChart.animateY(1400, Easing.EaseInOutQuad);
        mBarChart.animateX(1400, Easing.EaseInOutQuad);

        return mBarChart;
    }

}
