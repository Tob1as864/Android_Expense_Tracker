package at.ac.univie.expensetracker.moneymonkey.fragment;

import android.os.Bundle;
import android.graphics.Color;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import at.ac.univie.expensetracker.moneymonkey.R;
import at.ac.univie.expensetracker.moneymonkey.database.dao.DAOTransaction;
import at.ac.univie.expensetracker.moneymonkey.database.entity.EntityTransaction;
import at.ac.univie.expensetracker.moneymonkey.utils.barcharttransactionpreparations.BarChartSetUp;
import at.ac.univie.expensetracker.moneymonkey.utils.checkforinvalidinput.CheckInputStringOutputDate;
import at.ac.univie.expensetracker.moneymonkey.utils.datapreparationgraphs.DataPreparationBarChart;
import at.ac.univie.expensetracker.moneymonkey.utils.datapreparationgraphs.DataPreparationPieChart;
import at.ac.univie.expensetracker.moneymonkey.utils.observer.CustomObservableListEntityTransaction;
import at.ac.univie.expensetracker.moneymonkey.utils.observer.CustomObserverListEntityTransaction;
import at.ac.univie.expensetracker.moneymonkey.utils.proxy.ProxyMPPieChartClass;
import at.ac.univie.expensetracker.moneymonkey.viewmodel.TabOverviewSummaryViewModel;

public class TabOverviewSummaryFragment extends Fragment {
    private PieChart pieChart;
    private BarChart barChart;
    protected String[] arrayMonth;
    private TabOverviewSummaryViewModel mTabOverviewSummaryViewModel;
    protected List<Date> listAllYears;
    protected List<DAOTransaction.AmountPerExpenseCategory> valuesExpensesPieChart;
    protected List<DAOTransaction.AmountPerIncomeCategory> valuesIncomePieChart;
    private boolean indicatorCategory = true;
    private Button switchIncomeExpenseCategoryButton;
    private EditText editTextTimePeriodStartDay;
    private EditText editTextTimePeriodStartMonth;
    private EditText editTextTimePeriodStartYear;
    private EditText editTextTimePeriodEndDay;
    private EditText editTextTimePeriodEndMonth;
    private EditText editTextTimePeriodEndYear;
    private Button buttonCustomizedSelectionForDate;
    private Button buttonDailySelection;
    private Button buttonMonthlySelection;
    private Button buttonYearlySelection;
    private BarChart barChartTransaction;
    private ProxyMPPieChartClass proxyMPPieChartClass = new ProxyMPPieChartClass();
    private CustomObservableListEntityTransaction customObservableListEntityTransaction;

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public TabOverviewSummaryFragment() {
        // Required empty public constructor
    }

