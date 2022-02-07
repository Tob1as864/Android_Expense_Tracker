package at.ac.univie.expensetracker.moneymonkey.utils.proxy;

import android.graphics.Color;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;

import java.util.ArrayList;
import java.util.List;

public class ProxyMPPieChartClass implements ProxyMPPieChartInterface {

    private PieChart pieChart;
    private List<PieChart> pieChartCache = new ArrayList<>();
    private boolean drawHoleEnabled = false;
    private boolean setUsePercentValues = false;
    private float setEntryLabelTextSize = 0;
    private int setEntryLabelColor = 0;
    private String setCenterText = "";
    private float setCenterTextSize = 0;
    private boolean setEnabled = true;


    @Override
    public void setDrawHoleEnabled(boolean enabled) {
        pieChart = getFromCache();
        if (drawHoleEnabled == false){
            pieChart.setDrawHoleEnabled(true);
            pieChartCache.clear();
            pieChartCache.add(pieChart);
        }
    }

    @Override
    public void setUsePercentValues(boolean enabled) {
        pieChart = getFromCache();
        if (setUsePercentValues == false){
            pieChart.setUsePercentValues(true);
            pieChartCache.clear();
            pieChartCache.add(pieChart);
        }
    }

    @Override
    public void setEntryLabelTextSize(float size) {
        pieChart = getFromCache();
        if (setEntryLabelTextSize == 0){
            pieChart.setEntryLabelTextSize(12);
            pieChartCache.clear();
            pieChartCache.add(pieChart);
        }
    }

    @Override
    public void setEntryLabelColor(int color) {
        pieChart = getFromCache();
        if (setEntryLabelColor == 0){
            pieChart.setEntryLabelColor(Color.BLACK);
            pieChartCache.clear();
            pieChartCache.add(pieChart);
        }
    }

    @Override
    public void setCenterText() {
        pieChart = getFromCache();
        if (setCenterText == ""){
            pieChart.setCenterText("Distribution of Categories");
            pieChartCache.clear();
            pieChartCache.add(pieChart);
        }
    }

    @Override
    public void setCenterTextSize(float size) {
        pieChart = getFromCache();
        if (setCenterTextSize == 0){
            pieChart.setCenterTextSize(24);
            pieChartCache.clear();
            pieChartCache.add(pieChart);
        }
    }

    @Override
    public ProxyMPPieChartClass getDescription() {
        return this;
    }

    @Override
    public void setEnabled(boolean enabled) {
        pieChart = getFromCache();
        if (setEnabled == true){
            pieChart.setEnabled(false);
            pieChartCache.clear();
            pieChartCache.add(pieChart);
        }
    }

    @Override
    public Legend getLegend() {
        return pieChart.getLegend();
    }

    @Override
    public boolean create() {
        if (pieChartCache.isEmpty()){
            return false;
        }else {
            return true;
        }
    }

    public void saveToCache(PieChart pieChart) {
        pieChartCache.add(pieChart);
    }

    public PieChart getFromCache() {
        return pieChartCache.get(0);
    }

}