    public static TabOverviewSummaryFragment newInstance(String param1, String param2) {
        TabOverviewSummaryFragment fragment = new TabOverviewSummaryFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View rootView = inflater.inflate(R.layout.fragment_tab_overview_summary, container, false);

        buttonDailySelection = rootView.findViewById(R.id.fragmentsummary_button_dailyselection);
        buttonMonthlySelection = rootView.findViewById(R.id.fragmentsummary_button_monthlyselection);
        buttonYearlySelection = rootView.findViewById(R.id.fragmentsummary_button_yearlyselection);
        editTextTimePeriodStartDay = rootView.findViewById(R.id.fragmentsummary_specifcustomizeddatestart_day);
        editTextTimePeriodStartMonth = rootView.findViewById(R.id.fragmentsummary_specifcustomizeddatestart_month);
        editTextTimePeriodStartYear = rootView.findViewById(R.id.fragmentsummary_specifcustomizeddatestart_year);
        editTextTimePeriodEndDay = rootView.findViewById(R.id.fragmentsummary_specifcustomizeddateend_day);
        editTextTimePeriodEndMonth = rootView.findViewById(R.id.fragmentsummary_specifcustomizeddateend_month);
        editTextTimePeriodEndYear = rootView.findViewById(R.id.fragmentsummary_specifcustomizeddateend_year);
        buttonCustomizedSelectionForDate =  rootView.findViewById(R.id.fragmentsummary_button_apply_customized_selection);
        barChartTransaction = rootView.findViewById(R.id.fragmentsummary_barchart_graph);

        mTabOverviewSummaryViewModel = new ViewModelProvider(this).get(TabOverviewSummaryViewModel.class);

        if (proxyMPPieChartClass.create()==false){
            pieChart = rootView.findViewById(R.id.fragment_tab_overview_fragment_two_piechart);
            proxyMPPieChartClass.saveToCache(pieChart);
        }else{
            pieChart = proxyMPPieChartClass.getFromCache();
        }

        barChart = rootView.findViewById(R.id.fragment_tab_overview_fragment_two_barchart);
        switchIncomeExpenseCategoryButton = rootView.findViewById(R.id.fragmentsummary_button_switch_income_expense);

        switchIncomeExpenseCategoryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchIncomeExpense(v);
            }
        });

        listAllYears = new ArrayList<>();

        customObservableListEntityTransaction = new CustomObservableListEntityTransaction();

        customObservableListEntityTransaction.register(new CustomObserverListEntityTransaction(){
            private List<EntityTransaction> entityTransactionList;
            @Override
            public void update(Object object){
                this.entityTransactionList = (List<EntityTransaction>) object;
                setupBarChart();
                loadBarChart(entityTransactionList);
            }
        });

        // Set observer for checking new database entries
        mTabOverviewSummaryViewModel.vmGetAllTransactions().observe(getViewLifecycleOwner(), new Observer<List<EntityTransaction>>() {
            @Override
            public void onChanged(@Nullable List<EntityTransaction> allTransactions) {
                //yValuesBarChart = allTransactions;
                if(allTransactions != null) {
                    for(EntityTransaction transaction : allTransactions) {
                        listAllYears.clear();
                        listAllYears.add(transaction.getMTime());
                    }
                    //setupBarChart();
                    //loadBarChart();
                    customObservableListEntityTransaction.customNotify(allTransactions);
                }
            }
        });

        mTabOverviewSummaryViewModel.vmGetAmountPerExpenseCategory().observe(getViewLifecycleOwner(), new Observer<List<DAOTransaction.AmountPerExpenseCategory>>() {
            @Override
            public void onChanged(@Nullable List<DAOTransaction.AmountPerExpenseCategory> amountPerExpenseCategory) {
                valuesExpensesPieChart = amountPerExpenseCategory;
                if(amountPerExpenseCategory != null) {
                    if (indicatorCategory){
                        setupPieChart();
                        loadPieChartData();
                    }
                }
            }
        });

        mTabOverviewSummaryViewModel.vmGetAmountPerIncomeCategory().observe(getViewLifecycleOwner(), new Observer<List<DAOTransaction.AmountPerIncomeCategory>>() {
            @Override
            public void onChanged(@Nullable List<DAOTransaction.AmountPerIncomeCategory> amountPerIncomeCategory) {
                valuesIncomePieChart = amountPerIncomeCategory;
                if(amountPerIncomeCategory != null) {
                    if (!indicatorCategory) {
                        setupPieChart();
                        loadPieChartData();
                    }
                }
            }
        });

        arrayMonth = getResources().getStringArray(R.array.fragmentsummary_specifymonth);

        //----------------------------------Manuel Selection----------------------------------

        buttonDailySelection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDailyGraph(v);
            }
        });

        buttonMonthlySelection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showMonthlyGraph(v);
            }
        });

        buttonYearlySelection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showYearlyGraph(v);
            }
        });

        buttonCustomizedSelectionForDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSelectedTimeGraph(v);
            }
        });

        //----------------------------------Manuel Selection----------------------------------

        return rootView;

    }

    private void showDailyGraph(View v) {
        if(valuesExpensesPieChart != null && valuesIncomePieChart != null) {
            BarChartSetUp barChartSetUp = new BarChartSetUp();
            BarChart mBarChartTransactionGraph = barChartSetUp.barChartSetSettings(barChartTransaction);
            barChartSetUp.barChartLoadData(mBarChartTransactionGraph, valuesExpensesPieChart, valuesIncomePieChart,getYesterday() , new Date());
        }
    }

    private void showMonthlyGraph(View v) {
        if(valuesExpensesPieChart != null && valuesIncomePieChart != null) {
            BarChartSetUp barChartSetUp = new BarChartSetUp();
            BarChart mBarChartTransactionGraph = barChartSetUp.barChartSetSettings(barChartTransaction);
            barChartSetUp.barChartLoadData(mBarChartTransactionGraph, valuesExpensesPieChart, valuesIncomePieChart,getWholeMonth(-1) , new Date());
        }
    }

    private void showYearlyGraph(View v) {
        if(valuesExpensesPieChart != null && valuesIncomePieChart != null) {
            BarChartSetUp barChartSetUp = new BarChartSetUp();
            BarChart mBarChartTransactionGraph = barChartSetUp.barChartSetSettings(barChartTransaction);
            barChartSetUp.barChartLoadData(mBarChartTransactionGraph, valuesExpensesPieChart, valuesIncomePieChart,getWholeMonth(-12) , new Date());
        }
    }

    private void showSelectedTimeGraph(View v) {

        List<String> listInputStart = new ArrayList<>();
        listInputStart.add(editTextTimePeriodStartDay.getText().toString());
        listInputStart.add(editTextTimePeriodStartMonth.getText().toString());
        listInputStart.add(editTextTimePeriodStartYear.getText().toString());
        List<String> listInputEnd = new ArrayList<>();
        listInputEnd.add(editTextTimePeriodEndDay.getText().toString());
        listInputEnd.add(editTextTimePeriodEndMonth.getText().toString());
        listInputEnd.add(editTextTimePeriodEndYear.getText().toString());
        CheckInputStringOutputDate checkStartDate = new CheckInputStringOutputDate();
        CheckInputStringOutputDate checkEndDate = new CheckInputStringOutputDate();

        if(!checkStartDate.runCheck(listInputStart) || !checkEndDate.runCheck(listInputEnd)) {
            Toast toast = Toast.makeText(getContext(), "Please enter valid input data", Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        } else {
            if(valuesExpensesPieChart != null && valuesIncomePieChart != null) {
                BarChartSetUp barChartSetUp = new BarChartSetUp();
                BarChart mBarChartTransactionGraph = barChartSetUp.barChartSetSettings(barChartTransaction);
                barChartSetUp.barChartLoadData(mBarChartTransactionGraph, valuesExpensesPieChart, valuesIncomePieChart,checkStartDate.getConvertedValues().get(0) , checkEndDate.getConvertedValues().get(0));
            }
        }
    }

    private Date getYesterday() {
        return new Date(System.currentTimeMillis()-24*60*60*1000);
    }

    private Date getWholeMonth(int minusMonths) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, minusMonths);

        int max = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        calendar.set(Calendar.DAY_OF_MONTH, max);

        return calendar.getTime();
    }

    private void setupPieChart() {
        proxyMPPieChartClass.setDrawHoleEnabled(true);
        proxyMPPieChartClass.setUsePercentValues(true);
        proxyMPPieChartClass.setEntryLabelTextSize(12);
        proxyMPPieChartClass.setEntryLabelColor(Color.BLACK);
        proxyMPPieChartClass.setCenterText();
        proxyMPPieChartClass.setCenterTextSize(24);
        proxyMPPieChartClass.getDescription().setEnabled(false);
        Legend l = proxyMPPieChartClass.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        l.setOrientation(Legend.LegendOrientation.VERTICAL);
        l.setDrawInside(false);
        l.setEnabled(true);

        pieChart = proxyMPPieChartClass.getFromCache();
    }

    private void loadPieChartData() {
        DataPreparationPieChart dataPreparationPieChart = new DataPreparationPieChart();
        dataPreparationPieChart.setListPieChart(valuesExpensesPieChart);
        dataPreparationPieChart.selectCategories();

        ArrayList<PieEntry> entries = new ArrayList<>();
        if (indicatorCategory){
            for (DAOTransaction.AmountPerExpenseCategory amountPerExpenseCategory : valuesExpensesPieChart) {
                entries.add(new PieEntry((float) amountPerExpenseCategory.amount, amountPerExpenseCategory.cat_name));
            }
        }else {
            for (DAOTransaction.AmountPerIncomeCategory amountPerIncomeCategory : valuesIncomePieChart) {
                entries.add(new PieEntry((float) amountPerIncomeCategory.amount, amountPerIncomeCategory.cat_name));
            }
        }

        ArrayList<Integer> colors = new ArrayList<>();
        for (int color: ColorTemplate.MATERIAL_COLORS) {
            colors.add(color);
        }

        for (int color: ColorTemplate.VORDIPLOM_COLORS) {
            colors.add(color);
        }

        PieDataSet dataSet = new PieDataSet(entries, "Category");
        dataSet.setColors(colors);

        PieData data = new PieData(dataSet);
        data.setDrawValues(true);
        data.setValueFormatter(new PercentFormatter(pieChart));
        data.setValueTextSize(12f);
        data.setValueTextColor(Color.BLACK);

        pieChart.setData(data);
        pieChart.invalidate();
        pieChart.animateY(1400, Easing.EaseInOutQuad);
    }

    private void setupBarChart() {
        barChart.setDrawGridBackground(false);
        barChart.setDrawBarShadow(false);
        barChart.setDrawBorders(false);

        Legend l = barChart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        l.setOrientation(Legend.LegendOrientation.VERTICAL);
        l.setDrawInside(false);
        l.setEnabled(true);
    }

    private void loadBarChart(List<EntityTransaction> yValuesBarChart1) {

        /*
        List<EntityTransaction> filteredList = new ArrayList<>();
        for (EntityTransaction currentTransaction : yValuesBarChart1){
            //status enum
            if (true){
                filteredList.add(currentTransaction);
            }
        }
        */

        DataPreparationBarChart dataPreparationBarChart = new DataPreparationBarChart();
        dataPreparationBarChart.setListBarChart(yValuesBarChart1);
        dataPreparationBarChart.selectInOutTransactions();

        ArrayList<BarEntry> entries1 = new ArrayList<>();
        ArrayList<BarEntry> entries2 = new ArrayList<>();
        entries1.add(new BarEntry(0, dataPreparationBarChart.valuesIn));
        entries2.add(new BarEntry(1, dataPreparationBarChart.valuesOut));

        ArrayList<Integer> colors = new ArrayList<>();
        for (int color: ColorTemplate.MATERIAL_COLORS) {
            colors.add(color);
        }

        for (int color: ColorTemplate.VORDIPLOM_COLORS) {
            colors.add(color);
        }

        BarDataSet dataSet1 = new BarDataSet(entries1, "Income");
        BarDataSet dataSet2 = new BarDataSet(entries2, "Expense");
        dataSet1.setColors(colors);

        BarData data = new BarData(dataSet1, dataSet2);
        data.setBarWidth(0.9f); // set custom bar width
        data.setValueTextSize(12);
        data.setValueTextColor(Color.BLACK);

        barChart.setData(data);
        barChart.setFitBars(true); // make the x-axis fit exactly all bars
        barChart.invalidate(); // refresh
        barChart.animateY(1400, Easing.EaseInOutQuad);
        barChart.animateX(1400, Easing.EaseInOutQuad);

    }

    public void switchIncomeExpense(View view) {
        if (indicatorCategory) {
            switchIncomeExpenseCategoryButton.setText("Switch to Expenses");
            indicatorCategory = false;
            loadPieChartData();
        }else {
            switchIncomeExpenseCategoryButton.setText("Switch to Income");
            indicatorCategory = true;
            loadPieChartData();
        }
    }

}